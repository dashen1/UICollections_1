package com.example.meituancooridinator.business;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.meituancooridinator.R;
import com.example.meituancooridinator.fragment.MerchantCommentLayout;
import com.example.meituancooridinator.fragment.MerchantFoodLayout;
import com.example.meituancooridinator.fragment.MerchantInfoLayout;
import com.example.meituancooridinator.view.SmartTabLayout1;
import com.example.meituancooridinator.view.ViewPager2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerchantPageLayout extends LinearLayout {

    private MerchantPageAdapter pagerAdapter;
    private Context mContext;

    @BindView(R.id.vPager)
    public ViewPager2 vPager;
    @BindView(R.id.vSmartTab)
    public SmartTabLayout1 vSmartTab;

    public MerchantPageLayout(Context context) {
        this(context, null);
    }

    public MerchantPageLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MerchantPageLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.merchant_page_layout, this);
        ButterKnife.bind(view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        pagerAdapter = new MerchantPageAdapter(mContext);
        vPager.setAdapter(pagerAdapter);
        vSmartTab.setViewPager(vPager);
    }

    public boolean canScrollVertically(){
        View view = ((ScrollableViewProvider) pagerAdapter.getItem(vPager.getCurrentItem())).getScrollableView();
        return view.canScrollVertically(-1);
    }

    public class MerchantPageAdapter extends PagerAdapter {

        private Context context;

        private MerchantFoodLayout layFood;
        private MerchantInfoLayout layInfo;
        private MerchantCommentLayout layComment;

        public MerchantPageAdapter(Context context) {
            layFood = new MerchantFoodLayout(context);
            layInfo = new MerchantInfoLayout(context);
            layComment = new MerchantCommentLayout(context);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title;
            switch (position){
                case 0: title = "点菜";
                break;
                case 1: title = "评论(999+)";
                break;
                case 2: title = "商家";
                break;
                default: title = "";
                break;

            }
            return title;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View item = getItem(position);
            container.addView(item);
            return item;
        }

        public View getItem(int position){
            View view;
            switch (position){
                case 0: view = layFood;
                break;
                case 1: view = layComment;
                break;
                case 2: view = layInfo;
                break;
                default:
                    throw new RuntimeException("getItem error position :"+ position);
            }
            return view;
        }
    }


    public interface ScrollableViewProvider{
        public View getScrollableView();
    }
}



