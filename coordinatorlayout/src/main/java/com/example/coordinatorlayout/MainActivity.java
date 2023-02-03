package com.example.coordinatorlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coordinatorlayout.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    public RecyclerView recycleView;
    List<String> mDatas = new ArrayList<>();
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recycleView.addItemDecoration(itemDecoration);
        recycleView.setLayoutManager(layoutManager);

        for (int i = 0; i < 50; i++) {
            String s = String.format("我是第%d个item",i);
            mDatas.add(s);
        }

        mAdapter = new ItemAdapter(MainActivity.this, mDatas);
        recycleView.setAdapter(mAdapter);
    }
}