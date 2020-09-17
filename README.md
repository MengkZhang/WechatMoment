# WechatMoment
homeWork -- 仿微信朋友圈

## 项目架构

架构采用 MVVM架构 数据回调用LiveData 网络请求用Retrofit + RxJava实现

图片框架Glide结合自定义图片加载实现三级缓存


## 冷启动优化
问题：点击APP白屏或黑屏后再展示Activity

原因：界面都是在手机window上绘制出来的，我们开发时的Activity最终能显示出来，都需要通过measure、layout等过程之后，在window上绘制才能看到界面。在应用启动之后，window就已经存在了，Application初始化时，会从配置的Theme里，读取一个名为“android:windowBackground”的属性就是窗口背景，该背景就会在window上绘制出来，同样我们也能看到了。如果我们采用的Theme里，没有覆盖该属性值，则会采用该Theme的默认值，大部分系统提供的Theme里，windowBackground都是白色，所以我们会看到白屏了（减少在Application的onCreate()执行耗时初始化，建议采用Google的startUp方式）



方案：给window设置背景theme
```
<bitmap
            android:gravity="top|center_horizontal"
            android:scaleType="center"
            android:src="@mipmap/ic_v5_log_new" />
```

## 整体布局

CoordinatorLayout + RecyclerView实现
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.swiperefreshlayout.widget.SwipeRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/sfl"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:id="@+id/coordinatorLayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <!-- 被依赖view （dependency）-->
            <com.google.android.material.appbar.AppBarLayout
                android:id="@+id/app_bar"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_behavior="com.hx.wechatmoment.view.widget.FlingBehavior">

                <com.google.android.material.appbar.CollapsingToolbarLayout
                    android:id="@+id/toolbar_layout"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    app:layout_scrollFlags="scroll|exitUntilCollapsed"
                    app:toolbarId="@+id/toolbar">

                    <!-- 可伸缩背景图布局文件 -->
                    <include layout="@layout/top_view_layout"/>


                </com.google.android.material.appbar.CollapsingToolbarLayout>
            </com.google.android.material.appbar.AppBarLayout>


            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/rv_list"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layout_behavior="@string/appbar_scrolling_view_behavior" />

        </androidx.coordinatorlayout.widget.CoordinatorLayout>

        <!--标题view-->
        <include layout="@layout/title_layout" />

    </FrameLayout>


</androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
```

- 滑动设置TitleView的透明度实现朋友圈的效果：在AppBarLayout的addOnOffsetChangedListener监听中设置TitleView的Alpha值(以及沉浸式状态栏)
```
 mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= Constants.ZERO) {
                //将标题栏的颜色设置为完全不透明状态
                mRlTitleView.setAlpha(0f);
                mStatusView.setAlpha(0f);
                StatusBarUtil.setImmersiveStatusBar(MomentActivity.this, false);
            } else {
                int abs = Math.abs(verticalOffset);
                if (abs <= mAppBarLayoutHeight - (mTitleViewHeight + StatusBarUtil.getStatusBarHeight(MomentActivity.this))) {
                    float alpha = (float) abs / mAppBarLayoutHeight;
                    mRlTitleView.setAlpha(alpha);
                    mStatusView.setAlpha(alpha);
                    StatusBarUtil.setImmersiveStatusBar(MomentActivity.this, false);
                } else {
                    //将标题栏的颜色设置为完全不透明状态
                    mRlTitleView.setAlpha(1.0f);
                    mStatusView.setAlpha(1.0f);
                    StatusBarUtil.setImmersiveStatusBar(MomentActivity.this, true, ContextCompat.getColor(MomentActivity.this, R.color.home_status_bar_color));
                }
            }
        });
```


- SwipeRefreshLayout和滑动控件冲突解决
```
mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= Constants.ZERO) {
                mSwipeRefreshLayout.setEnabled(true);
            } else {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
```




## 九宫格图片控件
#### NineGridView 集成自ViewGroup 重写onMeasure()和onLayout()等主要方法
- 确定图片布局风格 微信风格
```
    private int singleImageSize = 250;              // 单张图片时的最大大小,单位dp
    private float singleImageRatio = 0.7f;          // 单张图片的宽高比(宽/高)
    private int maxImageSize = 9;                   // 最大显示的图片数
    private int gridSpacing = 3;                    // 宫格间距，单位dp
    private int mode = MODE_FILL;                   // 默认使用fill模式

    private int columnCount;    // 列数
    private int rowCount;       // 行数
    private int gridWidth;      // 宫格宽度
    private int gridHeight;     // 宫格高度
```

- onMeasure()方法计算出容器的宽高
```
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (mImageInfo != null && mImageInfo.size() > 0) {
            if (mImageInfo.size() == 1) {
                gridWidth = singleImageSize > totalWidth ? totalWidth : singleImageSize;
                gridHeight = (int) (gridWidth / singleImageRatio);
                //矫正图片显示区域大小，不允许超过最大显示范围
                if (gridHeight > singleImageSize) {
                    float ratio = singleImageSize * 1.0f / gridHeight;
                    gridWidth = (int) (gridWidth * ratio);
                    gridHeight = singleImageSize;
                }
            } else {
                if (mImageInfo.size() == 4) {
                    //4张图片 宽高都按总宽度的 1/2
                    gridWidth = gridHeight = (totalWidth - gridSpacing * 2) / 2;
                } else {
                    //这里5~9几张图片，宽高都按总宽度的 1/3
                    gridWidth = gridHeight = (totalWidth - gridSpacing * 2) / 3;
                }
            }
            width = gridWidth * columnCount + gridSpacing * (columnCount - 1) + getPaddingLeft() + getPaddingRight();
            height = gridHeight * rowCount + gridSpacing * (rowCount - 1) + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
```
- onLayout()方法算间距
```
        int childrenCount = mImageInfo.size();
        for (int i = 0; i < childrenCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
            
            int rowNum = i / columnCount;
            int columnNum = i % columnCount;
            int left = (gridWidth + gridSpacing) * columnNum + getPaddingLeft();
            int top = (gridHeight + gridSpacing) * rowNum + getPaddingTop();
            int right = left + gridWidth;
            int bottom = top + gridHeight;
            childrenView.layout(left, top, right, bottom);
            
            if (mImageLoader != null) {
                mImageLoader.onDisplayImage(getContext(), childrenView, mImageInfo.get(i).thumbnailUrl);
            }
        }
```
- 设置数据的时候算几行几列 控制怎样摆放
```
        int imageCount = imageInfo.size();
        if (maxImageSize > 0 && imageCount > maxImageSize) {
            imageInfo = imageInfo.subList(0, maxImageSize);
            imageCount = imageInfo.size();   //再次获取图片数量
        }

        //默认是3列显示，行数根据图片的数量决定
        rowCount = imageCount / 3 + (imageCount % 3 == 0 ? 0 : 1);
        columnCount = 3;
        //grid模式下，显示4张使用2X2模式
        if (mode == MODE_GRID) {
            if (imageCount == 4) {
                rowCount = 2;
                columnCount = 2;
            }
        }
```

- 其他细节 保证View的复用，避免重复创建等

## 自定义评论列表控件

#### CommentsView集成自LinearLayout(仿微信的实现方式，避免列表嵌套列表)

```
        removeAllViews();
        if (mDatas == null || mDatas.size() <= 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        for (int i = 0; i < mDatas.size(); i++) {
            View view = getView(i);
            if (view != null) {
                addView(view, i, layoutParams);
            }
        }
```

## 图片加载控件实现三级缓存

#### MomentBitmapManager 网络请求下载图片 存内存 存磁盘 再次加载 先找内存 再找磁盘 
```
    public void display(ImageView ivPic, String url, int placeId) {
        ivPic.setImageResource(placeId);
        Bitmap bitmap;

        //读取内存缓存
        bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            return;
        }

        //读取磁盘缓存
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            return;
        }

        //读取网络缓存
        mNetCacheUtils.getBitmapFromNet(ivPic, url);
    }
```

- 存磁盘 把图片的url当做文件名,并进行MD5加密
```
        String fileName = EncryptUtils.string2MD5UTF8(url);
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
```

- 存内存

进行内存缓存，就一定要注意一个问题，那就是内存溢出（OutOfMemory）
为什么会造成内存溢出？
Android 虚拟机默认分配给每个App 16M的内存空间，真机会比16M大，但任会出现内存溢出的情况
Android 系统在加载图片时是解析每一个像素的信息，再把每一个像素全部保存至内存中

图片大小 = 图片的总像素 * 每个像素占用的大小
单色图：每个像素占用1/8个字节，16色图：每个像素占用1/2个字节，

256色图：每个像素占用1个字节，24位图：每个像素占用3个字节（常见的rgb构成的图片）

例如一张1920x1080的JPG图片，在Android 系统中是以ARGB格式解析的，即一个像素需占用4个字节，图片的大小=1920x1080x4=7M


实现方法：

通过 HashMap<String,Bitmap>键值对的方式保存图片，key为地址，value为图片对象，但因是强引用对象，很容易造成内存溢出，可以尝试SoftReference软引用对象

通过 HashMap<String, SoftReference<Bitmap>>SoftReference 为软引用对象（GC垃圾回收会自动回收软引用对象），但在Android2.3+后，系统会优先考虑回收弱引用对象，官方提出使用LruCache

通过 LruCache<String,Bitmap> least recentlly use 最少最近使用算法
会将内存控制在一定的大小内, 超出最大值时会自动回收, 这个最大值开发者自己定
```
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
```
```
    /**
     * 往内存写图片
     *
     * @param url    url
     * @param bitmap Bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }
```

#### 刷新mSwipeRefreshLayout和分页RecyclerView的常规操作