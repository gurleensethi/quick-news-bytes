package app.com.thetechnocafe.quicknewsbytes.InitialSetUp;

import app.com.thetechnocafe.quicknewsbytes.Utils.SharedPreferencesUtils;

/**
 * Created by gurleensethi on 01/01/17.
 */

public class InitialSetUpPresenter implements InitialSetUpContract.Presenter {

    private InitialSetUpContract.View mMainView;

    public InitialSetUpPresenter(InitialSetUpContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {
        //Check if is first run of the app, if not then go to the news feed activity
        if (SharedPreferencesUtils.getInstance(mMainView.getAppContext()).getFirstRunValue()) {
            mMainView.setUpViewPager();
        } else {
            mMainView.startNewsFeed();
        }
    }
}
