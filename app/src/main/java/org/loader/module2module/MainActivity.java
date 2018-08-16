package org.loader.module2module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.basis.common.RouterConfig;
import org.basis.utils.Logger;
import org.router.Router;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        boolean is = Router.startActivity(MainActivity.this, RouterConfig.PATT_AActivity,true);
        Logger.e("MainActivity","is = "+is);
    }
}
