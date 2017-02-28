package com.licrafter.cnode.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * author: shell
 * date 2017/2/24 上午11:18
 **/
public class FragmentUtils {

    public static void replace(FragmentManager manager, int containerId, Fragment fragment, boolean addBackStack, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (addBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.replace(containerId, fragment, tag).commitAllowingStateLoss();
    }
}
