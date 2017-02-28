package com.licrafter.cnode.api.service;

import com.licrafter.cnode.model.TabModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lijx on 2017/2/24.
 */

public interface CNodeService {


    /**
     * 获取首页文章列表
     *
     * @param pageIndex
     * @param limit
     * @param mdrender
     * @return
     */
    @GET("topics")
    Observable<TabModel> getTopicPage(@Query("page") Integer pageIndex, @Query("limit") Integer limit, @Query("mdrender") Boolean mdrender);

    /**
     * 根据tab名字获取tab文章列表
     * 可用tab: ask share job good
     *
     * @param tab
     * @param pageIndex
     * @param limit
     * @param mdrender
     * @return
     */
    @GET("topics")
    Observable<TabModel> getTabByName(@Query("tab") String tab, @Query("page") Integer pageIndex, @Query("limit") Integer limit, @Query("mdrender") Boolean mdrender);
}
