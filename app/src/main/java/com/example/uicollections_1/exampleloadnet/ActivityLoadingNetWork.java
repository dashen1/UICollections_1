package com.example.uicollections_1.exampleloadnet;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uicollections_1.R;

public class ActivityLoadingNetWork extends AppCompatActivity {

    private ImageView view1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_network);

//        view1 = findViewById(R.id.view1);
//        ValueAnimator animator = ObjectAnimator.ofInt(view1, "backgroundColor", 0x00ff0000, 0x6600ff00);//对背景色颜色进行改变，操作的属性为"backgroundColor",此处必须这样写，不能全小写,后面的颜色为在对应颜色间进行渐变
//        animator.setDuration(3000);
//        animator.setEvaluator(new ArgbEvaluator());//如果要颜色渐变必须要ArgbEvaluator，来实现颜色之间的平滑变化，否则会出现颜色不规则跳动
//        animator.start();
    }
}
