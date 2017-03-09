package com.licrafter.cnode.utils;

import android.content.Context;

/**
 * author: shell
 * date 2017/3/3 下午2:16
 **/
public class DipConvertUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
