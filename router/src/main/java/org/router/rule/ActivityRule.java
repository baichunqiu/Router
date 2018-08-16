package org.router.rule;

import android.app.Activity;

import org.router.exception.ActivityNotRouteException;


/**
 * @author: BaiCQ
 * @ClassName: ActivityRule
 * @date: 2018/8/16
 * @Description: activity路由规则
 */
public class ActivityRule extends BaseIntentRule<Activity> {

    /**
     * activity路由scheme
     */
    public static final String ACTIVITY_SCHEME = "activity://";

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwException(String pattern) {
        throw new ActivityNotRouteException(pattern);
    }
}
