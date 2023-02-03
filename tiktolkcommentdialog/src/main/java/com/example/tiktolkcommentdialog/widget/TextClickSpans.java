package com.example.tiktolkcommentdialog.widget;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public abstract class TextClickSpans extends ClickableSpan implements View.OnClickListener {

    private boolean mPressed;

    public void setPressed(boolean mPressed) {
        this.mPressed = mPressed;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        if (mPressed){
            ds.bgColor = Color.parseColor("#B5B5B5");
        }else {
            ds.bgColor = Color.TRANSPARENT;
        }
        ds.setColor(Color.parseColor("#697A9F"));
        ds.setUnderlineText(false);
    }
}
