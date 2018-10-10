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

/**
 * @author: BaiCQ
 * @ClassName: ProgressActivity
 * @date: 2018/10/10
 * @Description: 展示Progress功能
 */
public class ProgressActivity extends BaseActivity {
    private int position = 0;
    private SpinKitView itemView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        titleBar = findViewById(R.id.titleBar);
        itemView = findViewById(R.id.spin_kit);
        refreshStyle();
        titleBar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, NetMasterActivity.class));
            }
        });
    }

    private void refreshStyle() {
        position = position % 15;
        itemView.setBackgroundColor(colors[position % colors.length]);
        Style style = Style.values()[position];
        Sprite drawable = SpriteFactory.create(style);
        itemView.setIndeterminateDrawable(drawable);
    }

    public void modefy(View view) {
        position++;
        refreshStyle();
    }
}
