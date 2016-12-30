package app.com.thetechnocafe.quicknewsbytes.MainHomeStream;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;

/**
 * Created by gurleensethi on 30/12/16.
 */

public class MainHomeStreamPresenter implements MainHomStreamContract.Presenter, DataManager.SourcesFetchListener {

    private MainHomStreamContract.View mMainView;

    public MainHomeStreamPresenter(MainHomStreamContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {
        DataManager.getInstance().getAllUserSavedSource(mMainView.getContext(), this);
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void onSourcesFetched(List<SourceModel> sourcesList) {
        mMainView.setUpViewPager(sourcesList);
    }
}
