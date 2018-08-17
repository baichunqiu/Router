package org.loader.shoplib.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.basis.constant.RouterConfig;
import org.router.Router;
import org.basis.utils.Logger;
import org.router.annoation.AutoRouter;

@AutoRouter
public class AActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setTextSize(20);
        tv.setText("AActivity : testa");
        setContentView(tv);
        String paramter = getIntent().getStringExtra(Router.KEY_STRING);
        boolean paramterb = getIntent().getBooleanExtra(Router.KEY_BOOLE, false);
        Logger.e("TAG", "AActivity paramter = " + paramter);
        Logger.e("TAG", "AActivity paramterb = " + paramterb);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AActivity.this, "AActivity", Toast.LENGTH_SHORT).show();
                boolean is = Router.startActivity(AActivity.this, RouterConfig.PATT_BActivity, "close");
                Logger.e("AActivity", "AActivity is = " + is);
            }
        });
    }
}
