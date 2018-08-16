package org.router;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import org.router.rule.ActivityRule;
import org.router.rule.Rule;

import java.io.Serializable;

/**
 * @author: BaiCQ
 * @ClassName: Router
 * @date: 2018/8/16
 * @Description:
 *      step 1. 调用Router.router方法添加路由
 *      step 2. 调用Router.invoke方法根据pattern调用路由
 */
public class Router {

    //路由传递参数KEY
    public final static String KEY_STRING = "router_string";
    public final static String KEY_BOOLE = "router_boolean";
    public final static String KEY_PARCEL = "router_parcel";
    public final static String KEY_SERIAL = "router_serial";

    /**
     * 添加自定义路由规则
     *
     * @param scheme 路由scheme
     * @param rule   路由规则
     * @return {@code RouterInternal} Router真实调用类
     */
    public static RouterInternal addRule(String scheme, Rule rule) {
        RouterInternal router = RouterInternal.get();
        router.addRule(scheme, rule);
        return router;
    }

    /**
     * 添加路由
     *
     * @param pattern 路由uri
     * @param klass   路由class
     * @return {@code RouterInternal} Router真实调用类
     */
    public static <T> RouterInternal router(String pattern, Class<T> klass) {
        return RouterInternal.get().router(pattern, klass);
    }

    /**
     * 路由调用
     *
     * @param ctx     Context
     * @param pattern 路由uri
     * @return {@code V} 返回对应的返回值
     */
    public static <V> V invoke(Context ctx, String pattern) {
        return RouterInternal.get().invoke(ctx, pattern);
    }

    /**
     * 是否存在该路由
     *
     * @param pattern
     * @return
     */
    public static boolean resolveRouter(String pattern) {
        return RouterInternal.get().resolveRouter(pattern);
    }

    public static boolean startActivity(Context ctx, String pattern) {
        return startActivity(ctx, pattern, null);
    }

    public static <P> boolean startActivity(Context ctx, String pattern, P paramter) {
        pattern = ActivityRule.ACTIVITY_SCHEME + pattern;
        Log.e("Router", "pattern : "+pattern );
        boolean isRouter = Router.resolveRouter(pattern);
        if (isRouter) {
            Intent in = Router.invoke(ctx,pattern);
            if (null != paramter && !"".equals(pattern)) {
                if (paramter instanceof String) {
                    in.putExtra(KEY_STRING, (String) paramter);
                } else if (paramter instanceof Boolean) {
                    in.putExtra(KEY_BOOLE, (Boolean) paramter);
                } else if (paramter instanceof Serializable) {
                    in.putExtra(KEY_SERIAL, (Serializable) paramter);
                } else if (paramter instanceof Parcelable) {
                    in.putExtra(KEY_PARCEL, (Parcelable) paramter);
                }
            }
            ctx.startActivity(in);
        }
        return isRouter;
    }

}
