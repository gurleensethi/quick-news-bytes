package app.com.thetechnocafe.quicknewsbytes.SIngleSourceStream;

/**
 * Created by gurleensethi on 23/12/16.
 */

public class SingleSourceStreamPresenter implements SingleSourceStreamContract.Presenter {

    private SingleSourceStreamContract.View mMainView;

    public SingleSourceStreamPresenter(SingleSourceStreamContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {

    }
}
