
package org.mobiletrain.android37_materialdesigndemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.MyPagerAdapter;
import org.mobiletrain.android37_materialdesigndemo.fragment.DummyFragment;
import org.mobiletrain.android37_materialdesigndemo.fragment.HeadLineFragment;
import org.mobiletrain.android37_materialdesigndemo.fragment.ImageFragment;
import org.mobiletrain.android37_materialdesigndemo.fragment.QiushiFragment;
import org.mobiletrain.android37_materialdesigndemo.fragment.SportFragment;
import org.mobiletrain.android37_materialdesigndemo.fragment.WarFragment;
import org.mobiletrain.android37_materialdesigndemo.fragment.ZhihuFragment;
import org.mobiletrain.android37_materialdesigndemo.receiver.NetBroadcastReceiver;
import org.mobiletrain.android37_materialdesigndemo.utils.NetStateUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NetBroadcastReceiver.OnNetListener {
    private static final String TAG = "MainActivity";
    private Context mContext = this;
    private DrawerLayout drawerLayout_main;
    private NavigationView navigationView_main;
    private ViewPager viewPager_main;
    private TabLayout tabLayout_main;
    private Toolbar toolbar_main;
    private List<Fragment> totalList = new ArrayList<Fragment>();
    private long exitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: " + NetStateUtil.checkNetworkType(this)
        );
        initView();

        initToolbar();

        initTabsAndViewPager();
    }

    private void initToolbar() {
        /*toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);

        //重新设置logo前方的图标
        toolbar_main.setNavigationIcon(R.mipmap.ic_menu);
        //toolbar_main.setLogo(R.mipmap.ic_launcher);
        toolbar_main.setTitle("MaterialDesign综合案例");*/


        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);
        //重新设置logo前方的图标
        toolbar_main.setNavigationIcon(R.mipmap.ic_menu);
        toolbar_main.setTitle("Title");

    }


    private void initTabsAndViewPager() {
        tabLayout_main = (TabLayout) findViewById(R.id.tabLayout_main);
        String[] arrTabTitles = getResources().getStringArray(R.array.arrTabTitles);
        viewPager_main = (ViewPager) findViewById(R.id.viewPager_main);

            WarFragment warfragment = new WarFragment();  //军事
            ZhihuFragment zhihufragment = new ZhihuFragment(mContext);//设计日报
            DummyFragment dummyfragment = new DummyFragment(mContext); //默认
            QiushiFragment qiushifragment = new QiushiFragment();
            HeadLineFragment headLineFragment = new HeadLineFragment(mContext);//头条
            SportFragment sportfragment = new SportFragment(); //体育
            ImageFragment imageFragment = new ImageFragment(mContext);//图片


            totalList.add(headLineFragment);
            totalList.add(sportfragment);
            totalList.add(warfragment);
            totalList.add(qiushifragment);
            totalList.add(zhihufragment);
            totalList.add(imageFragment);


        PagerAdapter adapter = new MyPagerAdapter(
                getSupportFragmentManager(), totalList, arrTabTitles);
        viewPager_main.setAdapter(adapter);

        tabLayout_main.setupWithViewPager(viewPager_main);
        tabLayout_main.setTabsFromPagerAdapter(adapter);
    }

    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.fab_main:
                Snackbar.make(view, "Snackbar comes out", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "Toast comes out", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout_main.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initView() {
        drawerLayout_main = (DrawerLayout) findViewById(R.id.drawerLayout_main);

        //初始化导航试图
        navigationView_main = (NavigationView) findViewById(R.id.navigationView_main);
        navigationView_main.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // item.setChecked(true);
                        switch (item.getItemId()) {
                            case R.id.nav_setting:

                                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_hot:
                                Toast.makeText(MainActivity.this, "热门", Toast.LENGTH_SHORT).show();

                                break;
                            case R.id.nav_messages:
                                Toast.makeText(MainActivity.this, "反馈意见", Toast.LENGTH_SHORT).show();

                                break;
                            case R.id.nav_provite:
                                Toast.makeText(MainActivity.this, "素材", Toast.LENGTH_SHORT).show();

                                break;
                        }

                        drawerLayout_main.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout_main.isDrawerOpen(GravityCompat.START)) {
            drawerLayout_main.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }


    @Override
    public void onNetchanged(int i) {
        Log.d(TAG, "onNetchanged: NET TYPE  = " + i);
        switch (i) {
            case NetStateUtil.TYPE_WIFI:
                Toast.makeText(MainActivity.this, "正在使用WiFi", Toast.LENGTH_SHORT).show();
                break;
            case NetStateUtil.TYPE_NET_WORK_DISABLED:
                Toast.makeText(MainActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.this, "正在使用流量", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

