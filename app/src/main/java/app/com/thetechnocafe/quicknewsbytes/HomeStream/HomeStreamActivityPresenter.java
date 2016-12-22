package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;

/**
 * Created by gurleensethi on 22/12/16.
 */

public class HomeStreamActivityPresenter implements HomeStreamActivityContract.Presenter, DataManager.SourcesFetchListener {

    private HomeStreamActivityContract.View mMainView;

    public HomeStreamActivityPresenter(HomeStreamActivityContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {
        DataManager.getInstance().getAllStoredSources(mMainView.getContext(), this);
    }

    @Override
    public void onSourcesFetched(List<SourceModel> sourcesList) {
        mMainView.onSourcesFetched(sourcesList);
    }
}