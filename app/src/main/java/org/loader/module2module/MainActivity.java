package org.loader.module2module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.basis.common.RouterConfig;
import org.basis.ui.base.BaseActivity;
import org.basis.utils.ToastManager;
import org.router.Router;

public class MainActivity extends BaseActivity {
    boolean textMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleBar = findViewById(R.id.titleBar);
        //展示动态切换titleBar右侧显示mode： text/icon
        titleBar.setOnRightListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textMode = !textMode;
                        if (textMode){
                            titleBar.setRightText(R.string.app_name,R.drawable.selector_titlebar_right_bg);
                        }else {
                            titleBar.setRightIcon(R.mipmap.icon_add);
                        }
                    }
                });

    }

    public void progress(View view){
        startActivity(new Intent(mActivity, ProgressActivity.class));
    }

    public void basecontroler(View view){
        startActivity(new Intent(mActivity, TestAbsActivity.class));
    }

    public void routerActivity(View view){
        ToastManager.show("router activity");
        Router.startActivity(mActivity, RouterConfig.PATT_BActivity, "close");
    }
    public void routerService(View view){
        ToastManager.show("router service");
        Router.startService(mActivity, RouterConfig.PATT_TestService);
    }
}
