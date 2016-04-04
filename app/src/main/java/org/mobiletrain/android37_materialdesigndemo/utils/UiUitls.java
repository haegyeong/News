package org.mobiletrain.android37_materialdesigndemo.utils;

import android.content.Context;

import org.mobiletrain.android37_materialdesigndemo.app.NewsApplication;

/**
 * Created by Administrator on 2016-03-25.
 */
public class UiUitls {
    public static int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static Context getContext() {
        return NewsApplication.getInstance().getApplicationContext();
    }
}
