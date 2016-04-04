package org.mobiletrain.android37_materialdesigndemo.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Steven
 * on 16-1-7.
 */
public class OkHttpUtils {
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    static {
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * 作用：实现网络访问文件，将获取到数据储存在文件流中
     *
     * @param urlString ：访问网络的url地址
     * @return InputStream
     */
    public static InputStream loadStreamFromURL(String urlString) {
        Request request = new Request.Builder()
                .url(urlString)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().byteStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 作用：实现网络访问文件，将获取到的数据存在字节数组中
     *
     * @param urlString ：访问网络的url地址
     * @return byte[]
     */
    public static byte[] loadByteFromURL(String urlString) {
        Request request = new Request.Builder()
                .url(urlString)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().bytes();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 作用：实现网络访问文件，将获取到的数据存在字节数组中
     *
     * @param urlString ：访问网络的url地址
     * @return String
     */
    public static String loadStringFromURL(String urlString) {
        Request request = new Request.Builder()
                .url(urlString)
                .tag("")
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 作用：实现网络访问文件，先给服务器通过“POST”方式提交数据，再返回相应的数据
     *
     * @param urlString ：访问网络的url地址
     * @param map       ：访问url时，需要传递给服务器的参数。
     * @return byte[]
     */
    public static String doPostSubmit(String urlString, Map<String, String> map) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = map.get(key);
                builder.add(key, value);
            }
        }
        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(urlString)
                .post(formBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void loadDataFromUrl(String urlString, com.squareup.okhttp.Callback callback) {
        Request request = new Request.Builder()
                .url(urlString)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static Bitmap loadImage(String url){
        final Bitmap[] bm = {null};
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                if(response.isSuccessful()) {
                    InputStream is = response.body().byteStream();

                    bm[0] = BitmapFactory.decodeStream(is);

                }
            }
        });
        return bm[0];
    }
}