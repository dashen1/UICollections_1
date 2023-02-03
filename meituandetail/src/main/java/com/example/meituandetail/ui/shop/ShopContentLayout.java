package com.example.meituandetail.ui.shop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.meituandetail.App;
import com.example.meituandetail.MainActivity;
import com.example.meituandetail.R;
import com.example.meituandetail.adapter.ViewPagerAdapter;
import com.example.meituandetail.fragment.EvaluateFragment;
import com.example.meituandetail.fragment.MenuFragment;
import com.example.meituandetail.fragment.ShopFragment;
import com.example.meituandetail.widgets.MyViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopContentLayout extends ConstraintLayout {

    @BindView(R.id.vp_main)
    public MyViewPager vp_main;

    @BindView(R.id.tab_layout)
    public SlidingTabLayout tab_layout;

    private ShopContentBehavior mShopContentBehavior;

    private ArrayList<Fragment> mFragmentList;
    public String[] mTitles = {"点菜", "评价", "商家"};


    public ShopContentLayout(@NonNull Context context) {
        this(context,null);
    }

    public ShopContentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShopContentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shop_details_content, this);
        ButterKnife.bind(view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //点菜、评价、商家 Fragment
        mFragmentList = new ArrayList<>();
        mFragmentList.add(MenuFragment.getInstance(new MenuFragment.LayoutExpandControl() {
            @Override
            public void fold() {
                if (mShopContentBehavior != null) {
                    mShopContentBehavior.fold();
                }
            }

            @Override
            public boolean isExpanded() {
                if (mShopContentBehavior != null) {
                    return mShopContentBehavior.isExpanded();
                } else {
                    return false;
                }
            }
        }));
        mFragmentList.add(new ShopFragment());
        mFragmentList.add(new EvaluateFragment());
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(((AppCompatActivity)getContext()).getSupportFragmentManager(), mFragmentList);
        vp_main.setAdapter(pagerAdapter);
        vp_main.setOffscreenPageLimit(mFragmentList.size());
        tab_layout.setViewPager(vp_main, mTitles);

    }

    public void setShopContentBehavior(ShopContentBehavior shopContentBehavior) {
        mShopContentBehavior = shopContentBehavior;
    }

    public View getScrollableView() {
        View view = ((ScrollableProvider) mFragmentList.get(vp_main.getCurrentItem())).getScrollableView();
        return view;
    }

    public View getRootScrollView(){
        View view = ((ScrollableProvider) mFragmentList.get(vp_main.getCurrentItem())).getRootScrollView();
        return view;
    }

    public interface ScrollableProvider {
        View getScrollableView();

        default View getRootScrollView() {
            return null;
        }
    }
}
