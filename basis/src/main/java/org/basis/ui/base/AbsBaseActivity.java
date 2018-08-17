package org.basis.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import org.basis.common.Constant;

/**
 * @author: BaiCQ
 * @ClassName: AbsBaseActivity
 * @date: 2018/8/17
 * @Description: 基类 统一处理app exit功能和返回键点击事件
 *              给子类提供自定义action的buildFilterAction入口和广播处理的onReceive入口
 */
public abstract class AbsBaseActivity extends FragmentActivity implements IRefresh {
    public String mPackageName;
    // 退出应用的广播接受者
    private BroadcastReceiver basisReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != basisReceiver) {
            unregisterReceiver(basisReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basisReceiver = new BasisReceiver();
        mPackageName = this.getPackageName();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(mPackageName + Constant.ACTION_APP_EXIT);
        buildFilterAction(intentfilter);
        //注册统一广播 处理退出app的功能
        registerReceiver(basisReceiver, intentfilter);
    }

    public class BasisReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ((mPackageName + Constant.ACTION_APP_EXIT).equals(action)) {//退出
                finish();
            }else{
                AbsBaseActivity.this.onReceive(context,intent);
            }
        }
    }

    //给子类提供一个自定义action的入口
    protected abstract void buildFilterAction(IntentFilter intentfilter);

    //处理自定义action的入口 参数参考广播的 onReceive
    protected abstract void onReceive(Context context, Intent intent);

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackCode();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackCode();
    }

    /**
     * 统一处理activity 的销毁关闭和back code 的时间
     */
    public void onBackCode() {
        finish();
    }
}
