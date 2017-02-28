package com.licrafter.cnode.utils;

import android.support.v4.widget.SwipeRefreshLayout;

import com.licrafter.cnode.R;

/**
 * author: shell
 * date 2017/2/27 下午1:47
 **/
public class SwipeRefreshUtils {

    public static void initStyle(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.loading_color_1,
                    R.color.loading_color_2,
                    R.color.loading_color_3,
                    R.color.loading_color_4,
                    R.color.loading_color_5);
        }
    }
}
