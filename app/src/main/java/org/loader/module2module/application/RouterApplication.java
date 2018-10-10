package org.loader.module2module.application;

import android.graphics.Color;

import org.basis.BasisApplication;
import org.basis.network.view.TitleBar;
import org.loader.module2module.R;
import org.router.annoation.Components;
import org.router.helper.RouterAnnoationHelper;

@Components({"shoplib", "bbslib"})
public class RouterApplication extends BasisApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setupRouter();

        TitleBar.setDefaultBuild(new TitleBar.DefaultBuild(12, 0)
                .buildLeftDrawable(getResources().getDrawable(R.mipmap.icon_back))
                .buildTitleColor(Color.parseColor("#FFFFFF"))
                .buildTitleSize(16)
                .buildPressDrawable(R.drawable.selector_titlebar_press)
                .buildBackGroundColor(getResources().getColor(R.color.color_title_bg)));
    }

    private void setupRouter() {
        RouterAnnoationHelper.install();
    }
}
