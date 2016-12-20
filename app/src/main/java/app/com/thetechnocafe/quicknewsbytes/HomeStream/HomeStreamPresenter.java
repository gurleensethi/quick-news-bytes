package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class HomeStreamPresenter implements HomeStreamContract.Presenter, DataManager.NewsFetchListener {

    private HomeStreamContract.View mHomeStreamView;

    public HomeStreamPresenter(HomeStreamContract.View view) {
        mHomeStreamView = view;
    }

    @Override
    public void start() {
        refreshNews();
    }

    @Override
    public void refreshNews() {
        DataManager.getInstance(mHomeStreamView.getViewContext()).fetchLatestNewsBySource(this);
        mHomeStreamView.startRefreshing();
    }

    @Override
    public void onOfflineNewsFetched(List<ArticleModel> list) {
        mHomeStreamView.displayNewsList(list);
    }

    @Override
    public void onNewsFetched(boolean isSuccessful, List<ArticleModel> articleList) {
        if (isSuccessful) {
            mHomeStreamView.displayNewsList(articleList);
        }

        mHomeStreamView.stopRefreshing();
    }

    @Override
    public Context getContext() {
        return mHomeStreamView.getViewContext();
    }
}
