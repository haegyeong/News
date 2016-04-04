package org.mobiletrain.android37_materialdesigndemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.mobiletrain.android37_materialdesigndemo.R;

public class HeadlineNormalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline_normal_detail);
       WebView web = ((WebView) findViewById(R.id.headline_normal_detail_web));
                web.loadUrl(getIntent().getStringExtra("url"));
        //支持javascript
        web.getSettings().setJavaScriptEnabled(true);
    // 设置可以支持缩放
        web.getSettings().setSupportZoom(true);
    // 设置出现缩放工具
        web.getSettings().setBuiltInZoomControls(true);
    //扩大比例的缩放
        web.getSettings().setUseWideViewPort(true);

        //适应内容大小
        web.getSettings() .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

    //自适应屏幕
//        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.getSettings().setLoadWithOverviewMode(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
