package app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomizeNewsFeedActivity extends AppCompatActivity implements CustomizeNewFeedContract.View {

    @BindView(R.id.save_button)
    FloatingActionButton mSaveButton;
    @BindView(R.id.sources_recycler_view)
    RecyclerView mSourcesRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sources_search_edit_text)
    EditText mSearchEditText;

    private CustomizeNewFeedContract.Presenter mPresenter;
    private SourcesRecyclerAdapter mSourcesRecyclerAdapter;
    private static int RECYCLER_VIEW_GRID_SIZE = 2;
    private int mLastSelectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_news_feed);

        ButterKnife.bind(this);

        mPresenter = new CustomizeNewsFeedPresenter(this);

        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setEventListeners();
    }

    private void setEventListeners() {
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.refreshListOnSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
            mSourcesRecyclerAdapter = new SourcesRecyclerAdapter(getContext(), list, new SourcesRecyclerAdapter.OnSourcesItemSelectedListener() {
                @Override
                public void onSourceItemSelected(int position, SourceModel model) {
                    mLastSelectedPosition = position;
                    mPresenter.onSourceItemSelected(model, mSearchEditText.getText().toString());

                    //If anything changes in source selection, notify for change to previous activity
                    setResult(Activity.RESULT_OK);
                }
            });
            mSourcesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), RECYCLER_VIEW_GRID_SIZE));
            mSourcesRecyclerView.setAdapter(mSourcesRecyclerAdapter);
        } else {
            mSourcesRecyclerAdapter.updateList(list);

            //Check if the item was clicked or list obtained from presenter
            if (mLastSelectedPosition >= 0) {
                mSourcesRecyclerAdapter.notifyItemChanged(mLastSelectedPosition);

                //Reset value back to -1 so that next time if the search is initiated
                //execution does't enters back into this branch because we would want to call notifyDataSetChanged()
                //due to change in the complete list
                mLastSelectedPosition = -1;
            } else {
                mSourcesRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }
}
