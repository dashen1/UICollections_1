package com.example.customfish;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

public class FishRelativeLayout extends RelativeLayout {


    private Paint mPaint;
    private ImageView ivFish;
    private FishDrawable mFishDrawable;
    private float mTouchX;
    private float mTouchY;
    private float mRipple;
    private int mAlpha;

    public FishRelativeLayout(Context context) {
        this(context, null);
    }

    public FishRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FishRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //ViewGroup 默认不执行onDraw()方法
        setWillNotDraw(false);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        ivFish = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ivFish.setLayoutParams(layoutParams);
        ivFish.setBackgroundColor(Color.BLUE);
        mFishDrawable = new FishDrawable();
        ivFish.setImageDrawable(mFishDrawable);
        addView(ivFish);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();
        mPaint.setAlpha(100);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "ripple", 0, 1f)
                .setDuration(1000);
        objectAnimator.start();

        makeTrail();

        return super.onTouchEvent(event);
    }

    private void makeTrail() {
        //鱼的重心，相对与ImageView坐标
        PointF fishRelativeMiddle = mFishDrawable.getMiddlePoint();
        //鱼的重心 绝对坐标 --- 起始点
        PointF fihMiddle = new PointF(ivFish.getX() + fishRelativeMiddle.x, ivFish.getY() + fishRelativeMiddle.y);
        //鱼头圆心坐标 --- 控制点1
        final PointF fishHead = new PointF(ivFish.getX() + mFishDrawable.getHeadPoint().x,
                ivFish.getY() + mFishDrawable.getHeadPoint().y);
        //点击坐标 --- 结束点
        PointF touch = new PointF(mTouchX, mTouchY);

        float angle = includeAngle(fihMiddle, fishHead, touch) / 2;
        float delta = includeAngle(fihMiddle, new PointF(fihMiddle.x + 1, fihMiddle.y), fishHead);
        //控制点2的坐标 ？？？todo
        PointF controlPoint = mFishDrawable.calculatePoint(fihMiddle, mFishDrawable.getHEAD_RADIUS() * 1.6f, angle + delta);
        Path path = new Path();
        path.moveTo(fihMiddle.x - fishRelativeMiddle.x, fihMiddle.y - fishRelativeMiddle.y);
        path.cubicTo(fishHead.x - fishRelativeMiddle.x, fishHead.y - fishRelativeMiddle.y,
                controlPoint.x - fishRelativeMiddle.x, controlPoint.y - fishRelativeMiddle.y,
                mTouchX - fishRelativeMiddle.x, mTouchY - fishRelativeMiddle.y);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivFish, "x", "y", path);
        objectAnimator.setDuration(2000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mFishDrawable.setFrequence(3f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFishDrawable.setFrequence(1f);
            }
        });

        final PathMeasure pathMeasure = new PathMeasure(path, false);
        final float[] tan = new float[2];
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //执行了整个周期的百分之多少
                float fraction = animation.getAnimatedFraction();
                pathMeasure.getPosTan(pathMeasure.getLength() * fraction, null, tan);
                float angle = (float) Math.toDegrees(Math.atan2(-tan[1], tan[0]));
                mFishDrawable.setFishMainAngle(angle);
            }
        });
        objectAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAlpha(mAlpha);
        canvas.drawCircle(mTouchX, mTouchY, mRipple * 150, mPaint);
        invalidate();
    }

    public float getRipple() {
        return mRipple;
    }

    public void setRipple(float mRipple) {
        this.mRipple = mRipple;
    }

    public float includeAngle(PointF O, PointF A, PointF B) {
        //cosAOB
        //OA*OB = (Ax-Ox)(Bx-Ox)+(Ay-Oy)(By-Oy)
        float AOB = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y);
        float OALength = (float) Math.sqrt((A.x - O.x) * (A.x - O.x) + (A.y - O.y) * (A.y - O.y));
        //OB长度
        float OBLength = (float) Math.sqrt((B.x - O.x) * (B.x - O.x) + (B.y - O.y) * (B.y - O.y));
        float cosAOB = AOB / (OALength * OBLength);
        //反余弦
        float angleAOB = (float) Math.toDegrees(Math.acos(cosAOB));
        //AB连线与X的夹角的tan值-OB连线于X轴的夹角的tan值
        float direction = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) / (O.x - B.x);

        if (direction == 0) {
            if (AOB >= 0) {
                return 0;
            } else {
                return 180;
            }
        } else {
            if (direction > 0) {
                return -angleAOB;
            } else {
                return angleAOB;
            }
        }
    }
}
