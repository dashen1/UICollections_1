package com.example.meituancooridinator.business;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.meituancooridinator.App;
import com.example.meituancooridinator.R;
import com.example.meituancooridinator.customview.TicketView;
import com.example.meituancooridinator.utils.SourceUtil;
import com.example.meituancooridinator.view.ViewState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MerchantContentLayout extends ConstraintLayout {

    @BindView(R.id.laySimple)
    public LinearLayout laySimple;
    @BindView(R.id.vMerchantName)
    public TextView vMerchantName;
    @BindView(R.id.layTicket)
    public HorizontalScrollView layTicket;
    @BindView(R.id.vSwitchIcon)
    public ImageView vSwitchIcon;
    @BindView(R.id.vAvatar)
    public ImageView vAvatar;
    @BindView(R.id.layScroll)
    public ScrollView layScroll;
    @BindView(R.id.vTicket1)
    public TicketView vTicket1;
    @BindView(R.id.vTicket2)
    public TicketView vTicket2;

    @BindView(R.id.vt2)
    public TextView vt2;
    @BindView(R.id.vt222)
    public TextView vt222;

    @BindView(R.id.vt3)
    public TextView vt3;
    @BindView(R.id.vt333)
    public TextView vt333;

    @BindView(R.id.vt4)
    public TextView vt4;
    @BindView(R.id.vt444)
    public TextView vt444;

    @BindView(R.id.vt5)
    public TextView vt5;
    @BindView(R.id.vt555)
    public TextView vt555;

    @BindView(R.id.vHide)
    public ImageView vHide;
    @BindView(R.id.vCover)
    public ImageView vCover;


    private boolean isFirstLayout = false;
    private boolean isExpanded = false;
    private float effected = 0f;

    public void setLaySettle(MerchantSettleLayout laySettle) {
        this.laySettle = laySettle;
    }

    private MerchantSettleLayout laySettle;

    public MerchantContentLayout(@NonNull Context context) {
        this(context, null);
    }

    public MerchantContentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MerchantContentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.merchant_content_layout, this);
        ButterKnife.bind(view);
        ArrayList<BitmapTransformation> trans = new ArrayList<>();
        trans.add(new BlurTransformation());
        SourceUtil.load(App.getContext(),R.mipmap.cover,trans,vCover);
        vTicket1.set(3, 27, "2018.06.12");
        vTicket2.set(5, 40, "2018.06.12");

        vSwitchIcon.setOnClickListener(v -> switchLayout(!isExpanded, false));
        vHide.setOnClickListener(v -> switchLayout(!isExpanded, false));

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!isFirstLayout) {
            isFirstLayout = true;
            ViewState.stateSave(laySimple, R.id.vs1).a(1F);
            ViewState.stateSave(laySimple, R.id.vs2).a(0F);
            ViewState.stateSave(vMerchantName, R.id.vs1).a(0F);
            ViewState.stateSave(vMerchantName, R.id.vs2).a(1F);
            ViewState.stateSave(layTicket, R.id.vs1).mt(SourceUtil.dp(15));
            ViewState.stateSave(layTicket, R.id.vs2).mt(SourceUtil.dp(70));

            ViewState.stateSave(vSwitchIcon, R.id.vs1);
            ViewState.stateSave(vSwitchIcon, R.id.vs2).r(180F);

            ViewState.stateSave(vAvatar, R.id.vs1);
            MarginLayoutParams layoutParams = (MarginLayoutParams) vAvatar.getLayoutParams();
            float tx = (float) (getWidth() - vAvatar.getWidth()) / 2 - (layoutParams.leftMargin);
            ViewState.stateSave(vAvatar, R.id.vs2).tx(tx).ty((float) SourceUtil.dp(10));
        }
    }

    public void effectByOffset(float transY) {
        float p2;
        if (transY <= SourceUtil.dp(10)) {
            p2 = 0F;
        } else if (transY > SourceUtil.dp(10) && transY < SourceUtil.dp(30)) {
            p2 = (transY - SourceUtil.dp(10)) / SourceUtil.dp(20);
        } else {
            p2 = 1F;
        }
        vt2.setAlpha(p2);
        vt222.setAlpha(p2);

        float p3;
        if (transY <= SourceUtil.dp(40)) {
            p3 = 0F;
        } else if (transY > SourceUtil.dp(40) && transY < SourceUtil.dp(60)) {
            p3 = (transY - SourceUtil.dp(40)) / SourceUtil.dp(20);
        } else {
            p3 = 1F;
        }
        vt3.setAlpha(p3);
        vt333.setAlpha(p3);

        float p4;
        if (transY <= SourceUtil.dp(70)) {
            p4 = 0F;
        } else if (transY > SourceUtil.dp(70) && transY < SourceUtil.dp(90)) {
            p4 = (transY - SourceUtil.dp(70)) / SourceUtil.dp(20);
        } else {
            p4 = 1F;
        }
        vt4.setAlpha(p4);
        vt444.setAlpha(p4);

        float p5;
        if (transY <= SourceUtil.dp(110)) {
            p5 = 0F;
        } else if (transY > SourceUtil.dp(110) && transY < SourceUtil.dp(130)) {
            p5 = (transY - SourceUtil.dp(110)) / SourceUtil.dp(20);
        } else {
            p5 = 1F;
        }
        vt5.setAlpha(p5);
        vt555.setAlpha(p5);

        if (transY <= SourceUtil.dp(140)) {
            effected = 0F;
        } else if (transY > SourceUtil.dp(140) && transY < SourceUtil.dp(230)) {
            effected = (transY - SourceUtil.dp(140)) / SourceUtil.dp(90);
        } else {
            effected = 1F;
        }

        for (View animView : animViews()) {
            ViewState.stateRefresh(animView, R.id.vs1, R.id.vs2, effected);
        }
    }


    public void switchLayout(boolean expended, boolean byScrollerSlide) {
        if (isExpanded == expended) {
            return;
        }
        //如果不是滚动，即通点击关闭展开，使得scrollview恢复原位
        layScroll.scrollTo(0, 0);
        isExpanded = expended;
        float start = effected;
        float end = expended ? 1F : 0F;

        ViewState.statesChangeByAnimation(animViews(), R.id.vs1, R.id.vs2, start, end, null, byScrollerSlide ? null : internalAnimListener, 300, 0L);
        laySettle.switchLayout(isExpanded);
    }

    public ArrayList<View> animViews() {
        ArrayList<View> list = new ArrayList<>();
        list.add(laySimple);
        list.add(vAvatar);
        list.add(vMerchantName);
        list.add(layTicket);
        list.add(vTicket1);
        list.add(vTicket2);
        list.add(vSwitchIcon);
        return list;
    }

    public AnimatorListenerAdapter1 animListener;

    private AnimatorListenerAdapter internalAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (animListener != null) {
                animListener.onAnimationStart(animation, isExpanded);
            }
        }
    };

    public interface AnimatorListenerAdapter1 {
        void onAnimationStart(Animator animator, boolean toExpanded);
    }
}
