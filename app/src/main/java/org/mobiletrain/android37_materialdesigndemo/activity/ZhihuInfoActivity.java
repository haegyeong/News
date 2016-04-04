package org.mobiletrain.android37_materialdesigndemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.utils.OkHttpUtils;
import org.mobiletrain.android37_materialdesigndemo.utils.URLSet;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ZhihuInfoActivity extends AppCompatActivity {

    private WebView webView;
    private String id;
    private String body=null;
    private String cssurl=null;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihuinfo);
        id = getIntent().getExtras().get("id").toString();
        webView = ((WebView) this.findViewById(R.id.zhihu_webview));
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = OkHttpUtils.loadStringFromURL(URLSet.URL_DESIGNINFO + id);
                try {
                    JSONObject object = new JSONObject(json);
                    body = object.getString("body");
                    cssurl = object.getJSONArray("css").getString(0);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String html = getString();
                            webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private String getString() {

        String head = "\n" +
                "<html>\n" +
                "<head>\n" +
                "      <link rel=\"stylesheet\" type=\"text/css\" href=\"csshrfe\">\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "bodymin\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        String head2 = head.replace("csshrfe", cssurl);
        String head3 = head2.replace("bodymin", body);
        Log.e("head3====", head3);
        return head3;
    }

    public void finsh(View view) {
        finish();
    }
}


