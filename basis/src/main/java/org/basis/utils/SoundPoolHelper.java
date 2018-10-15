package org.basis.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Looper;
import android.os.SystemClock;

/**
 * SoundPool播放音频文件大小要小于100k
 */
public class SoundPoolHelper {
    private SoundPool mSound;
    private boolean loadComplete = false;
    private Object obj = new Object();
    private long interval = 0L;
    private int[] soundIds;

    /**
     * @param ctx Context
     * @param resId resouceId
     */
    public SoundPoolHelper(Context ctx, int resId) {
        this(ctx,new int[]{resId},0);
    }

    /**
     * @param ctx Context
     * @param resId resouceId
     * @param loope 循环次数
     * @param interval 播放间隔
     */
    public SoundPoolHelper(Context ctx, int resId,int loope,long interval) {
        int[] resIds = new int[loope];
        for (int i = 0; i < loope; i++) {
            resIds[i] = resId;
        }
        init(ctx,resIds,interval);
    }

    /**
     * @param ctx Context
     * @param resIds resouceIds
     * @param resIds 播放间隔
     */
    public SoundPoolHelper(Context ctx, int[] resIds,long interval) {
        init(ctx,resIds,interval);
    }

    /**
     * @param resIds
     */
    private void init(Context ctx, int[] resIds,long interval) {
        this.interval = interval;
        mSound = new SoundPool(resIds.length, AudioManager.STREAM_MUSIC, 0);
        loadComplete = false;
        loads(ctx,resIds);
        mSound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadComplete = true;
                synchronized (obj) {//加载完毕，唤醒
                    obj.notifyAll();
                }
            }
        });
    }

    private void loads(Context ctx,int[] resIds){
        if (null != resIds && resIds.length > 0){
            int len = resIds.length;
            soundIds = new int[len];
            for (int i = 0; i < len ; i++) {
                soundIds[i] = mSound.load(ctx, resIds[i], 1);
            }
        }
    }

    /**
     * 参照HandlerThread 处理逻辑
     * 避免在未load完毕执行play方法
     */
    public void start() {
        Logger.e("start");
        if (null == mSound || null == soundIds)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    while (!loadComplete) {//未load完毕 一直等待
                        try {
                            Logger.e("wait ...");
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (loadComplete){
                    try {
                        for (int soundId : soundIds) {
                            Logger.e("play ...");
                            mSound.play(
                                    soundId,//load()返回的soundId
                                    15,// 左声道音量
                                    15,// 右声道音量
                                    1,// 优先级
                                    0,// 循环播放次数
                                    1);// 回放速度，该值在0.5-2.0之间 1为正常速度
                            if (interval > 0) {
                                Thread.sleep(interval);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void release() {
        if (null != mSound) {
            mSound.release();
        }
    }
}
