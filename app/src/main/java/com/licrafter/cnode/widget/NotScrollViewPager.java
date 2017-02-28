package com.licrafter.cnode.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author: shell
 * date 2017/2/24 上午11:42
 **/
public class NotScrollViewPager extends ViewPager {

    public NotScrollViewPager(Context context) {
        super(context);
    }

    public NotScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
