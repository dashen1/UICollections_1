package com.example.swipecardview;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swipecardview.adapter.MyViewHolder;
import com.example.swipecardview.adapter.UniversalAdapter;
import com.example.swipecardview.entity.SwipeCardBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_main)
    RecyclerView rv_main;


    private UniversalAdapter<SwipeCardBean> mUniversalAdapter;
    private List<SwipeCardBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        rv_main.setLayoutManager(new SwipeCardLayoutManager());
        mData = SwipeCardBean.initData();
        mUniversalAdapter = new UniversalAdapter<SwipeCardBean>(MainActivity.this, mData, R.layout.item_swipe_card) {
            @Override
            public void convert(MyViewHolder viewHolder, SwipeCardBean swipeCardBean) {
                viewHolder.setText(R.id.tv_item_name, swipeCardBean.getName());
                viewHolder.setText(R.id.tv_item_percent, swipeCardBean.getPosition() + "/" + mData.size());
//                Glide.with(MainActivity.this)
//                        .load(swipeCardBean.getUrl())
//                        .into((ImageView) viewHolder.getView(R.id.iv_item_card));
            }
        };
        rv_main.setAdapter(mUniversalAdapter);
        CardConfig.initConfig(this);
        SwipeCardCallback callback = new SwipeCardCallback(rv_main, mUniversalAdapter, mData);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv_main);
    }
}