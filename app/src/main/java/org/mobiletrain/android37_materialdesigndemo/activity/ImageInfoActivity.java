package org.mobiletrain.android37_materialdesigndemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ImageInfoActivity extends AppCompatActivity {

    private WebView webView;
    private String full_url;
    private String body = null;
    private String js1 = null;
    private String js2 = null;
    private List<String> list = new ArrayList<>();
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageinfo);
        full_url = getIntent().getExtras().get("url").toString();
        webView = ((WebView) this.findViewById(R.id.image_webview));
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = OkHttpUtils.loadStringFromURL(full_url);
                try {
                    if (json!=null){

                        JSONObject object = new JSONObject(json);
                        JSONObject dataobj = object.getJSONObject("data");
                        body = dataobj.getString("content");
                        JSONArray js_ref = dataobj.getJSONArray("js_ref");
                        js1 = js_ref.getString(0);
                        js2 = js_ref.getString(1);

                        JSONArray media = dataobj.getJSONArray("media");
                        for (int i = 0; i < media.length(); i++) {
                            JSONObject jsonObject = media.getJSONObject(i);
                            String raw_url = jsonObject.getString("raw_url");
                            list.add(raw_url);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String html = getString();
                                webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

                            }
                        });
                    }

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
                " <script src=\"js1\"></script>" +
                "\n" +
                " <script src=\"js2\"></script>" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "bodymin\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        String head2 = head.replace("js1", js1);
        String head3 = head2.replace("js2", js2);
        String head4 = head2.replace("bodymin", body);
        String head5 = null;

        for (int i = 0; i < list.size(); i++) {
            head4 = head4.replaceFirst("article_html_content_loading.png", list.get(i));

        }

        return head4;
    }

    public void finsh(View view) {
        finish();
    }
}


