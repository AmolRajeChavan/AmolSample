package com.threemb.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Amol on 1/28/2018.
 */

public class AppConstants extends MultiDexApplication {

    public static final String URL_GET_DATA = "http://adgebra.co.in/AdServing/PushNativeAds?pid=323&mkeys=&dcid=9&nads=2&deviceId=2&ip=203.109.101.177&url=demo.inuxu.org&pnToken=3KfutF4QBdjzSL9z";
    public static final String GET_DATA ="lbl_get_data" ;

    public static final String STATUS_DRAFT = "Draft";
    public static final String STATUS_IN_PROGRESS = "In Progress";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_CANCELLED = "Cancelled";

    public static final String STATUS_DRAFT_CODE = "3001";
    public static final String STATUS_IN_PROGRESS_CODE = "3002";
    public static final String STATUS_COMPLETED_CODE = "3003";
    public static final String STATUS_CANCELLED_CODE = "3004";

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);
    }
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
