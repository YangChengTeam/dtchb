package com.yc.redevenlopes.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitmapCompressUtils {

    public static Bitmap optsCompress(String srcPath) {
        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int degree = readPictureDegree(srcPath);

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        bitmap = compressImage(bitmap);
        /**
         * 把图片旋转为正的方向
         */
        bitmap = rotaingImageView(degree, bitmap);
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }


    public static Bitmap optsCompressTwo(String srcPath) {
        int degree = readPictureDegree(srcPath);
        Bitmap  bitmap = BitmapFactory.decodeFile(srcPath);
        bitmap = compressImage(bitmap);
        /**
         * 把图片旋转为正的方向
         */
        bitmap = rotaingImageView(degree, bitmap);
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }



    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    public static int readPictureDegree(String path) {
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

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 图片裁剪
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap  imageCrop(Bitmap bitmap, int num1, int num2, boolean isRecycled){
        if (bitmap == null){
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int retX, retY;
        int nw, nh;
        if (w > h){
            if (h > w * num2 / num1){
                nw = w;
                nh = w * num2 / num1;
                retX = 0;
                retY = (h - nh) / 2;
            } else{
                nw = h * num1 / num2;
                nh = h;
                retX = (w - nw) / 2;
                retY = 0;
            }
        } else{
            if (w > h * num2 / num1){
                nh = h;
                nw = h * num2 / num1;
                retY = 0;
                retX = (w - nw) / 2;
            } else{
                nh = w * num1 / num2;
                nw = w;
                retY = (h - nh) / 2;
                retX = 0;}
        }
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)&& !bitmap.isRecycled()){
            bitmap.recycle();//回收原图片
            bitmap = null;
        }
        return bmp;
    }


    public static String saveBmpToSd( Bitmap bm,
                                     boolean recyle) {
        String dir = Environment.getExternalStorageDirectory()+"xinshen";
        String filename = String.valueOf(System.currentTimeMillis()) + ".jpg";
        boolean ret = true;
        if (bm == null) {
            return "";}
        File dirPath = new File(dir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        File file = new File(dir + filename);
        OutputStream outStream = null;
        try {
            file.createNewFile();
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        } finally {
            try {
                if (outStream != null) outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (recyle && !bm.isRecycled()) {
                bm.recycle();
                bm = null;
            }
        }
        return "";
    }
    /**
     * 保存旋转后的Bitmap图片在SD卡中
     * 如果没有SD卡则存在手机中
     * @return 保存成功时返回图片的路径，失败时返回null
     */
    public static String savePhotoToSD( Bitmap mbitmap) {
        BufferedOutputStream bos=null;
        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        String filePathss = Environment.getExternalStorageDirectory()+"xinshen";
        File file = new File(filePathss );
        String filePath = file + "/" +fileName;
        // 判断文件是否已经存在，不存在则创建
        if ( !file.exists() ) {
            file.mkdirs();
        }
        File filess = new File(filePath);
        if (!filess.exists()) {
            try {
                filess.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
             bos = new BufferedOutputStream(new FileOutputStream(filess));
            // 把数据写入文件，100表示不压缩
            mbitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            Log.d("ccc", "-------savePhotoToSD: "+filePath);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (mbitmap != null) {
                    mbitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static String saveToLocal(Bitmap bitmap)  {
        String bitNames = String.valueOf(System.currentTimeMillis());
        File file = new File("/storage/emulated/0/Luban/image/" + bitNames + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
                //保存图片后发送广播通知更新数据库
                 Uri uri = Uri.fromFile(file);
                String absolutePath = file.getAbsolutePath();
                return absolutePath;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
