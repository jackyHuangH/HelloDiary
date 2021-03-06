package com.zenchn.picbrowserlib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 能够与轮播共存的viewpager
 *
 * @author yuedong
 */
public class BaseViewPager extends ViewPager {
    private boolean scrollable = true;

    public BaseViewPager(Context context) {
        super(context);
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable 滚动？
     */
    public void setScrollable(boolean enable) {
        scrollable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //解决photoview 缩小图时异常崩溃
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}