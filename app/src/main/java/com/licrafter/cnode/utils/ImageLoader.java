package com.licrafter.cnode.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * author: shell
 * date 2017/2/27 上午11:09
 **/
public class ImageLoader {

    public static void loadUrl(ImageView tag, String url) {
        Picasso.with(tag.getContext()).load(url).into(tag);
    }
}
