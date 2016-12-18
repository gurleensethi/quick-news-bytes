package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class HomeStreamFragment extends Fragment implements HomeStreamContract.View {

    @BindView(R.id.news_feed_recycler_view)
    RecyclerView mNewsFeedRecyclerView;
    private HomeStreamContract.Presenter mPresenter;
    private ArticlesRecyclerAdapter mArticlesRecyclerAdapter;

    public static HomeStreamFragment getInstance() {
        return new HomeStreamFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_stream, container, false);

        //Bind Butterknife
        ButterKnife.bind(this, view);

        mPresenter = new HomeStreamPresenter(this);

        return view;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void displayNewsList(List<ArticleModel> list) {
        setUpOrRefreshRecyclerView(list);
    }

    private void setUpOrRefreshRecyclerView(List<ArticleModel> list) {
        if (mArticlesRecyclerAdapter == null) {
            mArticlesRecyclerAdapter = new ArticlesRecyclerAdapter(getContext(), list);
            mNewsFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mNewsFeedRecyclerView.setAdapter(mArticlesRecyclerAdapter);
        } else {
            mArticlesRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
