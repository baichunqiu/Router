package org.basis.network.view;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.loader.utilslib.R;

/**
 * @author: BaiCQ
 * @ClassName: LoadDialog
 * @Description: 标准等待进度条
 */
public class LoadDialog extends Dialog {
    private Dialog dialog;
    private View rootView;
    private TextView textView;
    private String dialogMsg;
    private ProgressBar progressBar;

    public LoadDialog(Activity activity) {
        this(activity, "");
    }

    public LoadDialog(Activity activity, String dialogMsg) {
        super(activity);
        dialog = new Dialog(activity, R.style.CustomProgressDialog);
        rootView = LayoutInflater.from(activity).inflate(R.layout.net_layout_load_dialog, null);
        progressBar = rootView.findViewById(R.id.prgressBar);
        textView = rootView.findViewById(R.id.tv_load_msg);
        if (TextUtils.isEmpty(dialogMsg)){
            dialogMsg = activity.getString(R.string.net_loading);
        }
        this.dialogMsg = dialogMsg;
        textView.setText(dialogMsg);
        // 允许点返回键取消
        setCancelable(true);
        // 触碰其他地方不消失
        dialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(rootView, params);
        if (null != dialog){
            dialog.show();
        }
    }

    @Override
    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getMsg(){
        return dialogMsg;
    }
}