package org.loader.module2module.application;

import org.basis.BasisApplication;
import org.router.annoation.Components;
import org.router.helper.RouterAnnoationHelper;

@Components({"shoplib", "bbslib"})
public class RouterApplication extends BasisApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setupRouter();
    }

    private void setupRouter() {
        RouterAnnoationHelper.install();
    }
}
