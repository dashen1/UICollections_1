package com.example.swipecardview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swipecardview.R;

import java.util.ArrayList;
import java.util.List;

public abstract class UniversalAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected ViewGroup mRV;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public UniversalAdapter(Context mContext, List<T> mData, int mLayoutId) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder = MyViewHolder.get(this.mContext, (View) null, parent, this.mLayoutId);
        if (null == this.mRV){
            this.mRV = parent;
        }
        return holder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder){
        return viewHolder.getAdapterPosition();
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            this.convert(holder,this.mData.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mData != null ? this.mData.size() : 0;
    }

    public abstract void convert(MyViewHolder var1, T var2);

    public void setData(List<T> list){
        if (this.mData !=null){
            if (null !=list){
                ArrayList temp = new ArrayList();
                temp.addAll(list);
                this.mData.clear();
                this.mData.addAll(temp);
            }else {
                this.mData.clear();
            }
        }else {
            this.mData = list;
        }
        this.notifyDataSetChanged();
    }
    public void remove(int i) {
        if(null != this.mData && this.mData.size() > i && i > -1) {
            this.mData.remove(i);
            this.notifyDataSetChanged();
        }

    }

    public void addData(List<T> list) {
        if (null != list) {
            ArrayList temp = new ArrayList();
            temp.addAll(list);
            if (this.mData != null) {
                this.mData.addAll(temp);
            } else {
                this.mData = temp;
            }

            this.notifyDataSetChanged();
        }

    }
}
