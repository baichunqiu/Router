package org.basis;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

/**
 * @author: BaiCQ
 * @ClassName: UIUtil
 * @date: 2018/8/17
 * @Description: 常用简单api工具封装
 */
public class UIUtil {
    private static final String TAG = "UIUtil";

    public static Context getContext() {
        return BasisApplication.getmBaseContext();
    }

    public static Handler getHandler() {
        return BasisApplication.getHandler();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static AssetManager getAssets() {
        return getContext().getAssets();
    }

    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    public static <T extends View> T getView(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    public static <T extends View> T getView(View parent, int viewId) {
        return (T) parent.findViewById(viewId);
    }

    public static <T extends View> void setVisiable(T t, boolean visiable) {
        if (null == t) return;
        t.setVisibility(visiable ? View.VISIBLE : View.GONE);
    }

    public static void setText(TextView textView, int resouceId) {
        if (null == textView) return;
        if (resouceId > 0) {
            textView.setText(resouceId);
        } else {
            textView.setText("");
        }
    }

    public static void postDelayed(Runnable r,int delay){
        getHandler().postDelayed(r,delay);
    }
}
