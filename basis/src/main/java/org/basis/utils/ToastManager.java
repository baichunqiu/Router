package org.basis.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.basis.UIUtil;
import org.loader.utilslib.R;


/**
 * @author: BaiCQ
 * @ClassName: ToastManager
 * @date: 2018/4/4
 * @Description: ToastManager的备注 强制使用resouceId 替换message text
 */
public class ToastManager extends Toast {
    private static ToastManager instance;
    private TextView showText;

    public static ToastManager getInstance() {
        if (instance == null) {
            synchronized (ToastManager.class) {
                if (instance == null) {
                    instance = new ToastManager();
                }
            }
        }
        return instance;
    }

    private ToastManager() {
        super(UIUtil.getContext());
        init(this);
    }

    private void init(Toast toast) {
        View tipsView = UIUtil.inflate(R.layout.toast_mb);
        showText = tipsView.findViewById(R.id.toast_content);
        int distance = ScreenUtil.getScreemWidth() * 25 / 100;
//        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, distance);//顶部
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, distance);//底部
        toast.setView(tipsView);
    }

    private ToastManager makeText(int msgResouceId) {
        return makeText(ResourceUtil.getString(msgResouceId));
    }

    private ToastManager makeText(String msg) {
        if (null != showText) {
            showText.setText(msg);
        }
        return this;
    }

    private ToastManager setDurations(int duration) {
        this.setDuration(duration);
        return this;
    }

    /**
     * 显示通知
     *
     * @param resouceId 显示的字符串 id
     * @param length    显示时间
     */
    public static void show(int resouceId, int length) {
        getInstance().makeText(resouceId).setDurations(length).show();
    }

    /**
     * 显示通知
     *
     * @param resouceId 显示的字符串
     */
    public static void show(int resouceId) {
        show(resouceId, 300);
    }

    @Deprecated
    public static void show(String msg, int length) {
        getInstance().makeText(msg).setDurations(length).show();
    }

    @Deprecated
    public static void show(String msg) {
        show(msg, 300);
    }
}
