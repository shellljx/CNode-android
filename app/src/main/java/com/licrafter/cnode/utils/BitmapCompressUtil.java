package com.licrafter.cnode.utils;/**
 * Created by Administrator on 2016/3/4.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author: lijinxiang
 * date: 2016/3/4
 **/
public class BitmapCompressUtil {
    private static final int MAX_WIDTH = 480;
    private static final int MAX_HEIGHT = 800;
    private static final double MAX_SIZE = 150.00;//KB


    public static String compressToPath(String originalPath, String targetPath) {
        try {
            Bitmap bitmap = BitmapCompressUtil.decodeSampledBitmapFromPath(originalPath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            double size = byteArrayOutputStream.toByteArray().length / 1024;
            if (size > MAX_SIZE) {
                int quality = 100;
                while ((byteArrayOutputStream.toByteArray().length / 1024) > MAX_SIZE) {
                    if (quality < 80) {
                        break;
                    }
                    byteArrayOutputStream.reset();
                    quality = quality - 5;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
                }
            }
            File file = new File(targetPath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byteArrayOutputStream.writeTo(fileOutputStream);
            fileOutputStream.close();
            byteArrayOutputStream.close();
            return targetPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static Bitmap decodeSampledBitmapFromPath(String originalPath) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        rotateBitmap(BitmapFactory.decodeFile(originalPath, options), originalPath);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(originalPath, options);
    }


    /**
     * 计算缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * rotate the bitmap
     *
     * @param bitmap
     * @param path
     * @return
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, String path) {
        int degree = getDegree(path);
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return null;
    }


    /**
     * get the orientation of the bitmap {@link ExifInterface}
     *
     * @param path
     * @return
     */
    private static int getDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}
