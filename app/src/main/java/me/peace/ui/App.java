package me.peace.ui;

import android.app.Application;
import android.content.ComponentCallbacks2;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;

import java.io.File;

/**
 * @author: YSTEN
 * @date: 2020/8/18 23:10
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
            .setBaseDirectoryPath(new File(getFilesDir() + "/caches"))
            .setBaseDirectoryName("rsSystemPicCache").setMaxCacheSize(200 * ByteConstants.MB)
            .setMaxCacheSizeOnLowDiskSpace(100 * ByteConstants.MB)
            .setMaxCacheSizeOnVeryLowDiskSpace(50 * ByteConstants.MB)
            .setMaxCacheSize(80 * ByteConstants.MB).build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
            .setMainDiskCacheConfig(diskCacheConfig)
            .setDownsampleEnabled(true)
            .build();
        Fresco.initialize(this,config);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        try {
            if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) { // 60
                ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
