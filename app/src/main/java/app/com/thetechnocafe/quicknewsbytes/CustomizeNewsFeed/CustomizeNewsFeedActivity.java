package app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomizeNewsFeedActivity extends AppCompatActivity implements CustomizeNewFeedContract.View {

    @BindView(R.id.save_button)
    Button mSaveButton;
    @BindView(R.id.sources_recycler_view)
    RecyclerView mSourcesRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private CustomizeNewFeedContract.Presenter mPresenter;
    private SourcesRecyclerAdapter mSourcesRecyclerAdapter;
    private static int RECYCLER_VIEW_GRID_SIZE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_news_feed);

        ButterKnife.bind(this);

        mPresenter = new CustomizeNewsFeedPresenter(this);

        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void displaySourcesList(List<SourceModel> list) {
        setUpOrRefreshRecyclerView(list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpOrRefreshRecyclerView(List<SourceModel> list) {
        if (mSourcesRecyclerAdapter == null) {
            mSourcesRecyclerAdapter = new SourcesRecyclerAdapter(getContext(), list);
            mSourcesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), RECYCLER_VIEW_GRID_SIZE));
            mSourcesRecyclerView.setAdapter(mSourcesRecyclerAdapter);
        } else {
            mSourcesRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
