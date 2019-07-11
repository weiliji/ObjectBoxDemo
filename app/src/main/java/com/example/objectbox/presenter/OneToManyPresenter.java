package com.example.objectbox.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.objectbox.bean.Customer;
import com.example.objectbox.bean.Customer_;
import com.example.objectbox.bean.Order;
import com.example.objectbox.bean.Order_;
import com.example.objectbox.util.ObjectBox;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class OneToManyPresenter {
    private static final String TAG = "OneToManyPresenter";
    private Box<Customer> customerBox;
    private Box<Order> orderBox;
    private Activity activity;

    public OneToManyPresenter(Activity activity) {
        this.activity = activity;
        customerBox = ObjectBox.get().boxFor(Customer.class);
        orderBox = ObjectBox.get().boxFor(Order.class);
    }

    /**
     * 添加客户
     */
    public List<Customer> addCustomer(String customer_name, int rondom_id) {
        Customer customer = new Customer();
        customer.setName(TextUtils.isEmpty(customer_name) ? "客户" + rondom_id + "号" : customer_name);
        customerBox.put(customer);
        return queryCustomer(null,null);
    }

    /**
     * 删除客户
     * @param customer_name
     * @param customer_id
     */
    public List<Customer> deleteCustomer(String customer_name, String customer_id) {
        if (TextUtils.isEmpty(customer_id) && TextUtils.isEmpty(customer_name)) {//两者为空时 删除最后一个客户
            List<Customer> customers = customerBox.query().build().find();
            if (customers.size() > 0) {
                Customer customer1 = customers.get(customers.size() - 1);
                orderBox.remove(customer1.orders);
                customerBox.remove(customer1);
            } else {
                Toast.makeText(activity, "已经没有客户", Toast.LENGTH_SHORT).show();
            }
        } else if (!TextUtils.isEmpty(customer_id) && TextUtils.isEmpty(customer_name)) {//客户Id不为空 客户名空
            Customer first = customerBox.query().equal(Customer_.id, Long.parseLong(customer_id)).build().findUnique();
            if (first != null) {
                orderBox.remove(first.orders);
                customerBox.remove(first);
            } else {
                Toast.makeText(activity, "未找到该ID的客户", Toast.LENGTH_SHORT).show();
            }
        } else if (TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(customer_name)) {//客户Id为空 客户名不为空
            final List<Customer> customers = customerBox.query().equal(Customer_.name, customer_name).build().find();
            if (customers != null) {
                ObjectBox.get().runInTx(new Runnable() {
                    @Override
                    public void run() {
                        for (Customer customer : customers){
                            orderBox.remove(customer.orders);
                        }
                        customerBox.remove(customers);
                    }
                });
            } else {
                Toast.makeText(activity, "未找到该名字的客户", Toast.LENGTH_SHORT).show();
            }
        } else {//两者不为空 删除指定用户
            Customer first = customerBox.query().equal(Customer_.name, customer_name).equal(Customer_.id, Long.parseLong(customer_id)).build().findUnique();
            if (first != null) {
                orderBox.remove(first.orders);
                customerBox.remove(first);//删除指定id
            }else{
                Toast.makeText(activity, "未找到该客户", Toast.LENGTH_SHORT).show();
            }
        }
        return queryCustomer(null,null);
    }

    /**
     * 更新客户
     * @param customer_name
     * @param customer_id
     */
    public List<Customer> update(String customer_name, String customer_id) {
        if (TextUtils.isEmpty(customer_id)) {//无指定ID 修改最后一条数据
            List<Customer> customers = customerBox.query().build().find();
            Customer customer1 = customers.get(customers.size() - 1);
            if (TextUtils.isEmpty(customer_name)) {
                Toast.makeText(activity, "请输入客户名", Toast.LENGTH_SHORT).show();
            } else {
                customer1.setName(customer_name);
                customerBox.put(customer1);
            }
        } else {//修改指定ID客户
            Customer first = customerBox.query().equal(Customer_.id, Long.parseLong(customer_id)).build().findUnique();
            if (first == null) {
                Toast.makeText(activity, "未找到该用户", Toast.LENGTH_SHORT).show();
            } else {
                if (TextUtils.isEmpty(customer_name)) {
                    Toast.makeText(activity, "请输入客户名", Toast.LENGTH_SHORT).show();
                } else {
                    first.setName(customer_name);
                }
                customerBox.put(first);
            }
        }
        return queryCustomer(null,null);
    }

    /**
     * 查询客户
     */
    public List<Customer> queryCustomer(String customer_id, String customer_name) {
        QueryBuilder<Customer> query = customerBox.query();
        if (!TextUtils.isEmpty(customer_id)&&!TextUtils.isEmpty(customer_name)) {//两者不为空
            query.equal(Customer_.id, Long.parseLong(customer_id)).equal(Customer_.name, customer_name);
        }else if(!TextUtils.isEmpty(customer_id)){//客户id不为空
            query.equal(Customer_.id, Long.parseLong(customer_id));
        }else if(!TextUtils.isEmpty(customer_name)){//客户名不为空
            query.contains(Customer_.name, customer_name);
        }
        return query.build().find();
    }

    /**
     * 添加订单
     * @param rondom_id
     * @param order_name
     * @param customer_id
     * @return
     */
    public List<Order> addOrder(int rondom_id, String order_name, String customer_id) {
        Order order = new Order();
        order.setName(TextUtils.isEmpty(order_name) ? "订单" + rondom_id + "号" : order_name);
        if (TextUtils.isEmpty(customer_id)) {//没有指定客户ID
            List<Customer> customers = customerBox.query().build().find();
            if (customers.size() > 0) {
                Customer customer1 = customers.get(customers.size() - 1);
                customer1.orders.add(order);
                customerBox.put(customer1);
            }
        } else {//给指定客户加订单
            Customer first = customerBox.query().equal(Customer_.id, Long.parseLong(customer_id)).build().findUnique();
            if (first == null) {
                Toast.makeText(activity, "未找到该客户", Toast.LENGTH_SHORT).show();
            }else{
                first.orders.add(order);
                customerBox.put(first);
            }
        }
        return queryOrders(customer_id,null);
    }

    /**
     * 删除订单
     * @param customer_id
     * @param order_id
     */
    public List<Order> deleteOrder(String customer_id, String order_id) {
        if (TextUtils.isEmpty(customer_id) && TextUtils.isEmpty(order_id)) {
            Toast.makeText(activity, "请输入客户ID或订单ID", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (!TextUtils.isEmpty(customer_id) && !TextUtils.isEmpty(order_id)) {//两者不为空
            QueryBuilder<Order> equal = orderBox.query().equal(Order_.id, Long.parseLong(order_id));
            equal.link(Order_.customer).equal(Customer_.id, Long.parseLong(customer_id));
            Order unique = equal.build().findUnique();
            if (unique == null) {
                Toast.makeText(activity, "未找到指定订单", Toast.LENGTH_SHORT).show();
            } else {
                orderBox.remove(unique);
            }
        } else if (!TextUtils.isEmpty(customer_id)) {//客户ID不为空
            Customer first = customerBox.query().equal(Customer_.id, Long.parseLong(customer_id)).build().findUnique();
            if (first == null) {
                Toast.makeText(activity, "未找到指定客户", Toast.LENGTH_SHORT).show();
            } else {
                if (first.orders.size() > 0) {
                    Order order = first.orders.get(first.orders.size() - 1);
                    orderBox.remove(order);
                } else {
                    Toast.makeText(activity, "该用户没有订单", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (!TextUtils.isEmpty(order_id)) {//订单ID不为空
            Order first = orderBox.query().equal(Order_.id, Long.parseLong(order_id)).build().findUnique();
            if (first == null) {
                Toast.makeText(activity, "未找到指定订单", Toast.LENGTH_SHORT).show();
            } else {
                orderBox.remove(first);
            }
        }
        return queryOrders(customer_id,null);
    }


    /**
     * 更新订单
     *
     * @param customer_id
     * @param order_id
     * @param order_name
     */
    public List<Order> updateOrder(String customer_id, String order_id, String order_name) {
        if (TextUtils.isEmpty(order_id) || TextUtils.isEmpty(order_name)) {
            Toast.makeText(activity, "订单ID与订单名不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(customer_id)) {
                Order unique = orderBox.query().equal(Order_.id, Long.parseLong(order_id)).build().findUnique();
                if (unique == null) {
                    Toast.makeText(activity, "未找到指定订单", Toast.LENGTH_SHORT).show();
                } else {
                    unique.setName(order_name);
                    orderBox.put(unique);
                }
            } else {//客户ID不为空则删除指定客户的某条订单
                QueryBuilder<Order> equal = orderBox.query().equal(Order_.id, Long.parseLong(order_id));
                equal.link(Order_.customer).equal(Customer_.id, Long.parseLong(customer_id));
                Order unique = equal.build().findUnique();
                if (unique == null) {
                    Toast.makeText(activity, "未找到指定订单", Toast.LENGTH_SHORT).show();
                } else {
                    unique.setName(order_name);
                    orderBox.put(unique);
                }
            }
        }
        return queryOrders(customer_id,order_id);
    }

    /**
     * 获取订单
     */
    public List<Order> queryOrders(String customer_id,String order_id) {
        QueryBuilder<Order> query = orderBox.query();
        if(!TextUtils.isEmpty(customer_id)&&!TextUtils.isEmpty(order_id)){//查询客户id与订单id
            query.equal(Order_.id, Long.parseLong(order_id));
            query.link(Order_.customer).equal(Customer_.id,  Long.parseLong(customer_id));
        }else if(!TextUtils.isEmpty(customer_id)){//查询客户id
            Customer customer = customerBox.query().equal(Customer_.id, Long.parseLong(customer_id)).build().findUnique();
            if(customer==null) {
                Toast.makeText(activity, "未找到该订单", Toast.LENGTH_SHORT).show();
            }else{
                return  customer.orders;
            }
        }else if(!TextUtils.isEmpty(order_id)){//查询订单id
            query.equal(Order_.id, Long.parseLong(order_id));
        }
        return query.build().find();
    }

}
