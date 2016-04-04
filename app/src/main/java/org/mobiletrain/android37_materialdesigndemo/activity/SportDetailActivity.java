package org.mobiletrain.android37_materialdesigndemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import org.mobiletrain.android37_materialdesigndemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SportDetailActivity extends AppCompatActivity {
    @Bind(R.id.webView)WebView webView;
    @Bind(R.id.btn_comment)Button btn_comment;
    String comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_detail);

        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        comments = getIntent().getStringExtra("comments");

        webView.loadUrl(url);

        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setLoadsImagesAutomatically(true);

    }
    public void finsh(View view){
        finish();
    }
    public void clickButton(View view){
        Intent intent = new Intent(getApplicationContext(),SportCommentActivity.class);
        intent.putExtra("comments",comments);
        startActivity(intent);
    }


}
