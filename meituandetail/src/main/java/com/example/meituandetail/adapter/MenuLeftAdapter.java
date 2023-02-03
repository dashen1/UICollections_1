package com.example.meituandetail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituandetail.R;
import com.example.meituandetail.bean.MenuTabBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuLeftAdapter extends RecyclerView.Adapter<MenuLeftAdapter.ViewHolder>{

    private final ArrayList<MenuTabBean> data;

    public void setCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    private int mCurrentPosition = 0;

    private Callback callback;

    public MenuLeftAdapter(ArrayList<MenuTabBean> mLeftData) {
        data = mLeftData;
    }

    @NonNull
    @Override
    public MenuLeftAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_details_menu_left, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        int viewPosition = position;
        holder.cb_name.setChecked(viewPosition == mCurrentPosition);
        holder.cb_name.setText(data.get(viewPosition).getName());
        holder.cb_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.onClickItem(viewPosition);
                }
            }
        });
        if (viewPosition ==data.size()-1){
            holder.view_bottom.setVisibility(View.VISIBLE);
        }else {
            holder.view_bottom.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cb_name)
        public CheckBox cb_name;
        @BindView(R.id.view_bottom)
        public View view_bottom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback{
        void onClickItem(int position);
    }
}
