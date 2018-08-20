package org.loader.bbslib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.basis.utils.Logger;
import org.router.annoation.AutoRouter;

/**
 * @author: BaiCQ
 * @ClassName: TestService
 * @date: 2018/8/20
 * @Description: TestService的备注
 */
@AutoRouter
public class TestService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("TestService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e("TestService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

}
