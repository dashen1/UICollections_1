package com.example.meituandetail.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituandetail.MainActivity;
import com.example.meituandetail.R;
import com.example.meituandetail.adapter.FloatDecoration;
import com.example.meituandetail.adapter.MenuLeftAdapter;
import com.example.meituandetail.adapter.MenuRightAdapter;
import com.example.meituandetail.bean.MenuChildBean;
import com.example.meituandetail.bean.MenuTabBean;
import com.example.meituandetail.ui.shop.ShopContentLayout;
import com.example.meituandetail.widgets.CenterLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuFragment extends Fragment implements ShopContentLayout.ScrollableProvider {

    @BindView(R.id.rv_left)
    public RecyclerView rv_left;
    @BindView(R.id.rv_right)
    public RecyclerView rv_right;
    @BindView(R.id.scrollView)
    public NestedScrollView scrollView;

    @BindView(R.id.fl_banner)
    public FrameLayout fl_banner;

    private ArrayList<MenuTabBean> mLeftData;
    private ArrayList<MenuChildBean> mRightData;
    private MenuLeftAdapter mLeftAdapter;
    private LayoutExpandControl mLayoutControl;
    private boolean mIsClickFold;
    private RecyclerView.State mRvState;
    private CenterLayoutManager mLeftLayoutManager;

    public static MenuFragment getInstance(LayoutExpandControl layoutExpandControl) {
        Log.e("TAG", "getInstance");
        MenuFragment fragment = new MenuFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.setLayoutExpandControl(layoutExpandControl);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_details_menu, null);
        Log.e("TAG", "onCreateView");
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("TAG", "onViewCreated");
        mLeftData = new ArrayList<>();
        mRightData = new ArrayList<>();
        mRvState = new RecyclerView.State();
        mLeftAdapter = new MenuLeftAdapter(mLeftData);
        mLeftLayoutManager = new CenterLayoutManager(getContext());

        rv_left.setLayoutManager(mLeftLayoutManager);
        rv_left.setAdapter(mLeftAdapter);
        MenuRightAdapter rightAdapter = new MenuRightAdapter(mRightData);
        rv_right.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_right.setAdapter(rightAdapter);
        //左边RecyclerView item点击事件监听
        mLeftAdapter.setCallback(new MenuLeftAdapter.Callback() {
            @Override
            public void onClickItem(int position) {
                for (int i = 0; i < mRightData.size(); i++) {
                    if (mLeftData.get(position).getName() == mRightData.get(i).getGroupName()) {
                        //未折叠时进行折叠
                        if (mLayoutControl.isExpanded()) {
                            mLayoutControl.fold();
                            mIsClickFold = true;
                        }
                        if (rv_right.getLayoutParams().height != scrollView.getHeight()) {
                            ViewGroup.LayoutParams rv_rightLayoutParams = rv_right.getLayoutParams();
                            ViewGroup.LayoutParams rv_leftLayoutParams = rv_left.getLayoutParams();
                            rv_rightLayoutParams.height = scrollView.getHeight();
                            rv_leftLayoutParams.height = scrollView.getHeight();
                            rv_right.setLayoutParams(rv_rightLayoutParams);
                            rv_left.setLayoutParams(rv_leftLayoutParams);
                        }
                        //右边菜品 RecyclerView 将指定item滚动到可见第一条
                        LinearLayoutManager layoutManager = (LinearLayoutManager) rv_right.getLayoutManager();
                        if (layoutManager != null) {
                            layoutManager.scrollToPositionWithOffset(i, 0);
                        }
                        mLeftAdapter.setCurrentPosition(position);
                        mLeftAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });

        //右边 RecyclerView添加悬浮吸顶装饰
        rv_right.addItemDecoration(
                new FloatDecoration(
                        getContext(),
                        rv_right,
                        R.layout.item_shop_details_menu_right_group,
                        new FloatDecoration.DecorationCallback() {
                            @Override
                            public String getDecorationFlag(int position) {
                                return mRightData.get(position).getGroupName();
                            }

                            @Override
                            public void onBindView(View decorationView, int position) {
                                //装饰 View 数据绑定
                                ((TextView) decorationView.findViewById(R.id.tv_group_name)).setText(mRightData.get(position).getGroupName());
                            }
                        }));

        //右边 RecyclerView 滚动监听
        rv_right.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //实现右边滚动联动左边 RecyclerView
                int position = ((LinearLayoutManager) rv_right.getLayoutManager()).findFirstVisibleItemPosition();
                if (mLeftData.get(mLeftAdapter.getCurrentPosition()).getName() != mRightData.get(position).getGroupName()) {
                    for (int i = 0; i < mLeftData.size(); i++) {
                        if (mLeftData.get(i).getName() == mRightData.get(position).getGroupName()) {
                            mLeftAdapter.setCurrentPosition(i);
                            mLeftAdapter.notifyDataSetChanged();
                            mLeftLayoutManager.smoothScrollToPosition(
                                    rv_left,
                                    mRvState,
                                    mLeftAdapter.getCurrentPosition()
                            );
                            break;
                        }
                    }
                }
            }
        });

        //根据屏幕实际宽高设置两个RecyclerView的高度为固定值
        ViewGroup.LayoutParams rv_rightLayoutParams = rv_right.getLayoutParams();
        ViewGroup.LayoutParams rv_leftLayoutParams = rv_left.getLayoutParams();
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                rv_rightLayoutParams.height = scrollView.getHeight();
                rv_leftLayoutParams.height = scrollView.getHeight();
                rv_right.setLayoutParams(rv_rightLayoutParams);
                rv_left.setLayoutParams(rv_leftLayoutParams);
            }
        });

        //记录rv_left被触摸还是rv_right被触摸
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN && v.getId() == R.id.rv_right) {
                    mIsTouchRvRight = true;
                } else if (action == MotionEvent.ACTION_UP && v.getId() == R.id.rv_right) {
                    mIsTouchRvRight = false;
                } else if (action == MotionEvent.ACTION_DOWN && v.getId() == R.id.rv_left) {
                    mIsTouchRvLeft = true;
                } else if (action == MotionEvent.ACTION_UP && v.getId() == R.id.rv_left) {
                    mIsTouchRvLeft = false;
                }
                return false;
            }
        };
        rv_right.setOnTouchListener(onTouchListener);
        rv_left.setOnTouchListener(onTouchListener);
        //scrollView滚动监听
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int dy = scrollY - oldScrollY;
                //上滑时，如果bannerView尚未被滚出屏幕，则不允许左右两个 RecyclerView滚动
                //（通过 offsetChildrenVertical(dy) 实现两个rv未滚动的假象）
                if (dy > 0) {
                    if (scrollY < fl_banner.getHeight()) {
                        if (mIsTouchRvRight) {
                            rv_right.offsetChildrenVertical(dy);
                        }
                        if (mIsTouchRvLeft) {
                            rv_left.offsetChildrenVertical(dy);
                        }
                    }
                }
            }
        });

        //添加假数据
        getData();
    }

    @Override
    public View getScrollableView() {
        //如果左/右边RecyclerView还能往上滚，则将左/右边的recyclerview暴露出去用来判断滑动
        if (rv_right.canScrollVertically(-1)) {
            return rv_right;
        } else if (rv_left.canScrollVertically(-1)) {
            return rv_left;
        } else {
            return scrollView;
        }
    }


    @Override
    public View getRootScrollView() {
        return scrollView;
    }


    private void getData() {
        //假数据
        mLeftData.add(new MenuTabBean("收藏福利"));
        mLeftData.add(new MenuTabBean("一人食"));
        mLeftData.add(new MenuTabBean("新品尝鲜"));
        mLeftData.add(new MenuTabBean("推荐"));
        mLeftData.add(new MenuTabBean("折扣"));
        mLeftData.add(new MenuTabBean("买一送一"));
        mLeftData.add(new MenuTabBean("精选套餐"));
        mLeftData.add(new MenuTabBean("企业团餐"));
        mLeftData.add(new MenuTabBean("意面小吃"));
        mLeftData.add(new MenuTabBean("下午时光"));
        mLeftData.add(new MenuTabBean("卡券专用"));
        mLeftData.add(new MenuTabBean("饮品"));
        mLeftData.add(new MenuTabBean("收藏福利1"));
        mLeftData.add(new MenuTabBean("一人食1"));
        mLeftData.add(new MenuTabBean("新品尝鲜1"));
        mLeftData.add(new MenuTabBean("推荐1"));
        mLeftData.add(new MenuTabBean("折扣1"));
        mLeftData.add(new MenuTabBean("买一送一1"));
        mLeftData.add(new MenuTabBean("精选套餐1"));
        mLeftData.add(new MenuTabBean("企业团餐1"));
        mLeftData.add(new MenuTabBean("意面小吃1"));
        mLeftData.add(new MenuTabBean("下午时光1"));
        mLeftData.add(new MenuTabBean("卡券专用1"));
        mLeftData.add(new MenuTabBean("饮品1"));
        for (int i = 0; i < mLeftData.size(); i++) {
            mRightData.add(new MenuChildBean(mLeftData.get(i).getName(), ""));
            mRightData.add(new MenuChildBean(mLeftData.get(i).getName(), ""));
            mRightData.add(new MenuChildBean(mLeftData.get(i).getName(), ""));
        }
        rv_left.getAdapter().notifyDataSetChanged();
        rv_right.getAdapter().notifyDataSetChanged();
    }


    private boolean mIsTouchRvRight = false;
    private boolean mIsTouchRvLeft = false;

    private void setLayoutExpandControl(LayoutExpandControl layoutExpandControl) {
        mLayoutControl = layoutExpandControl;
    }

    public interface LayoutExpandControl {
        void fold();

        boolean isExpanded();
    }
}
