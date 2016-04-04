package org.mobiletrain.android37_materialdesigndemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.WarCommentAdatapter;
import org.mobiletrain.android37_materialdesigndemo.bean.WarComment;
import org.mobiletrain.android37_materialdesigndemo.utils.OkHttpUtils;
import org.mobiletrain.android37_materialdesigndemo.utils.ThreadManager;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;

import java.util.List;

/**
 * Created by Administrator on 2016-03-22.
 */
public class CommentActivity extends AppCompatActivity {

    private ListView listView;
    private WarCommentAdatapter adatapter;
    private WarComment warComment;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    warComment = (WarComment) msg.obj;
                    List<WarComment.CommentListEntity> commentList = warComment.getCommentList();
                    adatapter = new WarCommentAdatapter(CommentActivity.this, commentList);

                    listView.setAdapter(adatapter);
                    break;
                case 1:
                    adatapter.addData(warComment.getCommentList());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private boolean isBottom;
    private String id;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_comment);
        listView = ((ListView) findViewById(R.id.lv_comment));

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE & isBottom) {
                    downLoadData(id, ++page);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    isBottom = true;
                } else {
                    isBottom = false;
                }
            }
        });

        id = getIntent().getStringExtra("id");

        downLoadData(id, page);

    }

    private void downLoadData(final String id, final int page) {
        Log.e("main", "downLoadData: " + id + "::" + page);
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String json = OkHttpUtils.loadStringFromURL(URLSet.URL_JUNSHICOMMENT.replaceAll("@p", page + "").replaceAll("@", id));
                Log.e("main", "run: " + URLSet.URL_JUNSHICOMMENT.replaceAll("@", id).replaceAll("@p", page + ""));
                Gson gson = new Gson();
                warComment = gson.fromJson(json, WarComment.class);
                Message message = Message.obtain();
                if (page == 1) {

                    message.what = 0;
                } else {
                    message.what = 1;

                }
                message.obj = warComment;
                handler.sendMessage(message);
            }
        });
    }
}
