package org.loader.module2module.app;

import android.app.Application;

import org.router.annoation.Components;
import org.router.helper.RouterAnnoationHelper;

@Components({"shoplib", "bbslib"})
public class RouterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupRouter();
    }

    private void setupRouter() {
        RouterAnnoationHelper.install();
    }
}
