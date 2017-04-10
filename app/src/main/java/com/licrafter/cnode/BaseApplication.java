package com.licrafter.cnode;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.licrafter.cnode.utils.SharedPreferenceUtils;
import com.licrafter.cnode.widget.BoxingPicassoLoader;
import com.licrafter.cnode.widget.BoxingUcrop;

/**
 * author: shell
 * date 2017/2/24 下午3:27
 **/
public class BaseApplication extends Application {

    public static Context mContext;
    public static String APP_ID = "V2ewxmFalb07Lj1GFFYuWfQF-gzGzoHsz";
    public static String APP_KEY = "5A0DVS5BnD5HGftmjOXVtFuG";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SharedPreferenceUtils.init(this);
        AVOSCloud.initialize(this, APP_ID, APP_KEY);
        AVOSCloud.setDebugLogEnabled(true);
        IBoxingMediaLoader loader = new BoxingPicassoLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
    }

    public static Context getContext() {
        return mContext;
    }
}
