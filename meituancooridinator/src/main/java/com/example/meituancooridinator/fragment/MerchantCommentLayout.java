package com.example.meituancooridinator.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituancooridinator.R;
import com.example.meituancooridinator.business.MerchantPageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerchantCommentLayout extends FrameLayout implements MerchantPageLayout.ScrollableViewProvider {

    @BindView(R.id.vRecycler)
    public RecyclerView vRecycler;

    public MerchantCommentLayout(@NonNull Context context) {
        this(context,null);
    }

    public MerchantCommentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MerchantCommentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.merchant_page_cell_layout, this);
        ButterKnife.bind(view);
        initialData();
    }

    private void initialData() {

    }

    @Override
    public View getScrollableView() {
        return vRecycler;
    }
}
