package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed.CustomizeNewsFeedActivity;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import app.com.thetechnocafe.quicknewsbytes.Utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeStreamActivityContract.View {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_drawer)
    LinearLayout mLinearLayout;
    @BindView(R.id.right_navigation_drawer)
    LinearLayout mRightNavigationDrawer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.new_feed_text_view)
    TextView mNewsFeedTextView;
    @BindView(R.id.right_recycler_view)
    RecyclerView mSourcesRecyclerView;
    @BindView(R.id.navigation_recycler_view)
    RecyclerView mLeftNavigationRecyclerView;
    @BindView(R.id.sources_search_edit_text)
    EditText mSearchEditText;

    private static final String TAG = HomeActivity.class.getSimpleName();
    private SourcesRecyclerAdapter mSourcesRecyclerAdapter;
    private LeftNavigationRecyclerAdapter mLeftNavigationRecyclerAdapter;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private HomeStreamActivityContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Bind Butterknife
        ButterKnife.bind(this);

        mPresenter = new HomeStreamActivityPresenter(this);

        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_drawer);

        //Make the content move to right on drawer swipe
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                float moveFactor = (mLinearLayout.getWidth() * slideOffset);

                //If right navigation drawer
                if (drawerView.getId() == mRightNavigationDrawer.getId()) {
                    moveFactor = -moveFactor;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    mCoordinatorLayout.setTranslationX(moveFactor);
                }
            }
        };

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        //Remove the shadow caused by navigation drawer on main content
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        mDrawerLayout.setDrawerElevation(0);

        //Configure recycler view
        mSourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        addSourceStreamFragment(Constants.HOME_STREAM);
        setUpOnClickListeners();
        setUpLeftNavigationRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    private void setUpOnClickListeners() {
        mNewsFeedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSourceStreamFragment(Constants.HOME_STREAM);

                //Change toolbar title
                mToolbar.setTitle(getString(R.string.news_feed));

                //Close drawer
                mDrawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Change the list of sources
                mPresenter.refreshListOnSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_stream, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_right_navigation: {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpOrRefreshRecyclerView(List<SourceModel> sourcesList) {
        if (mSourcesRecyclerAdapter == null) {
            //Create new Adapter
            mSourcesRecyclerAdapter = new SourcesRecyclerAdapter(getApplicationContext(), sourcesList, new SourcesRecyclerAdapter.SourcesEventListener() {
                @Override
                public void onSourceItemClicked(SourceModel item) {
                    //Replace fragment
                    addSourceStreamFragment(item.getID());

                    //Change toolbar title
                    mToolbar.setTitle(item.getName());

                    //Close the drawer
                    mDrawerLayout.closeDrawer(GravityCompat.END);

                    //Clear edit text and remove focus
                    mSearchEditText.setText("");
                    mSearchEditText.clearFocus();

                    //Hide the keyboard
                    hideKeyboard();
                }
            });

            mSourcesRecyclerView.setAdapter(mSourcesRecyclerAdapter);
        } else {
            mSourcesRecyclerAdapter.updateList(sourcesList);
            mSourcesRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void setUpLeftNavigationRecyclerView() {
        if (mLeftNavigationRecyclerAdapter == null) {
            //Get the list of options
            String[] options = getResources().getStringArray(R.array.left_navigation);

            mLeftNavigationRecyclerAdapter = new LeftNavigationRecyclerAdapter(getContext(), options, new LeftNavigationRecyclerAdapter.OnOptionItemSelectedListener() {
                @Override
                public void onOptionClicked(String string) {
                    switch (string) {
                        case "Customize News Feed": {
                            //Close left drawer
                            mDrawerLayout.closeDrawer(GravityCompat.START);

                            Intent intent = new Intent(HomeActivity.this, CustomizeNewsFeedActivity.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            break;
                        }

                    }
                }
            });

            mLeftNavigationRecyclerView.setAdapter(mLeftNavigationRecyclerAdapter);
            mLeftNavigationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onSourcesFetched(List<SourceModel> sourcesList) {
        setUpOrRefreshRecyclerView(sourcesList);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    //Add particular source stream
    private void addSourceStreamFragment(String sourceID) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, HomeStreamFragment.getInstance(sourceID)).commit();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
    }
}
