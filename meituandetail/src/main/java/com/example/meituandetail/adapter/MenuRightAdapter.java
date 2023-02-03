package com.example.meituandetail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituandetail.R;
import com.example.meituandetail.bean.MenuChildBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuRightAdapter extends RecyclerView.Adapter<MenuRightAdapter.ViewHolder> {

    private ArrayList<MenuChildBean> data;

    public MenuRightAdapter(ArrayList<MenuChildBean> mRightData) {
        data = mRightData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_details_menu_right, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == data.size() - 1) {
            holder.view_bottom.setVisibility(View.VISIBLE);
        }
        holder.view_bottom.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_bottom)
        public View view_bottom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
