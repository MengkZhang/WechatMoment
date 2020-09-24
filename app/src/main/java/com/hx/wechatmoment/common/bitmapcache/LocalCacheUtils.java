package com.hx.wechatmoment.common.bitmapcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hx.wechatmoment.common.util.EncryptUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Desc 三级缓存之磁盘缓存
 * <p>
 * 把图片的url当做文件名,并进行MD5加密
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
class LocalCacheUtils {

    private Context mContext;

    public LocalCacheUtils(Context context) {
        this.mContext = context;
    }

    /**
     * 从网络中获取图片后 保存到本地
     *
     * @param url    图片地址
     * @param bitmap bitmap对象
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        //String fileName = url;//把图片的url当做文件名,并进行MD5加密
        String fileName = EncryptUtil.stringToUtf(url);
        try {
            File file = new File(getImgPathFile(), fileName);
            //通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }

            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmapFromLocal(String url) {
        //把图片的url当做文件名,并进行MD5加密
        String fileName = EncryptUtil.stringToUtf(url);
        try {
            File file = new File(getImgPathFile(), fileName);
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private File getImgPathFile() {
        return new File(mContext.getCacheDir(), "image");
    }
}
