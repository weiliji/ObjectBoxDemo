package com.example.objectbox.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.objectbox.App;
import com.example.objectbox.R;
import com.example.objectbox.bean.User;

import java.util.ArrayList;
import java.util.List;

public class BaseOperationAdapter extends RecyclerView.Adapter<BaseOperationAdapter.UserViewHolder> {
    private List<User> list = new ArrayList<>();

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.adapter_base_operation, viewGroup,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.id.setText(list.get(i).id+"");
        userViewHolder.name.setText(list.get(i).name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<User> list){
        this.list = list;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private  TextView id;
        private  TextView name;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_adapter_id);
            name = itemView.findViewById(R.id.tv_adapter_name);
        }
    }
}
