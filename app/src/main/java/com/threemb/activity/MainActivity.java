package com.threemb.activity;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.threemb.adapters.AdvertiseListingAdapter;
import com.threemb.app.AppConstants;
import com.threemb.async.GetAsync;
import com.threemb.callers.OnItemClickListener;
import com.threemb.callers.ServiceCaller;
import com.threemb.models.Advertise;
import com.threemb.models.Response;
import com.threemb.sachinsample.R;
import com.threemb.utils.CommonUtils;

public class MainActivity extends BaseActivity implements ServiceCaller,OnItemClickListener {

    private RecyclerView mRecyclerView;
    private AdvertiseListingAdapter adapter;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupUI();
        loadData();

    }

    private void setupUI() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Drawable drawable= ContextCompat.getDrawable(mContext, R.drawable.line_divider);
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(drawable));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadData() {
        GetAsync task = new GetAsync(this, this, false, AppConstants.GET_DATA, AppConstants.URL_GET_DATA);
        task.execute();
    }

    @Override
    public void OnAsyncCallResponse(Response response, String label) {
        switch (label) {
            case AppConstants.GET_DATA: {
                if (response!=null&&response.advertiseList!=null)
                {
                    adapter = new AdvertiseListingAdapter(mContext, response.advertiseList);
                    adapter.addItemClickListener(this);
                    mRecyclerView.setAdapter(adapter);
                }
            }
            break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Advertise advertise=adapter.getAdvertise(position);
        CommonUtils.showMessage(mContext,advertise.notificationTitle,advertise.notificationMessage, CommonUtils.MESSAGE.MESSAGE_BOX_WITH_OK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
