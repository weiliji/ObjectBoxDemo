package com.example.objectbox.bean;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * 客户
 */
@Entity
public class Customer implements Parcelable {
    @Id
    public long id;
    public String name;
    @Backlink(to = "customer")
    public ToMany<Order> orders;

    protected Customer(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public ToMany<Order> getOrders() {
        return orders;
    }

    public void setOrders(ToMany<Order> orders) {
        this.orders = orders;
    }

    public Customer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }
}
