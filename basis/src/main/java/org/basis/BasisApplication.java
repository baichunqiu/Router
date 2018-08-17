package org.basis;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import org.basis.utils.Logger;

/**
 * @author: BaiCQ
 * @ClassName: BasisApplication
 * @Description:
 */
public class BasisApplication extends Application {
    private static Application mBaseContext;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        initObj();
    }

    //赋值
    private void initObj() {
        mBaseContext = this;
        mHandler = new Handler();
    }

    public static Application getmBaseContext() {
        //没有实现BasisApplication
        if (null == mBaseContext){
            getInstanceFromReflexG();
        }
        return mBaseContext;
    }

    public static Handler getHandler() {
        if (null == mHandler){
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }
    /**
     * 通过反射获取当前app的Application实例
     */
    private static void getInstanceFromReflexG() {
        try {
            mBaseContext = (android.app.Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (mBaseContext == null) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
            Logger.e("android.app.AppGlobals");
        } catch (final Exception e) {
            try {
                mBaseContext = (android.app.Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
                Logger.e("android.app.ActivityThread");
            } catch (final Exception ex) {
                e.printStackTrace();
            }
        }
    }
}
