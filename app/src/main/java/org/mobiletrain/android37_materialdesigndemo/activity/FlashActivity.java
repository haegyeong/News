package org.mobiletrain.android37_materialdesigndemo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.FlashAapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-03-24.
 */
public class FlashActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private int[] imgSrc = {R.drawable.a, R.drawable.b, R.drawable.c};
    private List<ImageView> imgs;
    private FlashAapter adapter;
    private SharedPreferences sp;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        viewPager = ((ViewPager) findViewById(R.id.viewPager));
        button = ((Button) findViewById(R.id.button));
        initData();

        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        if (!isFirst) {
            Intent intent = new Intent(FlashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        adapter = new FlashAapter(imgs);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imgSrc.length - 1) {
                    button.setVisibility(View.VISIBLE);
                }else{
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        imgs = new ArrayList<>();
        for (int i = 0; i < imgSrc.length; i++) {
            ImageView iv = new ImageView(this);
            Picasso.with(this).load(imgSrc[i]).into(iv);
//            iv.setBackgroundResource(imgSrc[i]);
            imgs.add(iv);
        }
    }

    public void enterMaintivity(View view) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isFirst", false).commit();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
