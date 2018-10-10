package org.loader.shoplib.ui;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.basis.common.RouterConfig;
import org.basis.ui.AbsActivity;
import org.basis.utils.Logger;
import org.basis.utils.ToastManager;
import org.router.Router;
import org.router.annoation.AutoRouter;

@AutoRouter
public class AActivity extends AbsActivity {
    /**
     * 相当于 setContentView
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater) {
        TextView tv = new TextView(this);
        tv.setTextSize(20);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setText("AActivity : testa");
        String paramter = getIntent().getStringExtra(Router.KEY_STRING);
        boolean paramterb = getIntent().getBooleanExtra(Router.KEY_BOOLE, false);
        Logger.e("TAG", "AActivity paramter = " + paramter);
        Logger.e("TAG", "AActivity paramterb = " + paramterb);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastManager.show("AActivity");
                boolean is = Router.startActivity(AActivity.this, RouterConfig.PATT_BActivity, "close");
                Logger.e("AActivity", "AActivity is = " + is);
            }
        });

        return tv;
    }
}
