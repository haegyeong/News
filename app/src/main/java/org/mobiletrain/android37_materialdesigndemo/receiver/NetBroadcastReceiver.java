package org.mobiletrain.android37_materialdesigndemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.mobiletrain.android37_materialdesigndemo.utils.NetStateUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25.
 */
public class NetBroadcastReceiver extends BroadcastReceiver{
    public static abstract interface OnNetListener {
        public abstract void onNetchanged(int i);
    }
    public static ArrayList<OnNetListener> listeners = new ArrayList<>();
    private  static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(NET_CHANGE_ACTION)){
            int networkType = NetStateUtil.checkNetworkType(context);
            if(listeners.size() > 0) {
                for (OnNetListener listener : listeners) {
                    listener.onNetchanged(networkType);   //将网络状态返回
                }
            }
        }
    }

}
