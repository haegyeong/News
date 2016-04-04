package org.mobiletrain.android37_materialdesigndemo.utils;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobiletrain.android37_materialdesigndemo.bean.ZhihuImageurl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/3/18.
 */
public class Constant<T> {

    /**
     * 线程池
     */
    private static ExecutorService service = Executors.newFixedThreadPool(5);
    private Handler handler = new Handler();

    /**
     * 知乎
     */
    private static String ZHIHUPATH = URLSet.URL_DESIGN;
    private static String PATH;

    /**
     * 回调接口
     */
    public interface BaseListener<T> {
        void Listener(T entity);

        void Listenerimageurl(List<ZhihuImageurl> list);
    }

    /***
     * 拿到知乎的数据，并解析
     *
     * @param id
     * @param obj
     * @param listener
     */
    public void getZhihuData(int id, final Class<T> obj, final BaseListener<T> listener) {
        final Gson gson = new Gson();
        final String path;
        if (id == 0) {
            path = ZHIHUPATH;
        } else {
            path = ZHIHUPATH + "/before/" + id;
        }
        service.execute(new Runnable() {
            @Override
            public void run() {
                String json = OkHttpUtils.loadStringFromURL(path);
                if (json != null) {
                    final T t = gson.fromJson(json, obj);
                    final List<ZhihuImageurl> list = new ArrayList<ZhihuImageurl>();
                    String imgaeUrl = null;

                    try {
                        JSONObject object = new JSONObject(json);
                        JSONArray jsonArray = object.getJSONArray("stories");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            try {
                                JSONArray imagesArr = jsonObject.getJSONArray("images");
                                imgaeUrl = imagesArr.getString(0);
                                //Log.e("info---------------", imgaeUrl);

                            } catch (Exception e) {
                               // imgaeUrl = null;
                            }
                            ZhihuImageurl zhihuImageurl = new ZhihuImageurl(imgaeUrl);
                            list.add(zhihuImageurl);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("info", json);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.Listener(t);
                            listener.Listenerimageurl(list);
                        }
                    });
                }
            }
        });

    }

    /**
     * 判断是否加载更多图片
     */
    public boolean flag;
    public void loadamore(boolean flag){
        this.flag = flag;
        Log.e("flag", String.valueOf(flag));

    }





    /**
     * 拿到图片的数据，并返回
     *
     * @param path
     * @param obj
     * @param listener
     */
    public T t;
    public void getImageData(final String path, final Class<T> obj, final BaseListener<T> listener) {
        final Gson gson = new Gson();

        service.execute(new Runnable() {
            @Override
            public void run() {
                String json = OkHttpUtils.loadStringFromURL(path);

                if (json != null) {
                        t  = gson.fromJson(json, obj);
                        Log.e("info", json);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.Listener(t);
                            }
                        });


                }
            }
        });

       /* // 一般情况下，如果程序退出了，需要销毁线程
        service.shutdown();
        service = null;*/
    }

    public interface HeadlineListener<T> {
        void headlineListener(T headlineentity);
    }
    public void getHeadlineData(final Class<T> obj,final HeadlineListener listener) {
        new Thread(new Runnable() {
            public Gson gson = new Gson();

            @Override
            public void run() {
                String json = OkHttpUtils.loadStringFromURL(URLSet.URL_HEADLINE);


                final T t = gson.fromJson(json, obj);
//                final List<HeadLineNews.HeadlineEntity> headlineEntity = headLineNews.getHeadlineEntity();
//                final List<HeadLineNews.HeadlineEntity.AdsEntity> ads = headlineEntity.get(0).getAds();


                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        listener.headlineListener(t);
//                        adapter = new HeadLineAdapter(headlineEntity,mContext);
//                        mListView.setAdapter(adapter);
//                        avp.setUrlList(ads);  //设置广告
                    }
                });

            }
        }).start();
    }


}
