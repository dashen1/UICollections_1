package com.example.meituancooridinator.business;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.meituancooridinator.R;
import com.example.meituancooridinator.utils.SourceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerchantTitleLayout extends FrameLayout {

    @BindView(R.id.vBack)
    public ImageView vBack;
    @BindView(R.id.vTogether)
    public ImageView vTogether;
    @BindView(R.id.vMenu)
    public ImageView vMenu;
    @BindView(R.id.vCollection)
    public ImageView vCollection;
    @BindView(R.id.vSearch)
    public ImageView vSearch;
    @BindView(R.id.vSearchBorder)
    public ImageView vSearchBorder;
    @BindView(R.id.vSearchHint)
    public TextView vSearchHint;

    private AccelerateDecelerateInterpolator adInterpolator;
    private float offsetMax = (float) SourceUtil.resDimension(R.dimen.merchant_offset);
    private float offset = 0F;

    private Drawable drawableBack = SourceUtil.resDrawable(R.mipmap.back_white,1F);
    private Drawable drawableSearch = SourceUtil.resDrawable(R.mipmap.icon_search,1F);
    private Drawable drawableCollection = SourceUtil.resDrawable(R.mipmap.icon_collection,1F);
    private Drawable drawableTogether = SourceUtil.resDrawable(R.mipmap.icon_together,1F);
    private Drawable drawableMenu = SourceUtil.resDrawable(R.mipmap.icon_menu,1F);

    public MerchantTitleLayout(@NonNull Context context) {
        this(context,null);
    }

    public MerchantTitleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MerchantTitleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.merchant_title_layout, this);
        ButterKnife.bind(view);
        adInterpolator = new AccelerateDecelerateInterpolator();
    }

    public float effectByOffset(int dy){
        if (dy > 0 && offset == offsetMax) return 1F;
        else if (dy < 0 && offset == 0F) return 0F;


        offset +=dy;
        if (offset>offsetMax) offset = offsetMax;
        else if(offset<0) offset = 0F;

        float effect = adInterpolator.getInterpolation(offset / offsetMax);

        int e = Color.parseColor("#FFFAFAFA");
        vBack.setImageDrawable(tintDrawable(drawableBack, ColorStateList.valueOf(e)));
        vTogether.setImageDrawable(tintDrawable(drawableTogether, ColorStateList.valueOf(e)));
        vMenu.setImageDrawable(tintDrawable(drawableMenu, ColorStateList.valueOf(e)));
        vCollection.setImageDrawable(tintDrawable(drawableCollection, ColorStateList.valueOf(e)));
        vSearch.setImageDrawable(tintDrawable(drawableSearch, ColorStateList.valueOf(e)));

        vSearch.setScaleX((float) (1 - 0.4 * effect));
        vSearch.setScaleY((float) (1 - 0.4 * effect));
        vSearch.setTranslationX(-(vSearchBorder.getWidth() - vSearch.getWidth() + SourceUtil.dp(3)) * effect);
        vSearchBorder.setAlpha(effect);
        vSearchBorder.setPivotX((float) vSearchBorder.getWidth());
        vSearchBorder.setScaleX((float) (0.2 + 0.8 * effect));
        vSearchHint.setAlpha(effect);
        vSearchHint.setTranslationX((float) (vSearchHint.getWidth() / 3) * (1 - effect));

        return effect;
    }

    private Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

}
