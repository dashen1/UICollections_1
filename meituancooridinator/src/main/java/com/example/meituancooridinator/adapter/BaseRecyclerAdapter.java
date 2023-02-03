package com.example.meituancooridinator.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mDatas;
    protected Context mContext;
    protected final int mItemLayoutId;

    public BaseRecyclerAdapter(Context context, int itemLayoutId) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = new ArrayList<>();
    }

    public BaseRecyclerAdapter(Context context, int itemLayoutId, List<T> datas) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = datas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
        return new BaseRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseRecyclerHolder baseHolder = (BaseRecyclerHolder)holder;
        convert(baseHolder, mDatas.get(position), position);
    }

    public abstract void convert(BaseRecyclerHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return isEmpty() ? 0 : mDatas.size();
    }

    public boolean isEmpty() {
        return mDatas == null || mDatas.size() == 0;
    }

    public class BaseRecyclerHolder extends RecyclerView.ViewHolder{

        private SparseArray<View> mViews;
        private View mConvertView;
        Context mContext;

        public BaseRecyclerHolder(@NonNull View itemView) {
            this(itemView, itemView.getContext());
        }

        public BaseRecyclerHolder(View itemView, Context context) {
            super(itemView);
            mContext=context;
            mConvertView = itemView;
            mViews = new SparseArray<>();
            mConvertView.setTag(this);
        }

        public <T extends View> T getView(int viewId){
            View view = mViews.get(viewId);
            if (view == null){
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId,view);
            }
            return (T) view;
        }
    }
}
