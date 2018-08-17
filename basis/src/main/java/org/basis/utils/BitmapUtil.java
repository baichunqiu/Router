package org.basis.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.View;

import org.basis.UIUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author: BaiCQ
 * @ClassName: DeviceUtil
 * @Description: 屏幕相关的工具类
 */
public class BitmapUtil {

    /**
     * @param view       缓存的view
     * @param fileName   保存本地文件
     * @param maxMeasure 最大尺寸（宽/高）
     */
    public static void saveDrawingCache(final View view, final String fileName, final int maxMeasure) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        final Bitmap bmp = view.getDrawingCache();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (bmp != null) {
                    saveBitmap(fileName, compressBitmap(bmp, maxMeasure));
                }
                UIUtil.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.setDrawingCacheEnabled(false);
                    }
                });
            }
        }).start();
    }

    /**
     * 压缩bitmap
     * @param bitmap     bitmap对象
     * @param maxMeasure 最大尺寸
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int maxMeasure) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        if (max > maxMeasure) {
            float scale = max / maxMeasure;
            int w = Math.round(width / scale);
            int h = Math.round(height / scale);
            bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
        }
        return bitmap;
    }

    /**
     * bitmap 缓存到本地sd卡
     * @param bitName 文件名
     * @param mBitmap bitmap对象
     */
    public static void saveBitmap(String bitName, Bitmap mBitmap) {
        int index = bitName.lastIndexOf("/");
        String folder = bitName.substring(0, index);
        File f1 = new File(folder);
        if (!f1.exists() || f1.isDirectory()) {
            f1.mkdirs();
        }
        File f = new File(bitName);
        try {
            f.createNewFile();
        } catch (IOException e) {
        }

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {}
        return bitmap;
    }

    /**
     * 获取image 缩略图
     * @param imagePath
     * @param maxWidth 最大宽
     * @param maxHeight 高
     * @param isDeleteFile 是否删除
     * @return
     */
    public static Bitmap getImageThumbnail(String imagePath, int maxWidth,int maxHeight, boolean isDeleteFile) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        FileInputStream inputStream = null;
        File file = new File(imagePath);
        try {
            if(file != null && file.exists()){
                inputStream = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(inputStream, null,options);
                if(isDeleteFile){
                    file.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    /**
     * 计算bitmap压缩比
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    /**
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height) {
        Bitmap bitmap = null;
        try {
            // 获取视频的缩略图
            bitmap = createVideoThumbnail(videoPath, width,2000);
            if (null == bitmap){
                bitmap = createVideoThumbnail(videoPath, width,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取视频缩略图
     * @param filePath   视频路径
     * @param maxMeasure 最大宽高尺寸
     * @return
     */
    public static Bitmap createVideoThumbnail(String filePath, int maxMeasure,int durationTime) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(durationTime);
        } catch (IllegalArgumentException ex) {
        } catch (RuntimeException ex) {
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }
        if (bitmap == null) {
            return null;
        }
        return compressBitmap(bitmap,maxMeasure);
    }
}
