package com.example.meituancooridinator.business;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.meituancooridinator.R;


public class MerchantPageBehavior extends CoordinatorLayout.Behavior<MerchantPageLayout> {

    private Scroller scroller;

    private MerchantPageLayout selfView;
    private MerchantTitleLayout layTitle;
    private MerchantContentLayout layContent;
    private MerchantSettleLayout laySettle;

    private float simpleTopDistance = 0;
    private boolean isScrollToFullFood = false; // 上滑显示商品菜单
    private boolean isScrollToHideFood = false; // 下滑显示商店详情

    private int verticalPagingTouch = 0;
    private int horizontalPagingTouch = 0;
    private boolean isScrollRecommends = false;
    private int scrollDuration = 800;

    private Handler handler = new android.os.Handler();

    private Runnable flingRunnable = new Runnable() {
        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                selfView.setTranslationY((float) scroller.getCurrY());
                layContent.effectByOffset(selfView.getTranslationY());
                laySettle.effectByOffset(selfView.getTranslationY());
                handler.post(this);
            } else {
                isScrollToHideFood = false;
            }
        }
    };

    private MerchantContentLayout.AnimatorListenerAdapter1 mAnimListener = new MerchantContentLayout.AnimatorListenerAdapter1() {
        @Override
        public void onAnimationStart(Animator animator, boolean toExpanded) {
            if (toExpanded) {
                float defaultDisplayHeight = selfView.getHeight() - simpleTopDistance;
                scroller.startScroll(0, (int) selfView.getTranslationY(), 0, (int) (defaultDisplayHeight - selfView.getTranslationY()), scrollDuration);
            } else {
                scroller.startScroll(0, (int) selfView.getTranslationY(), 0, (int) (-selfView.getTranslationY()), scrollDuration);
            }
            handler.post(flingRunnable);
            isScrollToHideFood = true;
        }
    };

    public MerchantPageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        scroller = new Scroller(context);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull MerchantPageLayout child, int layoutDirection) {
        selfView = child;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) selfView.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            simpleTopDistance = lp.topMargin - layTitle.getHeight();
            lp.height = parent.getHeight() - layTitle.getHeight();
            child.setLayoutParams(lp);
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull MerchantPageLayout child, @NonNull View dependency) {

        switch (dependency.getId()) {
            case R.id.layTitle:
                layTitle = (MerchantTitleLayout) dependency;
                break;
            case R.id.layContent:
                layContent = (MerchantContentLayout) dependency;
                layContent.animListener = mAnimListener;
                if (laySettle != null) {
                    layContent.setLaySettle(laySettle);
                }
                break;
            case R.id.laySettle:
                laySettle = (MerchantSettleLayout) dependency;
                if (layContent != null) {
                    layContent.setLaySettle(laySettle);
                }
                break;
            default:
                return false;
        }
        return true;
    }


    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull MerchantPageLayout child, @NonNull View dependency) {
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MerchantPageLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MerchantPageLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        scroller.abortAnimation();
        isScrollToHideFood = false;
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MerchantPageLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (isScrollToHideFood) {
            consumed[1] = dy;
            return;
        }

        if ((child.getTranslationY() < 0 || (child.getTranslationY() == 0 && dy > 0)) && !child.canScrollVertically(-1)) {
            float effect = layTitle.effectByOffset(dy);
            float transY = -simpleTopDistance * effect;
            if (transY != child.getTranslationY()) {
                child.setTranslationY(transY);
                consumed[1] = dy;
            }
            if (type == 1) {
                isScrollToFullFood = true;
            }
        } else if ((child.getTranslationY() > 0 || (child.getTranslationY() == 0F && dy < 0)) && !child.canScrollVertically()) {
            if (isScrollToFullFood) {
                child.setTranslationY(0F);
            } else {
                child.setTranslationY(child.getTranslationY() - dy);
                layContent.effectByOffset(child.getTranslationY());
                laySettle.effectByOffset(child.getTranslationY());
            }
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MerchantPageLayout child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return onUserStopDragging();
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull MerchantPageLayout child, @NonNull View target, int type) {
        isScrollToFullFood = false;
        verticalPagingTouch = 0;
        horizontalPagingTouch = 0;
        isScrollRecommends = false;

        if (!isScrollToHideFood) {
            onUserStopDragging();
        }
    }

    public boolean onUserStopDragging() {
        if (selfView.getTranslationY() < 0f) {
            return false;
        }

        float defaultDisplayHeight = selfView.getHeight() - simpleTopDistance;
        if (defaultDisplayHeight * 0.4F > selfView.getTranslationY()) {
            scroller.startScroll(0, (int) selfView.getTranslationY(), 0, (int) (-selfView.getTranslationY()), scrollDuration);
            layContent.switchLayout(false, true);
            laySettle.switchLayout(false);
        } else {
            scroller.startScroll(0, (int) selfView.getTranslationY(), 0, (int) (defaultDisplayHeight - selfView.getTranslationY()), scrollDuration);
            layContent.switchLayout(true, true);
            laySettle.switchLayout(true);
        }
        handler.post(flingRunnable);
        isScrollToHideFood = true;
        return true;
    }
}
