package org.mobiletrain.android37_materialdesigndemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import org.mobiletrain.android37_materialdesigndemo.R;

/**
 * Created by Administrator on 2016-03-22.
 */
public class DetailWarActivity extends AppCompatActivity {

    private WebView webView;
    private String url = "http://if.app.miercn.com/article/view/@.html";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_war);

        Bundle bundle = getIntent().getExtras();
        final String id = (String) bundle.get("id");
        String commentNum = (String) bundle.get("commentNum");

        webView = ((WebView) findViewById(R.id.webView));
        tv = ((TextView) findViewById(R.id.tv_comment));
        tv.setText(commentNum+"条评论");

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnent = new Intent(DetailWarActivity.this,CommentActivity.class);
                intnent.putExtra("id",id);
                startActivity(intnent);
            }
        });


        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setLoadsImagesAutomatically(true);

        webView.loadUrl(url.replace("@", id));
    }

    public void finsh(View view) {
        finish();
    }
}
