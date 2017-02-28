package com.licrafter.cnode.utils.http;

import com.licrafter.cnode.BaseApplication;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author: shell
 * date 2017/2/24 下午3:13
 **/
public enum HttpClient {

    INSTANCE;
    private OkHttpClient client;
    private PersistentCookieStore persistentCookieStore;

    HttpClient() {
        persistentCookieStore = new PersistentCookieStore(BaseApplication.getContext());
        client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .cookieJar(new CustomCookieJar(new CookieManager(persistentCookieStore, CookiePolicy.ACCEPT_ALL)))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

    }

    public OkHttpClient getOkHttpClient() {
        return client;
    }

    public void clearCookie() {
        persistentCookieStore.removeAll();
    }
}
