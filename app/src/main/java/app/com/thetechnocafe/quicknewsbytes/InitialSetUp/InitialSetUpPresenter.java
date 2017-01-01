package app.com.thetechnocafe.quicknewsbytes.InitialSetUp;

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
        mMainView.setUpViewPager();
    }
}
