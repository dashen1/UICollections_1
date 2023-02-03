package com.example.tiktolkcommentdialog.listener;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 获取软键盘的高度
 */
public class SoftKeyBoardListener {

    private View rootView;//activity的根视图
    int rootViewVisibleHeight;//记录根视图的显示高度

    private OnSoftKeyBoardChangeListener mOnSoftKeyBoardChangeListener;

    public SoftKeyBoardListener(Activity activity, OnSoftKeyBoardChangeListener mOnSoftKeyBoardChangeListener) {
        if (activity == null) return;
        this.mOnSoftKeyBoardChangeListener = mOnSoftKeyBoardChangeListener;
        //获取Activity的根视图
        rootView = activity.getWindow().getDecorView();
        if (rootView == null) return;
        //监听视图树中全局发生改变或者某个视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    public void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener mOnSoftKeyBoardChangeListener) {
        this.mOnSoftKeyBoardChangeListener = mOnSoftKeyBoardChangeListener;
        if (mOnSoftKeyBoardChangeListener == null) {
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            rootView = null;
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            //获取当前根视图在屏幕上显示的大小
            if (rootView == null) {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                return;
            }
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);
            int visibleHeight = rect.height();
            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight;
                return;
            }
            //根视图显示高度没有变化，可以看作软键盘显示/隐藏状态没有改变
            if (rootViewVisibleHeight == visibleHeight) {
                return;
            }
            //根视图显示高度变小超过200，可以看作软键盘显示了
            if (rootViewVisibleHeight - visibleHeight > 200) {
                if (mOnSoftKeyBoardChangeListener != null) {
                    mOnSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                }
                rootViewVisibleHeight = visibleHeight;
                return;
            }

            //根视图显示给变大超过200，可以看作软件隐藏了
            if (visibleHeight - rootViewVisibleHeight > 200) {
                if (mOnSoftKeyBoardChangeListener != null) {
                    mOnSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                }
                rootViewVisibleHeight = visibleHeight;
                return;
            }
        }
    };

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }
}
