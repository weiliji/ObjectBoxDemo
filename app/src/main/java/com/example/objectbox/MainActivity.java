package com.example.objectbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.objectbox.baseOperation.BaseOperationActivity;
import com.example.objectbox.baseOperation.ManyToManyActivity;
import com.example.objectbox.baseOperation.OneToManyActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(this, BaseOperationActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, OneToManyActivity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(this, ManyToManyActivity.class));
                break;
            default:
                break;
        }
    }
}
