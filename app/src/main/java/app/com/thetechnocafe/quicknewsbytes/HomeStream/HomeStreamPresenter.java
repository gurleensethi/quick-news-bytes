package app.com.thetechnocafe.quicknewsbytes.HomeStream;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class HomeStreamPresenter implements HomeStreamContract.Presenter {

    private HomeStreamContract.View mHomeStreamView;

    public HomeStreamPresenter(HomeStreamContract.View view) {
        mHomeStreamView = view;
    }

    @Override
    public void start() {

    }
}
