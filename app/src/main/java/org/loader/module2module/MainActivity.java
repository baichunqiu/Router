package org.loader.module2module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.basis.common.RouterConfig;
import org.basis.ui.base.BaseActivity;
import org.basis.utils.MediaPlayerHelper;
import org.basis.utils.SoundPoolHelper;
import org.basis.utils.ToastManager;
import org.loader.module2module.media.ActivityListView;
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
    private MediaPlayerHelper mediaPlayerHelper;
    public void mediaHelper(View view){
        mediaPlayerHelper = new MediaPlayerHelper(mActivity,R.raw.wx_shake);
        mediaPlayerHelper.start();
    }
    private SoundPoolHelper soundPoolHelper;
    public void soundPoolHelper(View view){
        soundPoolHelper = new SoundPoolHelper(mActivity,R.raw.wx_shake,5,2500);
        soundPoolHelper.start();
    }

    public void adapterAutoSize(View view){
        startActivity(new Intent(mActivity, AutoSizeActivity.class));
    }
    public void videoPlayer(View view){
        startActivity(new Intent(mActivity, ActivityListView.class));
    }
}
