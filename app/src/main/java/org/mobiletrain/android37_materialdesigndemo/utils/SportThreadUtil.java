package org.mobiletrain.android37_materialdesigndemo.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SportThreadUtil {
    public static void showToast(final Context context, Handler handler , final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
               // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void adapterNotifyDataSetChanged(Handler handler, final BaseAdapter adapter){
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
