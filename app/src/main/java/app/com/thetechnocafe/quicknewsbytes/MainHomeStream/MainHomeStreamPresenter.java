package app.com.thetechnocafe.quicknewsbytes.MainHomeStream;

/**
 * Created by gurleensethi on 30/12/16.
 */

public class MainHomeStreamPresenter implements MainHomStreamContract.Presenter {

    private MainHomStreamContract.View mMainView;

    public MainHomeStreamPresenter(MainHomStreamContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }
}
