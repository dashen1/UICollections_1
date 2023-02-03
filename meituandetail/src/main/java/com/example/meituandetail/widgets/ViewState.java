package com.example.meituandetail.widgets;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

public class ViewState {
    private int topMargin;
    private int bottomMargin;
    private int leftMargin;
    private int rightMargin;

    private int width;
    private int height;

    private float translationX;
    private float translationY;

    private float scaleX;
    private float scaleY;

    private float rotation;
    private float alpha;

    public ViewState scaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public ViewState scaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public ViewState alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public ViewState ws(float s) {
        this.width = (int) (width * s);
        return this;
    }

    public ViewState hs(float s) {
        this.height = (int) (height * s);
        return this;
    }

    public ViewState copy(View view) {
        this.width = view.getWidth();
        this.height = view.getHeight();
        this.translationX = view.getTranslationX();
        this.translationY = view.getTranslationY();
        this.scaleX = view.getScaleX();
        this.scaleY = view.getScaleY();
        this.rotation = view.getRotation();
        this.alpha = view.getAlpha();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        this.topMargin = layoutParams.topMargin;
        this.bottomMargin = layoutParams.bottomMargin;
        this.leftMargin = layoutParams.leftMargin;
        this.rightMargin = layoutParams.rightMargin;
        return this;
    }

    public static void stateSet(View view, int tag, ViewState vs) {
        view.setTag(tag, vs);
    }

    public static ViewState stateRead(View view, int tag) {
        ViewState vs = (ViewState) view.getTag(tag);
        return vs;
    }

    public static ViewState stateSave(View view, int tag) {
        ViewState vs = stateRead(view, tag);
        if (vs == null) {
            vs = new ViewState();
        }
        stateSet(view, tag, vs);
        return vs;
    }

    public static void stateRefresh(View view, int tag1, int tag2, float p) {
        if (view instanceof AnimationUpdateListener) {
            ((AnimationUpdateListener) view).onAnimationUpdate(tag1, tag2, p);
        } else {
            ViewState vs1 = stateRead(view, tag1);
            ViewState vs2 = stateRead(view, tag2);
            if (vs1 != null && vs2 != null) {
                if (vs1.translationX != vs2.translationX)
                    view.setTranslationX(vs1.translationX + (vs2.translationX - vs1.translationX) * p);
                if (vs1.translationY != vs2.translationY)
                    view.setTranslationY(vs1.translationY + (vs2.translationY - vs1.translationY) * p);
                if (vs1.scaleX != vs2.scaleX)
                    view.setScaleX(vs1.scaleX + (vs2.scaleX - vs1.scaleX) * p);
                if (vs1.scaleY != vs2.scaleY)
                    view.setScaleY(vs1.scaleY + (vs2.scaleY - vs1.scaleY) * p);
                if (vs1.rotation != vs2.rotation)
                    view.setRotation((vs1.rotation + (vs2.rotation - vs1.rotation) * p) % 360);
                if (vs1.alpha != vs2.alpha) view.setAlpha(vs1.alpha + (vs2.alpha - vs1.alpha) * p);

                ViewGroup.LayoutParams o = view.getLayoutParams();

                boolean lpChanged = false;
                if (vs1.width != vs2.width) {
                    o.width = (int) (vs1.width + (vs2.width - vs1.width) * p);
                    lpChanged = true;
                }
                if (vs1.height != vs2.height) {
                    o.height = (int) (vs1.height + (vs2.height - vs1.height) * p);
                    lpChanged = true;
                }
                if (o instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) o;
                    if (vs1.topMargin != vs2.topMargin) {
                        Log.e("TAG", "MarginLayoutParams");
                        layoutParams.topMargin = (int) (vs1.topMargin + (vs2.topMargin - vs1.topMargin) * p);
                        lpChanged = true;
                    }
                    if (vs1.bottomMargin != vs2.bottomMargin) {
                        layoutParams.bottomMargin = (int) (vs1.bottomMargin + (vs2.bottomMargin - vs1.bottomMargin) * p);
                        lpChanged = true;
                    }
                    if (vs1.leftMargin != vs2.leftMargin) {
                        layoutParams.leftMargin = (int) (vs1.leftMargin + (vs2.leftMargin - vs1.leftMargin) * p);
                        lpChanged = true;
                    }
                    if (vs1.rightMargin != vs2.rightMargin) {
                        layoutParams.rightMargin = (int) (vs1.rightMargin + (vs2.rightMargin - vs1.rightMargin) * p);
                        lpChanged = true;
                    }
                }
                if (lpChanged) view.setLayoutParams(o);
            }
        }
    }

    public static void statesChangeByAnimation(ArrayList<View> views, int startTag, int endTag, float start, float end, AnimationUpdateListener updateCallback, AnimatorListenerAdapter updateStateListener, long duration, long startDelay) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float p = (float) animation.getAnimatedValue();
                if (updateCallback != null) {
                    updateCallback.onAnimationUpdate(startTag, endTag, p);
                }
                for (View view : views) {
                    stateRefresh(view, startTag, endTag, p);
                }
            }
        });
        if (updateStateListener!=null){
            animator.addListener(updateStateListener);
        }
        animator.start();
    }

    public interface AnimationUpdateListener {
        void onAnimationUpdate(int tag1, int tag2, float p);
    }
}
