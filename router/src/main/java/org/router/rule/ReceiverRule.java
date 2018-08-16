package org.router.rule;

import android.content.BroadcastReceiver;

import org.router.exception.ReceiverNotRouteException;

/**
 * @author: BaiCQ
 * @ClassName: ReceiverRule
 * @date: 2018/8/16
 * @Description: receiver路由规则
 */
public class ReceiverRule extends BaseIntentRule<BroadcastReceiver> {

    /**
     * receiver路由scheme
     */
    public static final String RECEIVER_SCHEME = "receiver://";

    @Override
    public void throwException(String pattern) {
        throw new ReceiverNotRouteException(pattern);
    }
}
