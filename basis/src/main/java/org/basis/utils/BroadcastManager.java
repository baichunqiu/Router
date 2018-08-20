package org.basis.utils;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import org.basis.UIUtil;

/**
 * @author: BaiCQ
 * @ClassName: BroadcastManager
 * @date: 2018/8/20
 * @Description: BroadcastManager 广播管理者
 */
public class BroadcastManager {

    /**
     * 注册全局广播
     * @param receiver
     * @param actions
     */
    public static void registerReceiver(BroadcastReceiver receiver, String[] actions) {
        registerReceiver(UIUtil.getContext(), receiver, actions);
    }

    /**
     * 解绑全局广播
     * @param receiver
     */
    public static void unregisterReceiver(BroadcastReceiver receiver) {
        unregisterReceiver(UIUtil.getContext(), receiver);
    }

    /**
     * 注册本地广播
     * @param receiver
     * @param actions
     */
    public static void registerLocalReceiver(BroadcastReceiver receiver, String[] actions) {
        registerReceiver(LocalBroadcastManager.getInstance(UIUtil.getContext()), receiver, actions);
    }

    /**
     * 解绑本地广播
     * @param receiver
     */
    public static void unregisterLocalReceiver(BroadcastReceiver receiver) {
        unregisterReceiver(LocalBroadcastManager.getInstance(UIUtil.getContext()), receiver);
    }

    /**
     * 发送全局广播
     * @param action
     */
    public static void sendBroadcast(String action) {
        UIUtil.getContext().sendBroadcast(buildIntent(action));
    }

    /**
     * 发送本地广播
     * @param action
     */
    public static void sendLocalBroadcast(String action) {
        LocalBroadcastManager.getInstance(UIUtil.getContext()).sendBroadcast(buildIntent(action));
    }

    /**
     * 发送有序广播
     * @param action
     */
    public static void sendOrderedBroadcast(String action) {
        UIUtil.getContext().sendOrderedBroadcast(buildIntent(action), null);
    }

    /**
     * 发送终结广播：一条广播，携带最终处理数据的接收器（必定执行）
     * @param action
     * @param endReceiver 最终处理数据的接收器
     */
    public static void sendEndBroadcast(String action, BroadcastReceiver endReceiver) {
        /**
         *  intent - 要发送的广播意图；
         *  receiverPermission - 发送的广播的权限，如果是null，即认为没有，任何符合条件的接收器都能收到；
         *  resultReceiver - 最终处理数据的接收器，也就是自己定义的；
         *  scheduler - 自定义的一个handler，来处理resultReceiver的回调，（其实就是设置运行这个接收器的线程），如果为null，默认在主线程；
         *  后面三个并不重要，通常情况下一次为：-1,null,null。（Activity.RESULT_OK 即 -1）
         */
        UIUtil.getContext().sendOrderedBroadcast(buildIntent(action), null, endReceiver, null, -1, null, null);
    }

    /**
     * 根据action 构建intent
     * @param action
     * @return
     */
    private static Intent buildIntent(String action) {
        return new Intent().setAction(action);
    }

    /**
     * 根据action集构建意图过滤器IntentFilter
     * @param actions
     * @return
     */
    private static IntentFilter buildFilter(String[] actions) {
        IntentFilter filter = new IntentFilter();
        if (null != actions && actions.length > 0) {
            for (String action : actions) {
                filter.addAction(action);
            }
        }
        return filter;
    }

    /**
     * 注册广播 兼容全局和本地广播
     *
     * @param t
     * @param receiver
     * @param actions
     * @param <T>      Context,LocalBroadcastManager
     */
    private static <T> void registerReceiver(T t, BroadcastReceiver receiver, String[] actions) {
        if (null == receiver) {
            Logger.e("the BroadcastReceiver of you register is null");
            return;
        }
        if (null != t && t instanceof LocalBroadcastManager) {
            ((LocalBroadcastManager) t).registerReceiver(receiver, buildFilter(actions));
        } else {
            UIUtil.getContext().registerReceiver(receiver, buildFilter(actions));
        }
    }

    /**
     * @param t
     * @param receiver
     * @param <T>      Context,LocalBroadcastManager
     */
    private static <T> void unregisterReceiver(T t, BroadcastReceiver receiver) {
        if (null == receiver) {
            Logger.e("the BroadcastReceiver of you unregister is null");
            return;
        }
        if (null != t && t instanceof LocalBroadcastManager) {
            ((LocalBroadcastManager) t).unregisterReceiver(receiver);
        } else {
            UIUtil.getContext().unregisterReceiver(receiver);
        }
    }

}
