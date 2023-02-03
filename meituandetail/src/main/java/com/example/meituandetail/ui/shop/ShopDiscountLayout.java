package com.example.meituandetail.ui.shop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.meituandetail.R;
import com.example.meituandetail.utils.SourceUtil;
import com.example.meituandetail.widgets.ViewState;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDiscountLayout extends ConstraintLayout {

    private boolean mIsExpanded = false;

    @BindView(R.id.tv_shop_name_b)
    public TextView tv_shop_name_b;
    @BindView(R.id.cl_discount)
    public ConstraintLayout cl_discount;
    @BindView(R.id.cl_discount_expanded)
    public ConstraintLayout cl_discount_expanded;
    @BindView(R.id.view_top_bg_shadow)
    public View view_top_bg_shadow;
    @BindView(R.id.sv_main)
    public ScrollView sv_main;
    @BindView(R.id.tv_announcement)
    public TextView tv_announcement;
    @BindView(R.id.iv_close)
    public ImageView iv_close;

    private float effected;
    private boolean isFirstLayout;

    public AnimatorListenerAdapter1 animListener;

    private AnimatorListenerAdapter internalAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (animListener != null) {
                animListener.onAnimationStart(animation, mIsExpanded);
            }
        }
    };

    public ShopDiscountLayout(@NonNull Context context) {
        this(context,null);
    }

    public ShopDiscountLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShopDiscountLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shop_details_discount, this);
        ButterKnife.bind(view);

        tv_announcement.setOnClickListener(v -> switchLayout(!mIsExpanded, false));
        iv_close.setOnClickListener(v -> switchLayout(!mIsExpanded, false));

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!isFirstLayout) {
            isFirstLayout = true;
            ViewState.stateSave(view_top_bg_shadow, R.id.viewStateStart).alpha(0F);
            ViewState.stateSave(view_top_bg_shadow, R.id.viewStateEnd).alpha(1F);
            ViewState.stateSave(tv_shop_name_b, R.id.viewStateStart).alpha(0.8F);
            ViewState.stateSave(tv_shop_name_b, R.id.viewStateEnd).alpha(1F);
            ViewState.stateSave(cl_discount, R.id.viewStateStart).alpha(1F);
            ViewState.stateSave(cl_discount, R.id.viewStateEnd).alpha(0F);
            ViewState.stateSave(cl_discount_expanded, R.id.viewStateStart).alpha(0F);
            ViewState.stateSave(cl_discount_expanded, R.id.viewStateEnd).alpha(1F);

        }
    }

    public ArrayList<View> animViews() {
        ArrayList<View> views = new ArrayList<>();
        views.add(tv_shop_name_b);
        views.add(cl_discount);
        views.add(cl_discount_expanded);
        views.add(view_top_bg_shadow);
        return views;
    }

    public void effectedByOffset(float transY) {
        if (transY <= SourceUtil.dp(140)) {
            effected = 0F;
        } else if (transY > SourceUtil.dp(140) && transY < SourceUtil.dp(230)) {
            effected = (transY - SourceUtil.dp(140)) / SourceUtil.dp(90);
        } else {
            effected = 1F;
        }
        for (View view : animViews()) {
            ViewState.stateRefresh(view, R.id.viewStateStart, R.id.viewStateEnd, effected);
        }
    }

    public void switchLayout(boolean expanded, boolean byScrollerSlide) {
        if (mIsExpanded == expanded) {
            return;
        }
        sv_main.scrollTo(0, 0);
        mIsExpanded = expanded;
        float start = 0f;
        float end = expanded ? 1F : 0F;
        ViewState.statesChangeByAnimation(animViews(), R.id.viewStateStart, R.id.viewStateEnd, start, end, null, byScrollerSlide ? null : internalAnimListener, 400, 0L);
    }

    interface AnimatorListenerAdapter1 {
        void onAnimationStart(Animator animator, boolean toExpand);
    }
}
