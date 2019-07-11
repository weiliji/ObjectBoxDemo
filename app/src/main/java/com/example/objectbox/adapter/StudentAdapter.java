package com.example.objectbox.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.objectbox.App;
import com.example.objectbox.R;
import com.example.objectbox.bean.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<CommonHolder>{
    private List<Student> students = new ArrayList<>();

    @NonNull
    @Override
    public CommonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.adapter_one_to_many, viewGroup,false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonHolder holder, final int i) {
        holder.id.setText(String.valueOf(students.get(i).getId()));
        holder.name.setText(students.get(i).getName());
        holder.num.setText(String.valueOf(students.get(i).getTeachers().size()));
        holder.ll_one_to_many.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,students.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setData(List<Student> students){
        this.students = students;
        notifyDataSetChanged();
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(View v, Student student);
    }
}
