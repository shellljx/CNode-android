package com.licrafter.cnode.api.service;

import com.licrafter.cnode.model.LoginBody;
import com.licrafter.cnode.model.LoginResultModel;
import com.licrafter.cnode.model.TabModel;
import com.licrafter.cnode.model.TopicDetailModel;
import com.licrafter.cnode.model.UnReadCountModel;
import com.licrafter.cnode.model.UserDetailModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    /**
     * 根据topic Id 获取文章详情
     *
     * @param topicId
     * @param accesstoken
     * @param mdrender
     * @return
     */
    @GET("topic/{topicId}")
    Observable<TopicDetailModel> getTopicDetailById(@Path("topicId") String topicId, @Query("accesstoken") String accesstoken, @Query("mdrender") boolean mdrender);

    /**
     * 获取用户详细信息
     *
     * @param userName
     * @return
     */
    @GET("user/{userName}")
    Observable<UserDetailModel> getUserDetailByName(@Path("userName") String userName);

    /**
     * 登录
     *
     * @param body
     * @return
     */
    @POST("accesstoken")
    Observable<LoginResultModel> login(@Body LoginBody body);

    /**
     * 获取未读消息数
     *
     * @param accesstoken
     * @return
     */
    @POST("message/count")
    Observable<UnReadCountModel> getUnReadCount(@Body String accesstoken);
}
