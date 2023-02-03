package com.example.meituandetail.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.meituandetail.MainActivity;
import com.example.meituandetail.R;
import com.example.meituandetail.ui.shop.ShopContentLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluateFragment extends Fragment implements ShopContentLayout.ScrollableProvider {

    @BindView(R.id.sv_main)
    public NestedScrollView sv_main;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_details_evaluate, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public View getScrollableView() {
        return sv_main;
    }
}
