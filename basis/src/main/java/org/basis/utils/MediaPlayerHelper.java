package org.basis.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;

import java.io.IOException;

/**
 * MediaPlayer Helper
 */
public class MediaPlayerHelper implements OnCompletionListener, OnErrorListener {
    private MediaPlayer mediaPlayer;

    public MediaPlayerHelper(Context ctx, int resId) {
        mediaPlayer = MediaPlayer.create(ctx, resId);
    }

    public MediaPlayerHelper(String localPath) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(localPath);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayer = null;
        }
    }

    public void start() {
        Logger.e("start");
        if (null != mediaPlayer && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private synchronized void stop() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer = null;
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stop();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        release();
        return false;
    }

    public void release() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
