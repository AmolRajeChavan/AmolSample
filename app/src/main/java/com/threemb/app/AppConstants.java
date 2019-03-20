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

    public static final String KEY_FRAGEMENT_NAME = "fragement_name";
    public static final String LOGIN_REQUEST ="login_request" ;
    public static final String SEND_INJEST ="injest_request";
    public static final String SEND_NOTE="send_note";
    public static final String PROJECT_SYNC_REQUEST ="sync_request" ;

    public static final String STATUS_DRAFT = "Draft";
    public static final String STATUS_IN_PROGRESS = "In Progress";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_CANCELLED = "Cancelled";

    public static final String STATUS_DRAFT_CODE = "3001";
    public static final String STATUS_IN_PROGRESS_CODE = "3002";
    public static final String STATUS_COMPLETED_CODE = "3003";
    public static final String STATUS_CANCELLED_CODE = "3004";

    public final static String JOB_STATUS_NON_STARTED="1";
    public final static String JOB_STATUS_STARTED="2";
    public final static String JOB_STATUS_COMPLETEDD="3";

    public static final String STATUS = "status";
    public static final String JOB_SYNC_REQUEST ="job_sync_request";
    public static final String PROJECT_ASYNC="project_async";
    public static final String PROJECTS = "projects";
    public static final String JOBS ="jobs" ;
    public static final String JOB ="job" ;
    public static final String SENSOR_JOB ="job" ;
    public static final String PROJECT = "project";


    public static final String SENSER_TYPE_THREE_RAD="Radiation";
    public static final String SENSER_TYPE_FIVE_GPS="GPS";
    public static final String SENSER_TYPE_SEVEN_NOTE="Notes";
    public static final String SENSER_TYPE_EIGHT_IMG="Photo";

    public static final String SENSER_TYPE_THREE_RAD_TITLE="RAD";
    public static final String SENSER_TYPE_FIVE_GPS_TITLE="GPS";
    public static final String SENSER_TYPE_SEVEN_NOTE_TITLE="NOTE";
    public static final String SENSER_TYPE_EIGHT_IMG_TITLE="IMAGE";
    public static final String DEFAULT_TITLE="Details";

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
