package com.example.objectbox.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.objectbox.App;
import com.example.objectbox.R;
import com.example.objectbox.bean.Teacher;
import java.util.ArrayList;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<CommonHolder>{

    private List<Teacher> teachers = new ArrayList<>();

    @NonNull
    @Override
    public CommonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.adapter_one_to_many, viewGroup,false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonHolder holder, final int i) {
        holder.id.setText(String.valueOf(teachers.get(i).getId()));
        holder.name.setText(teachers.get(i).getName());
        holder.num.setText(String.valueOf(teachers.get(i).getStudents().size()));
        holder.ll_one_to_many.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,teachers.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public void setData(List<Teacher> teachers){
        this.teachers = teachers;
        notifyDataSetChanged();
    }

    public OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(View v, Teacher teacher);
    }
}
