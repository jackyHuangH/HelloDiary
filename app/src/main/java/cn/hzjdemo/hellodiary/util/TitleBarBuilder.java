package cn.hzjdemo.hellodiary.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.hzjdemo.hellodiary.R;

/**
 * Created by Hzj on 2017/8/16.
 * 公共标题栏构造器
 */

public class TitleBarBuilder {
    private View titleView;
    private RelativeLayout titleBar;
    private TextView text;
    private TextView right_text;
    private ImageButton leftIco;
    private ImageButton rightIco;
    private Context mContext;

    /**
     * 获取titlebar标题
     *
     * @return
     */
    public TextView getTitle() {
        return text;
    }

    /**
     * 获取titlebar右标题
     *
     * @return
     */
    public TextView getRight_Title() {
        return right_text;
    }

    /**
     * 构造方法：用于获取对象
     */
    public TitleBarBuilder(Activity context, ViewGroup root) {
        mContext=context;
        titleView = LayoutInflater.from(context).inflate(R.layout.title_bar, root, true);
        titleBar = (RelativeLayout) titleView.findViewById(R.id.title_bar);
        text = (TextView) titleView.findViewById(R.id.title_text);
        right_text = (TextView) titleView.findViewById(R.id.tv_right);
        leftIco = (ImageButton) titleView.findViewById(R.id.title_leftIco);
        rightIco = (ImageButton) titleView.findViewById(R.id.title_rightIco);
    }

    /**
     * 用于设置标题栏文字
     */
    public TitleBarBuilder setTitleText(String titleText) {
        if (!TextUtils.isEmpty(titleText)) {
            text.setText(titleText);
        }
        return this;
    }

    /**
     * 用于设置右边文字
     */
    public TitleBarBuilder setRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            right_text.setText(text);
            right_text.setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * 用于设置标题栏左边要显示的图片
     * 默认为返回按钮
     */
    public TitleBarBuilder setLeftIco(int resId) {
        leftIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        leftIco.setImageResource(resId);
        return this;
    }

    /**
     * 用于设置标题栏左边要显示的图片
     * 默认为返回按钮
     */
    public TitleBarBuilder setLeftIconDrawable(@DrawableRes int drawableId) {
        leftIco.setVisibility(drawableId > 0 ? View.VISIBLE : View.GONE);
        leftIco.setBackground(ContextCompat.getDrawable(mContext,drawableId));
        return this;
    }

    /**
     * 用于设置标题栏右边要显示的图片
     */
    public TitleBarBuilder setRightIco(int resId) {
        rightIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        rightIco.setImageResource(resId);
        return this;
    }

    /**
     * 用于设置标题栏左边图片的单击事件
     */
    public TitleBarBuilder setLeftIcoListening(View.OnClickListener listener) {
        if (leftIco.getVisibility() == View.VISIBLE) {
            leftIco.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 用于设置标题栏右边图片的单击事件
     */
    public TitleBarBuilder setRightIcoListening(View.OnClickListener listener) {
        if (rightIco.getVisibility() == View.VISIBLE) {
            rightIco.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 用于设置标题栏右边文字的单击事件
     */
    public TitleBarBuilder setRightTitleListening(View.OnClickListener listener) {
        if (right_text.getVisibility() == View.VISIBLE) {
            right_text.setOnClickListener(listener);
        }
        return this;
    }
}
