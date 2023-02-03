package com.example.meituanshoppingcart.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meituanshoppingcart.R;
import com.example.meituanshoppingcart.util.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected Activity mActivity;
    protected Unbinder mUnBinder;

    protected boolean isRegistered = false;


    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayout());
        //ButterKnife绑定,注意要在绑定试图之后写
        mUnBinder = ButterKnife.bind(this);

        initEvent();
        //用来设置整体下移
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般手机的状态蓝文字和图标都是白色的，可如果你的应用也是纯白色的，就会导致状态栏文字看不清
        //所以如果你是这种情况，请使用一下代码，设置状态使用深色文字图标风格，否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this,false)){
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        StatusBarUtil.setStatusBarDarkTheme(mActivity,true);
        StatusBarUtil.setStatusBarColor(mActivity,mContext.getResources().getColor(R.color.color_ffffff));

        initListener();
        initData();
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder!=null){
            mUnBinder.unbind();
        }
        //网络监听解绑
        if (isRegistered){

        }
        super.onDestroy();
    }

    protected abstract int getLayout();

    protected abstract void initEvent();

    protected abstract void onNetWork();

    protected abstract void initListener();

    protected abstract void initData();
}
