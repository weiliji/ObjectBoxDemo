package com.example.objectbox.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.objectbox.R;

public class CommonHolder extends RecyclerView.ViewHolder{

    public TextView id,name,num;
    public LinearLayout ll_one_to_many;
    public CommonHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.tv_adapter_customer_id);
        name = itemView.findViewById(R.id.tv_adapter_customer_name);
        num = itemView.findViewById(R.id.tv_adapter_order_num);
        ll_one_to_many = itemView.findViewById(R.id.ll_one_to_many);
    }
}
