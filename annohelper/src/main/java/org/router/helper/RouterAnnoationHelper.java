package org.router.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: BaiCQ
 * @ClassName: RouterAnnoationHelper
 * @date: 2018/8/16
 * @Description: 调度注解辅助类
 *          在主module中调用 通过编译时注解apt 实现
 */
public class RouterAnnoationHelper {

    public static void install() {
        try {
            Class<?> klass = Class.forName(Config.PACKAGE_NAME + "." + Config.ROUTER_MANAGER);
            Method method = klass.getDeclaredMethod(Config.ROUTER_MANAGER_METHOD);
            method.invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
