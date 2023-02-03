package com.example.swipecardview;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swipecardview.adapter.UniversalAdapter;
import com.example.swipecardview.entity.SwipeCardBean;

import java.util.List;

public class SwipeCardCallback extends ItemTouchHelper.SimpleCallback {

    private RecyclerView mRV;
    private UniversalAdapter<SwipeCardBean> mUniversalAdapter;
    private List<SwipeCardBean> mData;

    public SwipeCardCallback(RecyclerView mRV, UniversalAdapter<SwipeCardBean> mUniversalAdapter, List<SwipeCardBean> mData) {
        super(0, 15);
        this.mRV = mRV;
        this.mUniversalAdapter = mUniversalAdapter;
        this.mData = mData;
    }

    //drag
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    // item 滑出去后回调
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        SwipeCardBean remove = mData.remove(viewHolder.getLayoutPosition());//第八个
        mData.add(0, remove);
        mUniversalAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX + dY * dY);
        double fraction = distance / maxDistance;

        if (fraction > 1) {
            fraction = 1;
        }
        //显示的个数 4个
        int itemCount = recyclerView.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View view = recyclerView.getChildAt(i);
            int level = itemCount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    view.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                }
            }
        }
    }

    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return 1000;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.2f;
    }
}
