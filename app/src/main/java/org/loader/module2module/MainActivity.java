package org.loader.module2module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.basis.UIUtil;
import org.basis.common.Constant;
import org.basis.common.RouterConfig;
import org.basis.ui.base.BaseActivity;
import org.basis.utils.BroadcastManager;
import org.basis.utils.Logger;
import org.basis.utils.ToastManager;
import org.router.Router;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        boolean is = Router.startActivity(MainActivity.this, RouterConfig.PATT_AActivity, true);
        Logger.e(TAG, "is = " + is);
        ToastManager.show(is + "");
    }

    public void clickA(View view) {
        ToastManager.show("clickA");
        startActivity(new Intent(mActivity, NetWorkActivity.class));
    }
}
