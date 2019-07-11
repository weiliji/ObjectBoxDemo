package com.example.objectbox.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * 订单
 */
@Entity
public class Order {
    @Id
    public long id;
    public String name;
    public ToOne<Customer> customer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToOne<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(ToOne<Customer> customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
