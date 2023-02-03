package com.example.meituancooridinator.business;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.meituancooridinator.R;
import com.example.meituancooridinator.utils.SourceUtil;
import com.example.meituancooridinator.view.ViewState;

import java.util.ArrayList;

public class MerchantSettleLayout extends ConstraintLayout {

    private boolean isFirstLayout = false;
    private boolean isExpanded = false; // layContent 内容是否展开查看中
    private float effected = 0f;

    public MerchantSettleLayout(@NonNull Context context) {
        this(context, null);
    }

    public MerchantSettleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MerchantSettleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.merchant_settle_layout, this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!isFirstLayout) {
            isFirstLayout = true;
            ViewState.stateSave(this, R.id.vs1).a(1F);
            ViewState.stateSave(this, R.id.vs2).a(0F);
        }
    }

    // 效果简单实现，具体内容应该根据业务动态计算变化高度区间。
    public void effectByOffset(float transY) {
        if (transY <= SourceUtil.dp(110)) {
            effected = 0F;
        } else if (transY > SourceUtil.dp(110) && transY < SourceUtil.dp(140)) {
            effected = (transY - SourceUtil.dp(110)) / SourceUtil.dp(30);
        } else {
            effected = 1F;
        }
        for (View view : animViews()) {
            ViewState.stateRefresh(view, R.id.vs1, R.id.vs2, effected);
        }
    }

    public void switchLayout(boolean expanded) {
        if (isExpanded == expanded) {
            return;
        }
        isExpanded = expanded;
        float start = effected;
        float end = expanded ? 1F : 0F;
        ViewState.statesChangeByAnimation(animViews(), R.id.vs1, R.id.vs2, start, end,
                null, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isExpanded) {
                            setVisibility(INVISIBLE);
                        }
                    }
                }, 300, 0L);
    }

    public ArrayList<View> animViews() {
        ArrayList<View> list = new ArrayList<>();
        list.add(this);
        return list;
    }
}
