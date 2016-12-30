package app.com.thetechnocafe.quicknewsbytes.SourceNewsStream;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import app.com.thetechnocafe.quicknewsbytes.Utils.Constants;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class SourceNewsStreamPresenter implements SourceNewsStreamContract.Presenter, DataManager.NewsFetchListener {

    private SourceNewsStreamContract.View mHomeStreamView;

    public SourceNewsStreamPresenter(SourceNewsStreamContract.View view) {
        mHomeStreamView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void refreshNews(String sourceID) {
        if (sourceID.equals(Constants.MAIN_HOME_STREAM)) {
            DataManager.getInstance().fetchArticlesForNewsFeed(mHomeStreamView.getViewContext(), this);
        } else {
            DataManager.getInstance().fetchLatestNewsBySource(mHomeStreamView.getViewContext(), sourceID, this);
        }
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
        } else {
            mHomeStreamView.showSnackbarMessage(R.string.unable_to_load_source);
        }

        mHomeStreamView.stopRefreshing();
    }

    @Override
    public Context getContext() {
        return mHomeStreamView.getViewContext();
    }
}