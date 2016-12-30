package app.com.thetechnocafe.quicknewsbytes.SourceNewsStream;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import app.com.thetechnocafe.quicknewsbytes.WebView.WebViewActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class SourceNewsStreamFragment extends Fragment implements SourceNewsStreamContract.View {

    @BindView(R.id.news_feed_recycler_view)
    RecyclerView mNewsFeedRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SourceNewsStreamContract.Presenter mPresenter;
    private ArticlesRecyclerAdapter mArticlesRecyclerAdapter;
    private static final String ARG_SOURCE_ID = "sourceid";

    public static Fragment getInstance(String sourceID) {
        //Create arguments
        Bundle args = new Bundle();
        args.putString(ARG_SOURCE_ID, sourceID);

        //Create fragment and set arguments
        Fragment fragment = new SourceNewsStreamFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mPresenter.refreshNews(getArguments().getString(ARG_SOURCE_ID));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_stream, container, false);

        //Bind Butterknife
        ButterKnife.bind(this, view);

        mPresenter = new SourceNewsStreamPresenter(this);

        //Add colors to swipe refresh layout
        mSwipeRefreshLayout.setColorSchemeColors(
                Color.RED,
                Color.BLUE,
                Color.CYAN,
                Color.GREEN,
                Color.BLACK
        );

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mNewsFeedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            mNewsFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        setUpEventListeners();

        return view;
    }

    private void setUpEventListeners() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshNews(getArguments().getString(ARG_SOURCE_ID));
            }
        });
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void displayNewsList(List<ArticleModel> list) {
        setUpOrRefreshRecyclerView(list);
    }

    @Override
    public void startRefreshing() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSnackbarMessage(int stringResource) {
        if (getView() != null) {
            Snackbar.make(getView(), stringResource, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setUpOrRefreshRecyclerView(List<ArticleModel> list) {
        if (mArticlesRecyclerAdapter == null) {
            mArticlesRecyclerAdapter = new ArticlesRecyclerAdapter(new ArticlesRecyclerAdapter.ArticleEventListener() {
                @Override
                public void onArticleClicked(ArticleModel item) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra(WebViewActivity.WEB_VIEW_PARCELABLE_EXTRA, item);
                    startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

                @Override
                public Context getContext() {
                    return SourceNewsStreamFragment.this.getContext();
                }
            }, list);
            mNewsFeedRecyclerView.setAdapter(mArticlesRecyclerAdapter);

            //Set visibility and make animation
            Animation bottomUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
            mNewsFeedRecyclerView.startAnimation(bottomUpAnimation);
            mNewsFeedRecyclerView.setVisibility(View.VISIBLE);
        } else {
            //Change with the updated list
            mArticlesRecyclerAdapter.updateList(list);
            mArticlesRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
