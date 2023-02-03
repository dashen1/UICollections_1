package com.example.meituancooridinator.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.meituancooridinator.R;
import com.example.meituancooridinator.view.ViewState;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketView extends ConstraintLayout implements ViewState.AnimationUpdateListener {

    private boolean isFirstLayout = false;

    @BindView(R.id.vBorder1)
    public ImageView vBorder1;
    @BindView(R.id.vBorder2)
    public ImageView vBorder2;
    @BindView(R.id.vSimple)
    public TextView vSimple;
    @BindView(R.id.layDetail)
    public ConstraintLayout layDetail;
    @BindView(R.id.vDetail1)
    public TextView vDetail1;
    @BindView(R.id.vDetail2)
    public TextView vDetail2;
    @BindView(R.id.vDetail3)
    public TextView vDetail3;

    private ArrayList<View> list;

    public TicketView(@NonNull Context context) {
        this(context,null);
    }

    public TicketView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TicketView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_view, this);
        ButterKnife.bind(view);
       list = new ArrayList<>();
        list.add(layDetail);
       list.add(vBorder1);
       list.add(vBorder2);
       list.add(vSimple);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(!isFirstLayout){
            isFirstLayout = true;
            ViewState.stateSave(vBorder1, R.id.vs1).a(1F);
            ViewState.stateSave(vBorder1, R.id.vs2).ws(3.8F).hs(3.8F).a(0F);
            ViewState.stateSave(vBorder2, R.id.vs1);
            ViewState.stateSave(vBorder2, R.id.vs2).ws(3.8F).hs(3.8F).a(1F);
            ViewState.stateSave(vSimple, R.id.vs1);
            ViewState.stateSave(vSimple, R.id.vs2).a(0F);
            ViewState.stateSave(layDetail, R.id.vs1);
            ViewState.stateSave(layDetail, R.id.vs2).sx(1F).sy(1F).ws(3.8F).hs(3.8F).a(1F);
        }
    }

    public void set(int amount, int limit, String expireTime){
        vSimple.setText("领￥"+amount);

        vDetail1.setText("￥"+amount);
        vDetail2.setText("满"+ limit +"可用");
        vDetail3.setText("有效期至"+expireTime);
    }

    public void onAnimationUpdate(int tag1,int tag2,float p){
        for (View view : list) {
            ViewState.stateRefresh(view, tag1,tag2,p);
        }
    }
}
