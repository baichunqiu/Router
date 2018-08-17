package org.basis.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.basis.UIUtil;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.UI_MODE_SERVICE;

/**
 * @author: BaiCQ
 * @ClassName: SystemUtil
 * @date: 2018/8/17
 * @Description: SystemUtil 功能工具集
 */
public class SystemUtil {

    /**
     * 获取当前版本号
     * @return
     */
    public static String getVersion() {
        String version = "";
        try {
            PackageManager manager = UIUtil.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(UIUtil.getContext().getPackageName(), 0);
            version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 判断当前activity是否是栈顶
     * @param activity
     * @return
     */
    public static boolean isTopActivity(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        String topName = (runningTasks.get(0)).topActivity.getClassName();
        String currentName = activity.getComponentName().getClassName();
        return currentName.equalsIgnoreCase(topName);
    }

    /**
     * app是否在后台运行
     * @return
     */
    public static boolean isBackground() {
        ActivityManager activityManager = (ActivityManager) UIUtil.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(UIUtil.getContext().getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 获取设备的Mac地址
     * @return
     */
    public static String getMacAddress() {
        String address = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netWork = interfaces.nextElement();
                // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
                byte[] by = netWork.getHardwareAddress();
                if (by == null || by.length == 0) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : by) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                String mac = builder.toString();
                // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
                if (netWork.getName().equals("wlan0")) {
                    address = mac;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static int getCurrentVolume() {
        AudioManager mAudioManager = (AudioManager) UIUtil.getContext().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        return current * 100 / maxVolume;
    }

    public static void setCurrentVolume(int volume) {
        AudioManager mAudioManager = (AudioManager) UIUtil.getContext().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int index = (int) (volume * maxVolume / 100 + 0.5);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, AudioManager.ADJUST_SAME);
    }

    public static boolean isRunOnTV() {
        boolean resust = false;
        UiModeManager uiModeManager = (UiModeManager) UIUtil.getContext().getSystemService(UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            resust = true;
        }
        return resust;
    }

    /**
     * 通过设置全屏，设置状态栏透明
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//              window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 设置横竖屏
     * @param rotation 角度
     */
    public static void setRequestedOrientation(Activity activity, int rotation) {
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            // 横屏设置
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            // 竖屏设置
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            // 默认设置
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }
}
