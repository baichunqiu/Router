package org.basis.network.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.loader.utilslib.R;

/**
 * @author: BaiCQ
 * @ClassName: TitleBar
 * @date: 2018/6/28
 * @Description: TitleBar的备注
 */
public class TitleBar extends FrameLayout {
    private View ll_back;
    private TextView tv_title;
    private ImageView iv_left;
    private View ll_right;
    private TextView tv_right;

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this);
        ll_back = view.findViewById(R.id.ll_back_titlebar);
        iv_left = view.findViewById(R.id.iv_left_titlebar);
        tv_title = view.findViewById(R.id.tv_title_titlebar);
        ll_right = view.findViewById(R.id.ll_right_titlebar);
        tv_right = view.findViewById(R.id.tv_right_titlebar);
    }

    public void setBack(OnClickListener onClickListener) {
        setBack(-1, onClickListener);
    }

    public void setBack(int resouceId, OnClickListener onClickListener) {
        if (null == ll_back || iv_left == null) return;
        ll_back.setVisibility(View.VISIBLE);
        ll_back.setOnClickListener(onClickListener);
        if (resouceId > 0) {
            iv_left.setBackgroundResource(resouceId);
        }
    }

    public void setRightButton(String message, OnClickListener onClickListener) {
        setRightButton(-1, message, onClickListener);
    }

    public void setRightButton(int backResource, String massage, OnClickListener onClickListener) {
        if (null == ll_right || tv_right == null) return;
        ll_right.setVisibility(View.VISIBLE);
        ll_right.setOnClickListener(onClickListener);
        if (backResource > 0) {
            tv_right.setBackgroundResource(backResource);
        }
        if (!TextUtils.isEmpty(massage)) {
            tv_right.setText(massage);
        }
    }

    public void setTitle(String title) {
        if (null == tv_title) return;
        tv_title.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }

    public void setDefault(final Activity activity, String title,String rightMsg,OnClickListener rightOnClick) {
        setTitle(title);
        setRightButton(-1,rightMsg,rightOnClick);
        setBack(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }
}
