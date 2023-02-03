package com.example.meituandetail.ui.shop;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.meituandetail.R;
import com.example.meituandetail.utils.SourceUtil;
import com.example.meituandetail.widgets.MyViewPager;

import java.sql.SQLOutput;

public class ShopContentBehavior extends CoordinatorLayout.Behavior<ShopContentLayout> {

    private ShopTitleLayout mShopTitleLayoutView;
    private ShopDiscountLayout mShopDiscountLayoutView;
    private ShopContentLayout mShopContentLayoutView;
    private ShopPriceLayout mShopPriceLayoutView;

    private Scroller mScroller;
    private int mScrollDuration = 800;
    private int mSimpleTopDistance = 0;
    private boolean mIsScrollToFullFood = false; // 上滑显示商品菜单
    private boolean mIsScrollToHideFood = false; // 下滑显示商店详情
    private int mVerticalPagingTouch = 0; // 菜单竖项列表(商品，评价，商家)内容的触摸滑动距离
    private int mHorizontalPagingTouch = 0; // 菜单横项列表(推荐商品)内容的触摸滑动距离
    private boolean mIsScrollRecommends = false;

    private MyViewPager mVpMain;

    private int mPagingTouchSlop = SourceUtil.dp(5);

    private int mFoldingDy;
    private Handler mHandler = new Handler();

    private Runnable mFlingRunnable = new Runnable() {
        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                mShopContentLayoutView.setTranslationY((float) mScroller.getCurrY());
                mShopDiscountLayoutView.effectedByOffset(mShopContentLayoutView.getTranslationY());
                mShopPriceLayoutView.effectedByOffset(mShopContentLayoutView.getTranslationY());
                mHandler.post(this);
            } else {
                mIsScrollToHideFood = false;
            }
        }
    };

    private Runnable mFoldingRunnable = new Runnable() {
        @Override
        public void run() {
            if (mFoldingDy < mSimpleTopDistance) {
                mFoldingDy += 4;//影响折叠速度
                layoutFolding(mFoldingDy);
                mHandler.post(this);
            } else {
                //折叠结束
            }
        }
    };


    public ShopContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mScroller = new Scroller(context);
    }

    /**
     * 是否处于惯性滑动且有触摸动作插入
     */
    private boolean mIsFlingAndDown = false;


    private ShopDiscountLayout.AnimatorListenerAdapter1 mAnimListener = new ShopDiscountLayout.AnimatorListenerAdapter1() {
        @Override
        public void onAnimationStart(Animator animator, boolean toExpand) {
            //防止惯性滑动影响展开/闭合动画 所以要设置为true
            mIsFlingAndDown = true;
            if (toExpand) {
                float defaultDisplayHeight = mShopContentLayoutView.getHeight() - mSimpleTopDistance;
                mScroller.startScroll(
                        0,
                        (int) mShopContentLayoutView.getTranslationY(),
                        0,
                        (int) (defaultDisplayHeight - mShopContentLayoutView.getTranslationY()),
                        mScrollDuration
                );
            } else {
                mScroller.startScroll(
                        0,
                        (int) mShopContentLayoutView.getTranslationY(),
                        0,
                        (int) (-mShopContentLayoutView.getTranslationY()),
                        mScrollDuration
                );
            }
            mHandler.post(mFlingRunnable);
            mIsScrollToHideFood = true;
        }
    };

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull ShopContentLayout child, int layoutDirection) {
        if (mShopContentLayoutView == null) {
            mShopContentLayoutView = child;
            mShopContentLayoutView.setShopContentBehavior(this);
            mVpMain = child.findViewById(R.id.vp_main);
            mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        mShopPriceLayoutView.setVisibility(View.VISIBLE);
                    } else {
                        mShopPriceLayoutView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            CoordinatorLayout.LayoutParams priceLayoutParams = (CoordinatorLayout.LayoutParams) mShopPriceLayoutView.getLayoutParams();
            priceLayoutParams.topMargin = parent.getHeight() - mShopPriceLayoutView.getHeight();
            mShopPriceLayoutView.setLayoutParams(priceLayoutParams);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mShopContentLayoutView.getLayoutParams();
            if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
                mSimpleTopDistance = lp.topMargin - mShopTitleLayoutView.getHeight();
                lp.height = parent.getHeight() - mShopTitleLayoutView.getHeight();
                child.setLayoutParams(lp);
                return true;
            }
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ShopContentLayout child, @NonNull View dependency) {

        switch (dependency.getId()) {
            case R.id.layout_title:
                mShopTitleLayoutView = (ShopTitleLayout) dependency;
                break;
            case R.id.layout_discount:
                mShopDiscountLayoutView = (ShopDiscountLayout) dependency;
                mShopDiscountLayoutView.animListener = mAnimListener;
                break;
            case R.id.layout_price:
                mShopPriceLayoutView = (ShopPriceLayout) dependency;
                break;
            default:
                return false;
        }
        return true;
    }

    //当被依赖的 View 状态改变时回调
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ShopContentLayout child, @NonNull View dependency) {
        return true;
    }
    /**
     * 是否处于惯性滑动且有触摸动作插入
     */

    /**
     * 嵌套滑动开始（ACTION_DOWN） 确定behavior是否要监听此次事件
     */

    private boolean mIsFling = false;

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ShopContentLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        if (mIsFling && type == ViewCompat.TYPE_TOUCH) {
            //处于惯性滑动且有触摸插入动作
            mIsFlingAndDown = true;
        }
        return true;
    }

    /**
     * 嵌套滑动进行中，要监听的View将要滑动，滑动事件即将被消费（但最终被谁消费，可以通过代码控制）
     *
     * @param type = ViewCompat.TYPE_TOUCH 表示是触摸引起的滚动 = ViewCompat.TYPE_NON_TOUCH 表示是触摸后的惯性引起的滚动
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ShopContentLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (mIsScrollToHideFood) {
            consumed[1] = dy;
            return;//scroller滑动中... do nothing
        }
        mVerticalPagingTouch += dy;
        if (mVpMain.isScrollable() && Math.abs(mVerticalPagingTouch) > mPagingTouchSlop) {
            mVpMain.setScrollable(false);//屏蔽pager横向滑动的影响
        }
        if (type == ViewCompat.TYPE_NON_TOUCH && mIsFlingAndDown) {
            //当处于惯性滑动时，有触摸动作进入，屏蔽惯性滑动，以防止滚动错乱
            consumed[1] = dy;
            return;
        }
        //todo 这里待会记得测试一下是不是向上惯性滑动 mIsFlingAndDown -》是否时惯性向下滑动
        //判断此时由于触摸引起的滚动还是惯性引起的滚动
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            mIsScrollToFullFood = true;
        }
        mHorizontalPagingTouch += dx;
        //todo 什么意思？
        if ((child.getTranslationY() < 0 || (child.getTranslationY() == 0F && dy > 0))
                && !child.getScrollableView().canScrollVertically(-1)) {

            float effect = mShopTitleLayoutView.effectedByOffset(dy);
            float transY = -mSimpleTopDistance * effect;
            mShopDiscountLayoutView.setTranslationY(transY);
            if (transY != child.getTranslationY()) {
                child.setTranslationY(transY);
                consumed[1] = dy;
            }
            //菜品已经滚到底了，也就是页面的开始显示的样子 且 可以
            //child.getScrollableView().canScrollVertically(-1) 表示是否能向下滚动，false表示已经滚动到顶部了，不能再往下滚动了
        } else if ((child.getTranslationY() > 0 || (child.getTranslationY() == 0F && dy < 0))
                && !child.getScrollableView().canScrollVertically(-1)) {
            if (mIsScrollToFullFood) {
                child.setTranslationY(0F);
            } else {
                child.setTranslationY(child.getTranslationY() - dy);
                mShopDiscountLayoutView.effectedByOffset(child.getTranslationY());
                mShopPriceLayoutView.effectedByOffset(child.getTranslationY());
            }
            consumed[1] = dy;
        } else {
            //折叠状态
            if (child.getRootScrollView() != null && (child.getScrollableView() instanceof RecyclerView)) {
                //这个是防止按着BannerView滚动时导致ScrollView速度翻倍
                if (dy > 0) {
                    View view = child.getRootScrollView();
                    if (view != null) {
                        view.setScrollY(view.getScrollY() + dy);
                    }
                }
            }
        }
    }

    /**
     * 接受嵌套滚动
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     */
    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ShopContentLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        mScroller.abortAnimation();
        mIsScrollToHideFood = false;
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    /**
     * 要监听的子View即将惯性滑动（开始非实际触摸的惯性滑动） type一定等于1
     */
    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ShopContentLayout child, @NonNull View target, float velocityX, float velocityY) {
        mIsFling = true;
        mIsFlingAndDown = false;
        return onUserStopDragging();
    }

    /**
     * 嵌套滑动结束（ACTION_UP或者ACTION_CANCEL）
     *
     * @return
     */

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ShopContentLayout child, @NonNull View target, int type) {
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            //惯性滑动结束
            mIsFling = false;
        }
        mIsScrollToFullFood = false;
        mVerticalPagingTouch = 0;
        mHorizontalPagingTouch = 0;
        mIsScrollRecommends = false;
        mVpMain.setScrollable(true);
        if (!mIsScrollToHideFood) {
            onUserStopDragging();
        }
    }


    private boolean onUserStopDragging() {
        if (mShopContentLayoutView.getTranslationY() <= 0F) {
            return false;
        }
        float defaultDisplayHeight = mShopContentLayoutView.getHeight() - mSimpleTopDistance;
        if (defaultDisplayHeight * 0.4F > mShopContentLayoutView.getTranslationY()) {
            mScroller.startScroll(
                    0,
                    (int) mShopContentLayoutView.getTranslationY(),
                    0,
                    (int) (-mShopContentLayoutView.getTranslationY()),
                    mScrollDuration
            );
            mShopDiscountLayoutView.switchLayout(false, true);
        } else {
            mScroller.startScroll(
                    0,
                    (int) mShopContentLayoutView.getTranslationY(),
                    0,
                    (int) (defaultDisplayHeight - mShopContentLayoutView.getTranslationY()),
                    mScrollDuration
            );
            mShopDiscountLayoutView.switchLayout(true, true);
        }
        mHandler.post(mFlingRunnable);
        mIsScrollToHideFood = true;
        return true;
    }

    /**
     * 折叠
     */
    public void fold() {
        //一处惯性滑动 防止重复设置 selfView.setTranslationY()
        mScroller.abortAnimation();
        mHandler.removeCallbacks(mFlingRunnable);
        //开始折叠
        mFoldingDy = 0;
        mHandler.post(mFoldingRunnable);
    }

    /**
     * 是否展开
     */
    public boolean isExpanded() {
        return (-mShopContentLayoutView.getTranslationY()) < mSimpleTopDistance;
    }

    /**
     * 通过动画使布局折叠
     */

    private void layoutFolding(int dy) {
        mVerticalPagingTouch += dy;
        float effect = mShopTitleLayoutView.effectedByOffset(dy);
        float transY = -mSimpleTopDistance * effect;
        mShopDiscountLayoutView.setTranslationY(transY);
        mShopContentLayoutView.setTranslationY(transY);
    }
}
