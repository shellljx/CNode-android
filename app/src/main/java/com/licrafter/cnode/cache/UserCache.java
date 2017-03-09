package com.licrafter.cnode.cache;

import com.licrafter.cnode.utils.SharedPreferenceUtils;

/**
 * author: shell
 * date 2017/2/24 下午3:30
 **/
public class UserCache {
    public static String USER_NAME = null;

    public static String USER_TOKEN = null;

    public static void cache(String name, String token) {
        USER_NAME = name;
        USER_TOKEN = token;
        SharedPreferenceUtils.save("user_name", USER_NAME);
        SharedPreferenceUtils.save("user_token", USER_TOKEN);
    }
}
