package com.example.tiktolkcommentdialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiktolkcommentdialog.R;
import com.example.tiktolkcommentdialog.base.BaseApplication;

public class InputTextMsgDialog extends AppCompatDialog {

    private Context mContext;
    private InputMethodManager imm;
    private EditText messageTextView;
    private RelativeLayout rlDialog;
    private int mLastDiff = 0;
    private TextView tvNumber;
    private int maxNumber = 100;

    private OnTextSendListener mOnTextSendListener;


    public void setOnTextSendListener(OnTextSendListener mOnTextSendListener) {
        this.mOnTextSendListener = mOnTextSendListener;
    }

    public interface OnTextSendListener {
        void onTextSend(String msg);

        void dismiss();
    }

    public InputTextMsgDialog(@NonNull Context context) {
        this(context, 0);
    }

    public InputTextMsgDialog(@NonNull Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        init();
        setLayout();
    }

    private void init() {
        setContentView(R.layout.dialog_input_text_msg);
        messageTextView = findViewById(R.id.et_input_message);
        tvNumber = findViewById(R.id.tv_test);
        final LinearLayout rlDialogView = findViewById(R.id.rl_inputdlg_view);
        messageTextView.requestFocus();
        messageTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvNumber.setText(editable.length() + "/" + maxNumber);
            }
        });
        imm = (InputMethodManager) mContext.getSystemService((Context.INPUT_METHOD_SERVICE));
        ImageView iv_confirm = findViewById(R.id.iv_confirm);
        iv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = messageTextView.getText().toString().trim();
                if (msg.length() > maxNumber) {
                    Toast.makeText(mContext, "超过最大字数限制 " + maxNumber, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!TextUtils.isEmpty(msg)) {
                    mOnTextSendListener.onTextSend(msg);
                    imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    messageTextView.setText("");
                    InputTextMsgDialog.this.dismiss();
                } else {
                    Toast.makeText(BaseApplication.getApplication(), "请输入文字", Toast.LENGTH_LONG).show();
                }
                messageTextView.setText(null);
            }
        });

        messageTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case KeyEvent.KEYCODE_ENDCALL:
                    case KeyEvent.KEYCODE_ENTER:
                        if (messageTextView.getText().length()>maxNumber){
                            Toast.makeText(mContext, "超过最大字数限制", Toast.LENGTH_LONG).show();
                            return true;
                        }
                        if (messageTextView.getText().length()>0){
                            imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                            InputTextMsgDialog.this.dismiss();
                        }else {
                            Toast.makeText(mContext, "请输入文字", Toast.LENGTH_LONG).show();
                        }
                        return true;
                        case KeyEvent.KEYCODE_BACK:
                            InputTextMsgDialog.this.dismiss();
                            return false;
                    default:
                        return false;
                }
            }
        });

        messageTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("InputTextMsgDialog","onKey "+event.getCharacters());
                return false;
            }
        });
        rlDialogView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Rect rect = new Rect();
                //获取当前界面的可视部分
                InputTextMsgDialog.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                //获取屏幕的高度
                int screenHeight = InputTextMsgDialog.this.getWindow().getDecorView().getRootView().getHeight();
                //此处是用来获取键盘的高度，在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDiff = screenHeight-rect.bottom;
                if (heightDiff<=0&&mLastDiff>0){
                    InputTextMsgDialog.this.dismiss();
                }
                mLastDiff = heightDiff;
            }
        });

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode ==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
                    InputTextMsgDialog.this.dismiss();
                }
                return false;
            }
        });
    }

    private void setLayout(){
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p  =getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //在dismiss之前重置mLastDiff的值，以避免下次无法打开
        mLastDiff = 0;
        if (mOnTextSendListener!=null){
            mOnTextSendListener.dismiss();
        }
    }

    @Override
    public void show() {
        super.show();
    }
}
