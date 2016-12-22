package app.com.thetechnocafe.quicknewsbytes.HomeStream;

/**
 * Created by gurleensethi on 22/12/16.
 */

public class HomeStreamActivityPresenter implements HomeStreamActivityContract.Presenter {

    private HomeStreamActivityContract.View mMainView;

    public HomeStreamActivityPresenter(HomeStreamActivityContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {

    }
}
