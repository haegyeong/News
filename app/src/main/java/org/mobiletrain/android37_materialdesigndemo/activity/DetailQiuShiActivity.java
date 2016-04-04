package org.mobiletrain.android37_materialdesigndemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.QiuShiCommentAdapter;
import org.mobiletrain.android37_materialdesigndemo.bean.QiuShiDetail;
import org.mobiletrain.android37_materialdesigndemo.bean.QiushiComment;
import org.mobiletrain.android37_materialdesigndemo.utils.OkHttpUtils;
import org.mobiletrain.android37_materialdesigndemo.utils.ThreadManager;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;

import java.util.List;

/**
 * Created by Administrator on 2016-03-23.
 */
public class DetailQiuShiActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailQiuShiActivity";
    private ImageView iv_heard, iv_up, iv_down, iv_comment, iv_share;
    private TextView tv_username, tv_comtent, tv_up, tv_comment, tv_share;
    private ListView listView;
    private Context context = this;
    private Gson gson;
    private QiuShiDetail qiuShiDetail;
    private QiushiComment qiushiComment;
    private QiuShiCommentAdapter adapter;
    private QiuShiDetail.ArticleEntity articleEntity;
    private int page = 1;
    private int id;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    qiushiComment = (QiushiComment) msg.obj;
                    Bundle bundle = msg.getData();
                    qiuShiDetail = (QiuShiDetail) bundle.getSerializable("detail");
                    Log.e(TAG, "handleMessage: " + qiuShiDetail.getArticle().getContent());
                    articleEntity = qiuShiDetail.getArticle();
                    List<QiushiComment.ItemsEntity> items = qiushiComment.getItems();
                    adapter = new QiuShiCommentAdapter(context, items);
                    if (articleEntity.getUser() == null) {
                        tv_username.setText("匿名用户");
                    } else {
                        tv_username.setText(articleEntity.getUser().getLogin());
                    }
                    tv_comtent.setText(articleEntity.getContent());
                    tv_up.setText("好笑 " + articleEntity.getVotes().getUp());
                    tv_comment.setText("评论 " + articleEntity.getComments_count());
                    if (articleEntity.getShare_count() == 0) {
                        tv_share.setVisibility(View.GONE);
                    } else {
                        tv_share.setVisibility(View.VISIBLE);
                        tv_share.setText("分享 " + articleEntity.getShare_count());
                    }
                    listView.setAdapter(adapter);
                    break;
                case 2:
                    adapter.addData(qiushiComment.getItems());
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private boolean isBotton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_qiushi);
        listView = ((ListView) this.findViewById(R.id.listview));

        View headView = getHeadView();
        listView.addHeaderView(headView);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE & isBotton) {
                    downloadComment(id, ++page);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    isBotton = true;
                } else {
                    isBotton = false;
                }
            }
        });
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        gson = new Gson();

        downloadComment(id, page);
    }

    private View getHeadView() {
        View view = LayoutInflater.from(context).inflate(R.layout.qiushi_head, null);
        iv_heard = (ImageView) view.findViewById(R.id.iv_heard);
        iv_up = (ImageView) view.findViewById(R.id.iv_up);
        iv_down = (ImageView) view.findViewById(R.id.iv_down);
        iv_comment = (ImageView) view.findViewById(R.id.iv_comment);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        tv_comtent = (TextView) view.findViewById(R.id.tv_qcomtent);
        tv_up = (TextView) view.findViewById(R.id.tv_qup);
        tv_comment = (TextView) view.findViewById(R.id.tv_qcomment);
        tv_share = (TextView) view.findViewById(R.id.tv_qshare);
        iv_up.setOnClickListener(this);
        iv_down.setOnClickListener(this);
        iv_comment.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        return view;
    }

    private void downloadComment(final int id, final int page) {
        Log.e(TAG, "downloadComment: " + id + "::" + page);
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                String json = OkHttpUtils.loadStringFromURL(URLSet.URL_LATEST_COMMTENT.replaceAll("@p", page + "").replaceAll("@", id + ""));
                Log.e(TAG, "downloadComment: " + URLSet.URL_LATEST_COMMTENT.replaceAll("@p", page + "").replaceAll("@", id + ""));
                qiushiComment = gson.fromJson(json, QiushiComment.class);
                String detail = OkHttpUtils.loadStringFromURL(URLSet.URL_LATEST_DETAIL.replaceAll("@", id + ""));
                Log.e(TAG, "detail: " + URLSet.URL_LATEST_DETAIL.replaceAll("@", id + ""));
                QiuShiDetail qiuShiDetail = gson.fromJson(detail, QiuShiDetail.class);
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail", qiuShiDetail);
                message.setData(bundle);
                if (page == 1) {
                    message.what = 1;
                } else {
                    message.what = 2;
                }
                message.obj = qiushiComment;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_up:
                Toast.makeText(context, "好笑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_down:
                Toast.makeText(context, "不好笑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_comment:
                Toast.makeText(context, "评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_share:
                Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
