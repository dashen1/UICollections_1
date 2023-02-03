package com.example.meituancooridinator.utils;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.meituancooridinator.App;

import java.util.List;

import jp.wasabeef.glide.transformations.BitmapTransformation;

public class SourceUtil {

    public static final Handler dispatcher = new Handler(Looper.getMainLooper());
    public static final DrawableTransitionOptions crossFade = new DrawableTransitionOptions().crossFade(200);
    public static final RequestOptions centerCrop = new RequestOptions().centerCrop().placeholder(android.R.color.transparent).priority(Priority.NORMAL);
    public static final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public static int resDimension(int dimensionRes){
        return App.getContext().getResources().getDimensionPixelSize(dimensionRes);
    }

    public static Drawable resDrawable(int drawableRes, float scale){

        Drawable drawable = ContextCompat.getDrawable(App.getContext(), drawableRes);
        if (drawable!=null){
            drawable.setBounds(0,0,(int)(drawable.getMinimumWidth()*scale),(int)(drawable.getMinimumWidth()*scale));
        }
        return drawable;
    }

    public static int dp(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int) ( dp * metrics.density + 0.5f);
    }

    public static void load(Context context,Object res, List<BitmapTransformation> transform, ImageView imageView){
        RequestOptions requestOptions;
        if (transform != null){
            requestOptions = RequestOptions.bitmapTransform(new MultiTransformation(transform));
        } else {
            requestOptions = centerCrop;
        }
        Glide.with(App.getContext()).load(res).apply(requestOptions).transition(crossFade).into(imageView);

    }


}
