package app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed;

/**
 * Created by gurleensethi on 28/12/16.
 */

public class CustomizeNewsFeedPresenter implements CustomizeNewFeedContract.Presenter {

    private CustomizeNewFeedContract.View mMainView;

    public CustomizeNewsFeedPresenter(CustomizeNewFeedContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {

    }
}
