package com.example.meituandetail.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class FloatDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private int mDecorationLayoutRes;
    private DecorationCallback mCallback;

    private View mDecorationView;
    private int mDecorationHeight;

    public FloatDecoration(Context mContext, RecyclerView mRecyclerView, int mDecorationLayoutRes, DecorationCallback mDecorationCallback) {
        this.mContext = mContext;
        this.mRecyclerView = mRecyclerView;
        this.mDecorationLayoutRes = mDecorationLayoutRes;
        this.mCallback = mDecorationCallback;
        mDecorationView = LayoutInflater.from(mContext).inflate(mDecorationLayoutRes, mRecyclerView, false);
        if (mDecorationView.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mDecorationHeight = 0;
        } else {
            mDecorationHeight = mDecorationView.getLayoutParams().height;
        }
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (isShowDecoration(position)) {
            outRect.top = mDecorationHeight;
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (mDecorationHeight == 0) return;
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        String preDecoration = "";
        String currentDecoration = "";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preDecoration = currentDecoration;
            currentDecoration = mCallback.getDecorationFlag(position);
            if (Objects.equals(preDecoration, currentDecoration)) {
                continue;
            }
            int viewBottom = view.getBottom();
            float top = Math.max(mDecorationHeight, view.getTop());
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                String nextDecoration = mCallback.getDecorationFlag(position + 1);
                //组内最后一个view进入了header
                if (!Objects.equals(nextDecoration, currentDecoration) && viewBottom < top) {
                    top = (float) viewBottom;
                }
            }
            mCallback.onBindView(mDecorationView, position);
            //下面代码作用是？

            mDecorationView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            mDecorationView.layout(left, 0, right, mDecorationHeight);
            mDecorationView.setDrawingCacheEnabled(true);
            c.drawBitmap(
                    mDecorationView.getDrawingCache(),
                    (float) left,
                    top - mDecorationHeight,
                    null
            );
            mDecorationView.setDrawingCacheEnabled(false);
            mDecorationView.destroyDrawingCache();
        }
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    public boolean isShowDecoration(int position) {
        if (position == 0) {
            return true;
        } else {
            String preDecoration = mCallback.getDecorationFlag(position - 1);
            String currentDecoration = mCallback.getDecorationFlag(position);
            return !Objects.equals(preDecoration, currentDecoration);
        }
    }

    public interface DecorationCallback {
        String getDecorationFlag(int position);

        void onBindView(View decorationView, int position);
    }
}
