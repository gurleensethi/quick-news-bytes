package app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;

/**
 * Created by gurleensethi on 28/12/16.
 */

public class CustomizeNewsFeedPresenter implements CustomizeNewFeedContract.Presenter, DataManager.SourcesFetchListener {

    private CustomizeNewFeedContract.View mMainView;

    public CustomizeNewsFeedPresenter(CustomizeNewFeedContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {
        DataManager.getInstance().getAllStoredSources(mMainView.getContext(), this);
    }

    @Override
    public void refreshListOnSearch(String searchString) {
        DataManager.getInstance().getSourcesWithSearch(mMainView.getContext(), searchString, this);
    }

    @Override
    public void onSourcesFetched(List<SourceModel> sourcesList) {
        mMainView.displaySourcesList(sourcesList);
    }
}
