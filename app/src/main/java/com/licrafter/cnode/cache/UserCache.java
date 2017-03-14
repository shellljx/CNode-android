package com.licrafter.cnode.cache;

import com.licrafter.cnode.utils.SharedPreferenceUtils;

/**
 * author: shell
 * date 2017/2/24 下午3:30
 **/
public class UserCache {

    public static void cache(String id, String name, String avatar_url, String token) {
        SharedPreferenceUtils.save("user_id", id);
        SharedPreferenceUtils.save("avatar_url", avatar_url);
        SharedPreferenceUtils.save("user_name", name);
        SharedPreferenceUtils.save("user_token", token);
    }

    public static String getUserId() {
        return SharedPreferenceUtils.getString("user_id", null);
    }

    public static String getUserName() {
        return SharedPreferenceUtils.getString("user_name", null);
    }

    public static String getAvatarUrl() {
        return SharedPreferenceUtils.getString("avatar_url", null);
    }

    public static String getUserToken() {
        return SharedPreferenceUtils.getString("user_token", null);
    }

    public static void clear() {
        SharedPreferenceUtils.clearAll();
    }
}
