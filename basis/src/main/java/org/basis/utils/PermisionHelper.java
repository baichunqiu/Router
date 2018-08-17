package org.basis.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

/**
 * @author: BaiCQ
 * @ClassName: PermisionHelper
 * @date: 2018/7/13
 * @Description: 权限辅助类
 */
public class PermisionHelper {

    /**
     * 默认权限申请
     *  读写
     */
    public static String[] permisions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    /**
     * 检查是否拥有指定的所有权限
     */
    public static boolean checkPermissionAllGranted(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 检测权限申请结果是否全部授权
     *
     * @param grantResults
     * @return
     */
    public static boolean checkgrantResults(int[] grantResults) {
        boolean isAllGranted = true;
        for (int grant : grantResults) {// 判断是否所有的权限都已经授予了
            if (grant != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
                break;
            }
        }
        return isAllGranted;
    }


    /**
     * 开启应用设置权限设置界面
     *
     * @param context
     */
    public static void openAppPermissionDetails(Activity context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }
}
