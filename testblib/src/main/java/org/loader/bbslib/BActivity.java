package org.loader.bbslib;

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
public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setTextSize(30);
        tv.setText("BActivity : testb");
        setContentView(tv);
        String paramter = getIntent().getStringExtra(Router.KEY_STRING);
        Logger.e("TAG", "BActivity paramter = "+paramter);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BActivity.this, "BActivity", Toast.LENGTH_SHORT).show();
                boolean is = Router.startActivity(BActivity.this, RouterConfig.PATT_AActivity,"open");
                Logger.e("AActivity", "BActivity is = " + is);
            }
        });
    }
}
