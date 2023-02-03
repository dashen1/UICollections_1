package com.example.uicollections_1.exampleloadnet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.uicollections_1.R;

public class LoadingLine extends FrameLayout {
    private View loadView;

    public LoadingLine(Context context) {
        super(context);
        initView(context);
    }

    public LoadingLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.activity_loading_line, null);
        loadView = view.findViewById(R.id.loadingView);
        startAnimation();
        this.addView(view);
    }

    public void startAnimation(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.1f, 1f, 1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.2f);
        scaleAnimation.setRepeatCount(-1);
        alphaAnimation.setRepeatCount(-1);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);;
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(1000);
        loadView.startAnimation(animationSet);
    }

}
