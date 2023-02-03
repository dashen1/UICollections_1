package com.example.uicollections_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uicollections_1.exampleloadnet.ActivityLoadingNetWork;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.load_net_bt);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.load_net_bt:
                gotoPage(ActivityLoadingNetWork.class);
                break;
            default:
                break;
        }
    }

    private void gotoPage(final Class<?> tClass){
        Intent intent = new Intent(MainActivity.this, tClass);
        startActivity(intent);
    }
}