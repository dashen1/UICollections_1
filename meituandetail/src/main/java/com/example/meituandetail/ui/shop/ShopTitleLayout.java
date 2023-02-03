package com.example.meituandetail.ui.shop;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.meituandetail.R;
import com.example.meituandetail.utils.SourceUtil;
import com.example.meituandetail.utils.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopTitleLayout extends ConstraintLayout {

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_menu)
    public ImageView iv_menu;
    @BindView(R.id.iv_collection)
    public ImageView iv_collection;
    @BindView(R.id.iv_search)
    public ImageView iv_search;
    @BindView(R.id.tv_search)
    public TextView tv_search;


    private float offsetMax = (float) SourceUtil.resDimension(R.dimen.top_min_height);
    private AccelerateDecelerateInterpolator adInterpolator = new AccelerateDecelerateInterpolator();
    private float offset = 0F;
    private Drawable drawableBack = SourceUtil.resDrawable(R.mipmap.back_white, 1F);
    private Drawable drawableSearch = SourceUtil.resDrawable(R.mipmap.icon_search, 1F);
    private Drawable drawableCollection = SourceUtil.resDrawable(R.mipmap.icon_collection, 1F);
    private Drawable drawableMenu = SourceUtil.resDrawable(R.mipmap.icon_menu, 1F);

    private boolean mStatusBarIsDark;

    public ShopTitleLayout(@NonNull Context context) {
        this(context, null);
    }

    public ShopTitleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopTitleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shop_details_title, this);
        ButterKnife.bind(view);
    }


    public float effectedByOffset(int dy) {
        if (dy > 0 && offset == offsetMax) {
            return 1F;
        } else if (dy < 0 && offset == 0F) {
            return 0F;
        }

        offset += dy;
        if (offset > offsetMax) {
            offset = offsetMax;
        } else if (offset < 0) {
            offset = 0F;
        }

        float effect = adInterpolator.getInterpolation(offset / offsetMax);
        setBackgroundColor(
                (int) SourceUtil.argbEvaluator.evaluate(
                        effect,
                        Color.TRANSPARENT,
                        Color.parseColor("#FFFFFFFF")
                )
        );

        int e = (int) SourceUtil.argbEvaluator.evaluate(effect, Color.WHITE, Color.parseColor("#FF646464"));
        iv_back.setImageDrawable(tintDrawable(drawableBack, ColorStateList.valueOf(e)));
        iv_menu.setImageDrawable(tintDrawable(drawableMenu, ColorStateList.valueOf(e)));
        iv_collection.setImageDrawable(tintDrawable(drawableCollection, ColorStateList.valueOf(e)));
        iv_search.setImageDrawable(tintDrawable(drawableSearch, ColorStateList.valueOf(e)));

        iv_search.setScaleX((float) (1 - 0.4 * effect));
        iv_search.setScaleY((float) (1 - 0.4 * effect));
        iv_search.setTranslationX(-(tv_search.getWidth() - iv_search.getWidth() + SourceUtil.dp(3)) * effect);
        tv_search.setAlpha(effect);
        tv_search.setPivotX((float) tv_search.getWidth());
        tv_search.setScaleX((float) (0.2+0.8*effect));

        //根据滚动比例动态设置状态字体颜色为深色
        if ((effect>=0.5f)!=mStatusBarIsDark){
            mStatusBarIsDark = !mStatusBarIsDark;
            SystemUtil.setStatusBarDarkMode((Activity)getContext(),mStatusBarIsDark);
        }
        return effect;
    }

    private Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
