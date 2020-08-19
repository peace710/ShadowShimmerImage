package me.peace.ui;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author: YSTEN
 * @date: 2020/8/18 23:10
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
