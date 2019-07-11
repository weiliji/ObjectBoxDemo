package com.example.objectbox.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.objectbox.App;
import com.example.objectbox.R;
import com.example.objectbox.bean.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<CommonHolder>{

    private List<Order> list =new ArrayList<>();

    @NonNull
    @Override
    public CommonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.adapter_one_to_many, viewGroup, false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonHolder holder, int i) {
        holder.id.setText(String.valueOf(list.get(i).getCustomer().getTargetId()));
        holder.name.setText(String.valueOf(list.get(i).getId()));
        holder.num.setText(String.valueOf(list.get(i).getName()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Order> list){
        this.list = list;
        notifyDataSetChanged();
    }

}
