package com.licrafter.cnode.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by lijx on 2017/4/14.
 */

public class IntentUtils {

    public static void startBrower(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    public static void shareTopic(Context context, String title, String id) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, title + " 链接>https://cnodejs.org/topic/" + id + "\n\n来自CNode-Android客户端(https://github.com/shellljx/CNode-android)");
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }
}
