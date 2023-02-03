package com.example.meituancooridinator.adapter;

import android.content.Context;
import android.widget.TextView;


import com.example.meituancooridinator.R;

import java.util.List;

public class ItemAdapter extends BaseRecyclerAdapter<String>{


    public ItemAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_string, datas);
    }

    @Override
    public void convert(BaseRecyclerAdapter<String>.BaseRecyclerHolder holder, String item, int position) {
        TextView tv = holder.getView(R.id.tv);
        tv.setText(item);
    }
}
