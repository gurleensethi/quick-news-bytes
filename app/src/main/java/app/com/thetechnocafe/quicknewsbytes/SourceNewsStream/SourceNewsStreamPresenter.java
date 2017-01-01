package app.com.thetechnocafe.quicknewsbytes.SourceNewsStream;

import android.content.Context;
import android.util.Log;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.R;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class SourceNewsStreamPresenter implements SourceNewsStreamContract.Presenter, DataManager.NewsFetchListener {

    private static final String TAG = SourceNewsStreamPresenter.class.getSimpleName();
    private SourceNewsStreamContract.View mHomeStreamView;

    public SourceNewsStreamPresenter(SourceNewsStreamContract.View view) {
        mHomeStreamView = view;
    }

    @Override
    public void start(boolean isInstanceCreated) {

    }

    @Override
    public void refreshNews(String sourceID) {
        DataManager.getInstance().fetchLatestNewsBySource(mHomeStreamView.getViewContext(), sourceID, this);
        mHomeStreamView.startRefreshing();
    }

    @Override
    public void onOfflineNewsFetched(List<ArticleModel> list) {
        mHomeStreamView.displayNewsList(list);
    }

    @Override
    public void onNewsFetched(boolean isSuccessful, List<ArticleModel> articleList) {
        //Check if context is null
        if (mHomeStreamView.getViewContext() == null) {
            Log.d(TAG, "Context was null, cancelling operation.");
            return;
        }

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
