package com.example.tiktolkcommentdialog;

import android.view.View;

import com.example.tiktolkcommentdialog.activity.multi.CommentMultiActivity;
import com.example.tiktolkcommentdialog.activity.single.CommentSingleActivity;
import com.example.tiktolkcommentdialog.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void Single(View view) {
        startActivity(CommentSingleActivity.class);
    }

    public void Multi(View view) {
        startActivity(CommentMultiActivity.class);
    }
}