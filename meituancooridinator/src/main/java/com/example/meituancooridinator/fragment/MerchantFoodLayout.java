package com.example.meituancooridinator.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituancooridinator.R;
import com.example.meituancooridinator.adapter.ItemAdapter;
import com.example.meituancooridinator.business.MerchantPageLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerchantFoodLayout extends FrameLayout implements MerchantPageLayout.ScrollableViewProvider{

    @BindView(R.id.vRecycler)
    public RecyclerView vRecycler;

    List<String> mDatas = new ArrayList<>();
    private ItemAdapter mAdapter;
    private Context mContext;

    public MerchantFoodLayout(@NonNull Context context) {
        this(context,null);
    }

    public MerchantFoodLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MerchantFoodLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.merchant_page_food_layout, this);
        ButterKnife.bind(view);
        initialData();
    }

    private void initialData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        vRecycler.addItemDecoration(itemDecoration);
        vRecycler.setLayoutManager(layoutManager);

        for (int i = 0; i < 50; i++) {
            String s = String.format("我是第%d个item",i);
            mDatas.add(s);
        }

        mAdapter = new ItemAdapter(mContext, mDatas);
        vRecycler.setAdapter(mAdapter);

    }

    @Override
    public View getScrollableView() {
        return vRecycler;
    }
}
