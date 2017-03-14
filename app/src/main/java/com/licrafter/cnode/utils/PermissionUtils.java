package com.licrafter.cnode.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * author: shell
 * date 2017/3/13 上午11:19
 **/
public class PermissionUtils {

    /**
     * 权限是否被开启
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 显示权限dialog
     *
     * @param activity
     * @param message
     * @param needFinishActivity
     */
    public static void showPermissionsDialog(final Activity activity, String message, final boolean needFinishActivity) {
        new AlertDialog.Builder(activity)
                .setTitle("权限申请")
                .setMessage(message)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Uri packageURI = Uri.parse("package:" + "com.licrafter.cnode");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (needFinishActivity) {
                            activity.finish();
                        }
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
