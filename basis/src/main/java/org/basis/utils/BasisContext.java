package org.basis.utils;

import android.app.Application;

public class BasisContext {

    private static Application sInstance;

    public static Application get() {
        if (sInstance == null) {
            android.app.Application app = null;
            try {
                app = (android.app.Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
                if (app == null) {
                    throw new IllegalStateException("Static initialization of Applications must be on main thread.");
                }
                Logger.e("android.app.AppGlobals");
            } catch (final Exception e) {
                try {
                    app = (android.app.Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
                    Logger.e("android.app.ActivityThread");
                } catch (final Exception ex) {
                    e.printStackTrace();
                }
            } finally {
                sInstance = app;
            }
        }
        return sInstance;
    }
}
