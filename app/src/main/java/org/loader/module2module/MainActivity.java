package org.loader.module2module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.basis.common.RouterConfig;
import org.basis.network.view.progress.SpinKitView;
import org.basis.network.view.progress.SpriteFactory;
import org.basis.network.view.progress.Style;
import org.basis.network.view.progress.sprite.Sprite;
import org.basis.ui.base.BaseActivity;
import org.basis.utils.Logger;
import org.basis.utils.ToastManager;
import org.router.Router;

import static org.basis.network.view.progress.Colors.colors;

public class MainActivity extends BaseActivity {

    private int position = 0;
    SpinKitView itemView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemView = findViewById(R.id.spin_kit);
        refreshStyle();
    }

    private void refreshStyle() {
        position = position % 15;
        itemView.setBackgroundColor(colors[position % colors.length]);
        Style style = Style.values()[position];
        Sprite drawable = SpriteFactory.create(style);
        itemView.setIndeterminateDrawable(drawable);
    }

    public void click(View view) {
//        boolean is = Router.startService(MainActivity.this, RouterConfig.PATT_TestService);
//        Logger.e(TAG, "is = " + is);
//        ToastManager.show(is + "");
        position++;
        refreshStyle();
    }

    public void clickA(View view) {
        ToastManager.show("clickA");
        startActivity(new Intent(mActivity, NetWorkActivity.class));
    }
}
