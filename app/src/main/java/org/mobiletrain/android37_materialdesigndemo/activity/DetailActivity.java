package org.mobiletrain.android37_materialdesigndemo.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.DetailRecyclerAdapter;

public class DetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout_detail;
    private RecyclerView recyclerView_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
    }

    private void initView() {
        collapsingToolbarLayout_detail =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout_detail);
        collapsingToolbarLayout_detail.setTitle("我的课程");

        Toolbar toolbar_detail = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar_detail);
        toolbar_detail.setNavigationIcon(R.mipmap.ic_menu);

        recyclerView_detail = (RecyclerView) findViewById(R.id.recyclerView_detail);
        recyclerView_detail.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        recyclerView_detail.setAdapter(new DetailRecyclerAdapter(this));
    }

    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.fab_detail:
                Snackbar.make(view, "您点击了Snackbar!", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
