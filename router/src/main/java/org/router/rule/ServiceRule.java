package org.router.rule;

import android.app.Service;

import org.router.exception.ServiceNotRouteException;

/**
 * @author: BaiCQ
 * @ClassName: ServiceRule
 * @date: 2018/8/16
 * @Description: service路由规则
 */
public class ServiceRule extends BaseIntentRule<Service> {

    /**
     * service路由scheme
     */
    public static final String SERVICE_SCHEME = "service://";

    @Override
    public void throwException(String pattern) {
        throw new ServiceNotRouteException(pattern);
    }
}
