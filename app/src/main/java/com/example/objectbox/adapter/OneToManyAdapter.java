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
import com.example.objectbox.bean.Customer;

import java.util.ArrayList;
import java.util.List;

public class OneToManyAdapter extends RecyclerView.Adapter<CommonHolder> {
    private List<Customer> list = new ArrayList<>();

    @NonNull
    @Override
    public CommonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.adapter_one_to_many, viewGroup, false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommonHolder holder, final int i) {
        holder.id.setText(String.valueOf(list.get(i).getId()));
        holder.name.setText(list.get(i).getName());
        holder.num.setText(String.valueOf(list.get(i).getOrders().size()));
        holder.ll_one_to_many.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,list.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Customer> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClickListener onItemClickListener){
        listener = onItemClickListener;
    }

    public OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(View v,Customer customer);
    }
}