package com.example.objectbox.baseOperation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.objectbox.adapter.BaseOperationAdapter;
import com.example.objectbox.util.ObjectBox;
import com.example.objectbox.R;
import com.example.objectbox.bean.User;

import java.util.List;

import io.objectbox.Box;

public class BaseOperationActivity extends AppCompatActivity implements View.OnClickListener {

    private Box<User> userBox;
    private int i=0;
    private BaseOperationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_operation);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        RecyclerView rv = findViewById(R.id.rv_demo);
        rv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL,false));
        adapter = new BaseOperationAdapter();
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this,OrientationHelper.VERTICAL));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userBox = ObjectBox.get().boxFor(User.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                User user1 = new User();
                user1.setName("xx" + i++);
                userBox.put(user1);
                query();
                break;
            case R.id.btn_delete:
                List<User> users1 = userBox.query().build().find();
                int v2 = (int)(Math.random() * (users1.size()));
                userBox.remove( users1.get(v2));
                query();
                break;
            case R.id.btn_update:
                List<User> users = userBox.query().build().find();
                if(users.size()==0) return;
                int v1 = (int)(Math.random() * (users.size()));
                User user = users.get(v1);
                if(user.getName().contains("xx")){
                    user.setName("oo"+v1);
                }else{
                    user.setName("xx"+v1);
                }
                userBox.put(user);
                query();
                break;
            case R.id.btn_query:
                query();
                break;
        }
    }

    private void query() {
        List<User> all = userBox.getAll();
        adapter.setData(all);
    }

}
