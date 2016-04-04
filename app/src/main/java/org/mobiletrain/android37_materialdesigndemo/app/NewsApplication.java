package org.mobiletrain.android37_materialdesigndemo.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2016/3/23.
 */
public class NewsApplication  extends Application{
    private  static NewsApplication app;
    public  static NewsApplication getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Fresco.initialize(this);
    }
}
