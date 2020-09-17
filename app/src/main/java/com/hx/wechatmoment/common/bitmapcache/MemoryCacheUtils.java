package com.hx.wechatmoment.common.bitmapcache;

import android.graphics.Bitmap;
import android.util.LruCache;


/**
 * Desc 三级缓存之内存缓存
 * <p>
 * 进行内存缓存，就一定要注意一个问题，那就是内存溢出（OutOfMemory）
 * 为什么会造成内存溢出？
 * Android 虚拟机默认分配给每个App 16M的内存空间，真机会比16M大，但任会出现内存溢出的情况
 * Android 系统在加载图片时是解析每一个像素的信息，再把每一个像素全部保存至内存中
 * 图片大小 = 图片的总像素 * 每个像素占用的大小
 * 单色图：每个像素占用1/8个字节，16色图：每个像素占用1/2个字节，256色图：每个像素占用1个字节，24位图：每个像素占用3个字节（常见的rgb构成的图片）
 * <p>
 * 例如一张1920x1080的JPG图片，在Android 系统中是以ARGB格式解析的，即一个像素需占用4个字节，图片的大小=1920x1080x4=7M
 * 实现方法：
 * <p>
 * 通过 HashMap<String,Bitmap>键值对的方式保存图片，key为地址，value为图片对象，但因是强引用对象，很容易造成内存溢出，可以尝试SoftReference软引用对象
 * <p>
 * 通过 HashMap<String, SoftReference<Bitmap>>SoftReference 为软引用对象（GC垃圾回收会自动回收软引用对象），但在Android2.3+后，系统会优先考虑回收弱引用对象，官方提出使用LruCache
 * <p>
 * 通过 LruCache<String,Bitmap> least recentlly use 最少最近使用算法
 * 会将内存控制在一定的大小内, 超出最大值时会自动回收, 这个最大值开发者自己定
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
class MemoryCacheUtils {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        //得到手机最大允许内存的1/8,即超过指定内存,则开始回收
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;
        //需要传入允许的内存最大值,虚拟机默认内存16M,真机不一定相同
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            /**
             * 用于计算每个条目的大小
             * @param key 键
             * @param value 值
             * @return int
             */
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }

    /**
     * 往内存写图片
     *
     * @param url    url
     * @param bitmap Bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }

    /**
     * 从内存读取图片
     *
     * @param url url
     * @return Bitmap
     */
    public Bitmap getBitmapFromMemory(String url) {
        return mMemoryCache.get(url);
    }
}
