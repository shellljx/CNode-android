package com.licrafter.cnode.api;

import com.google.gson.GsonBuilder;
import com.licrafter.cnode.api.service.CNodeService;
import com.licrafter.cnode.utils.http.HttpClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: shell
 * date 2017/2/24 下午2:56
 **/
public class CNodeApi {

    private static CNodeApi instance;
    private CNodeService mService;

    public static CNodeApi getInstance() {
        if (instance == null) {
            instance = new CNodeApi();
        }
        return instance;
    }

    public CNodeApi() {
        if (mService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Urls.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                            .serializeNulls()
                            .create()))
                    .client(HttpClient.INSTANCE.getOkHttpClient())
                    .build();
            mService = retrofit.create(CNodeService.class);
        }
    }

    public CNodeService getService() {
        return mService;
    }
}
