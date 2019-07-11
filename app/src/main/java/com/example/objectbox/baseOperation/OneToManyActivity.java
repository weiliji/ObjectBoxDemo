package com.example.objectbox.baseOperation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.objectbox.R;
import com.example.objectbox.adapter.OneToManyAdapter;
import com.example.objectbox.adapter.OrderAdapter;
import com.example.objectbox.bean.Customer;
import com.example.objectbox.presenter.OneToManyPresenter;
import com.example.objectbox.util.MyUtils;


public class OneToManyActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_customer_id,et_order_id,et_customer_name,et_order_name;
    private OneToManyAdapter oneToManyAdapter;
    private OrderAdapter orderAdapter;
    private RecyclerView rv_customer,rv_order;
    private LinearLayout ll_customer;
    private LinearLayout ll_order;
    private OneToManyPresenter presenter;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_many);
        initUI();
        initRV();
        presenter = new OneToManyPresenter(this);
        oneToManyAdapter.setOnItemClick(new OneToManyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Customer customer) {
                if(customer.orders.size()>0){
                    orderAdapter.setData(customer.orders);
                    int measuredWidth = ll_customer.getMeasuredWidth();
                    MyUtils.startAnim(1, measuredWidth,width, ll_customer, ll_order);
                }else {
                    Toast.makeText(OneToManyActivity.this, "该客户没有订单", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUI() {
        width = getWindowManager().getDefaultDisplay().getWidth();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        findViewById(R.id.btn_add_customer).setOnClickListener(this);
        findViewById(R.id.btn_add_order).setOnClickListener(this);
        findViewById(R.id.btn_delete_customer).setOnClickListener(this);
        findViewById(R.id.btn_delete_order).setOnClickListener(this);
        findViewById(R.id.btn_update_customer).setOnClickListener(this);
        findViewById(R.id.btn_update_order).setOnClickListener(this);
        findViewById(R.id.btn_query_customer).setOnClickListener(this);
        findViewById(R.id.btn_query_order).setOnClickListener(this);
        ll_customer = findViewById(R.id.ll_customer);
        ll_order = findViewById(R.id.ll_order);
        et_customer_id = findViewById(R.id.et_customer_id);
        et_order_id = findViewById(R.id.et_order_id);
        et_customer_name = findViewById(R.id.et_customer_name);
        et_order_name = findViewById(R.id.et_order_name);
        rv_customer = findViewById(R.id.rv_customer);
        rv_order = findViewById(R.id.rv_order);
    }

    private void initRV() {
        rv_customer.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        rv_order.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        oneToManyAdapter = new OneToManyAdapter();
        orderAdapter = new OrderAdapter();
        rv_customer.setAdapter(oneToManyAdapter);
        rv_order.setAdapter(orderAdapter);
    }

    @Override
    public void onClick(View v) {
        int rondom_id = MyUtils.creatRandom();
        int measuredWidth = ll_customer.getMeasuredWidth();
        String customer_name = et_customer_name.getText().toString().trim();
        String order_name = et_order_name.getText().toString().trim();
        String customer_id = et_customer_id.getText().toString().trim();
        String order_id = et_order_id.getText().toString().trim();
        switch (v.getId()) {
            case R.id.fab:
                finish();
                break;
            case R.id.btn_add_customer:
                oneToManyAdapter.setData(presenter.addCustomer(customer_name, rondom_id));
                MyUtils.startAnim(2,measuredWidth,width,ll_customer,ll_order);
                break;
            case R.id.btn_delete_customer:
                oneToManyAdapter.setData(presenter.deleteCustomer(customer_name, customer_id));
                MyUtils.startAnim(2,measuredWidth,width,ll_customer,ll_order);
                break;
            case R.id.btn_update_customer:
                oneToManyAdapter.setData(presenter.update(customer_name, customer_id));
                MyUtils.startAnim(2,measuredWidth,width,ll_customer,ll_order);
                break;
            case R.id.btn_query_customer:
                oneToManyAdapter.setData(presenter.queryCustomer(customer_id, customer_name));
                MyUtils.startAnim(2,measuredWidth,width,ll_customer,ll_order);
                break;
            case R.id.btn_add_order:
                orderAdapter.setData(presenter.addOrder(rondom_id,order_name, customer_id));
                MyUtils.startAnim(1,measuredWidth,width,ll_customer,ll_order);
                break;
            case R.id.btn_delete_order:
                orderAdapter.setData(presenter.deleteOrder(customer_id, order_id));
                MyUtils.startAnim(1,measuredWidth,width,ll_customer,ll_order);
                break;
            case R.id.btn_update_order:
                orderAdapter.setData(presenter.updateOrder(customer_id, order_id, order_name));
                MyUtils.startAnim(1,measuredWidth,width,ll_customer,ll_order);
                break;
            case R.id.btn_query_order:
                orderAdapter.setData(presenter.queryOrders(customer_id,order_id));
                MyUtils.startAnim(1,measuredWidth,width,ll_customer,ll_order);
                break;
        }
    }

}
