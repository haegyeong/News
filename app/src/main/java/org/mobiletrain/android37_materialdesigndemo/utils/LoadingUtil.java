package org.mobiletrain.android37_materialdesigndemo.utils;

import android.content.Context;
import android.view.View;

import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by Administrator on 2016/3/25.
 */
public class LoadingUtil {
    private static DynamicBox box;

    public static void createBox(Context context,View v ,String loadingMsg) {
         box = new DynamicBox(context, v);
        box.showLoadingLayout();
        box.setLoadingMessage(loadingMsg);
    }


    public static void hideBox(){
        box.hideAll();
    }
    /**
     *
     * @param context
     * @param viewlayoutid 需要加载的控件布局Layout
     * @param loadingMsg    自定义加载信息
     */
    public static void createBox(Context context,int viewlayoutid ,String loadingMsg) {
        DynamicBox box = new DynamicBox(context, viewlayoutid);
        box.showLoadingLayout();
        box.setLoadingMessage(loadingMsg);
    }
}
