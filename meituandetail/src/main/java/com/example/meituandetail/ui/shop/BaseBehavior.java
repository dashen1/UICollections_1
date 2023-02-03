package com.example.meituandetail.ui.shop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public abstract class BaseBehavior<T extends View> extends CoordinatorLayout.Behavior<T>{

    public BaseBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull T child, int layoutDirection) {
        return onLayoutChildBase(parent, child, layoutDirection) || super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull T child, @NonNull View dependency) {
        return layoutDependsOnBase(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull T child, @NonNull View dependency) {
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return onStartNestedScrollBase(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        onNestedPreScrollBase(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        onNestedScrollAcceptedBase(coordinatorLayout, child, directTargetChild, target, axes, type);
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View target, float velocityX, float velocityY) {
        return onNestedPreFlingBase(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View target, int type) {
        onStopNestedScrollBase(coordinatorLayout, child, target, type);
    }

    public abstract boolean onLayoutChildBase(@NonNull CoordinatorLayout parent, @NonNull T child, int layoutDirection);

    public abstract boolean layoutDependsOnBase(@NonNull CoordinatorLayout parent, @NonNull T child, @NonNull View dependency);

    public abstract boolean onStartNestedScrollBase(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View directTargetChild, @NonNull View target, int axes, int type);

    public abstract void onNestedPreScrollBase(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type);

    public abstract void onNestedScrollAcceptedBase(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View directTargetChild, @NonNull View target, int axes, int type);

    public abstract boolean onNestedPreFlingBase(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View target, float velocityX, float velocityY);

    public abstract void onStopNestedScrollBase(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T child, @NonNull View target, int type);
    }
