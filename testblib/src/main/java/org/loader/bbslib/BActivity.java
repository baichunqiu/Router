package org.loader.bbslib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.basis.common.RouterConfig;
import org.basis.ui.base.BaseActivity;
import org.basis.utils.Logger;
import org.basis.utils.ToastManager;
import org.router.Router;
import org.router.annoation.AutoRouter;

@AutoRouter
public class BActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setTextSize(30);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setBackgroundResource(R.color.color_green_blue);
        tv.setGravity(Gravity.CENTER);
        tv.setText("BActivity : testb");
        setContentView(tv);
        String paramter = getIntent().getStringExtra(Router.KEY_STRING);
        Logger.e(TAG, "BActivity paramter = " + paramter);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastManager.show("BActivity");
                boolean is = Router.startActivity(BActivity.this, RouterConfig.PATT_AActivity, "open");
                Logger.e(TAG, "BActivity is = " + is);
            }
        });
    }
}
