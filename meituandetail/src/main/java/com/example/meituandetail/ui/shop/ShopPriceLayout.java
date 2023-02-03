package com.example.meituandetail.ui.shop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.meituandetail.R;
import com.example.meituandetail.utils.SourceUtil;
import com.example.meituandetail.widgets.ViewState;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopPriceLayout extends ConstraintLayout {

    @BindView(R.id.cl_main)
    public ConstraintLayout cl_main;

    private float effected = 0F;
    private boolean isFirstLayout;

    public ShopPriceLayout(@NonNull Context context) {
        this(context,null);
    }

    public ShopPriceLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShopPriceLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shop_price, this);
        ButterKnife.bind(view);
    }

    private ArrayList<View> animViews(){
        ArrayList<View> views = new ArrayList<>();
        views.add(cl_main);
        return views;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!isFirstLayout){
            isFirstLayout = true;
            ViewState.stateSave(cl_main,R.id.viewStateStart).alpha(1F);
            ViewState.stateSave(cl_main,R.id.viewStateEnd).alpha(0F);
        }
    }

    public void effectedByOffset(float transY) {
        if (transY <= SourceUtil.dp(140)){
            effected = 0F;
        }else if(transY > SourceUtil.dp(140) && transY < SourceUtil.dp(230)){
            effected = (transY - SourceUtil.dp(140)) / SourceUtil.dp(90);
        }else {
            effected = 1F;
        }
        for (View view : animViews()) {
            ViewState.stateRefresh(view,R.id.viewStateStart,R.id.viewStateEnd,effected);
        }
    }
}
