package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.Utils.Constants;

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
        DataManager.getInstance().getAllSources(mMainView.getContext(), this);
    }

    @Override
    public void refreshListOnSearch(String searchString) {
        DataManager.getInstance().getSourcesWithSearch(mMainView.getContext(), searchString, this);
    }

    @Override
    public void loadFragmentInContainer(String sourceID) {
        //Check which fragment to load (MainHomeStream or Fragment for a single source)
        if (sourceID.equals(Constants.MAIN_HOME_STREAM)) {
            mMainView.loadMainHomeStream();
        } else {
            mMainView.loadSourceHomeStream(sourceID);
        }
    }

    @Override
    public void onSourcesFetched(List<SourceModel> sourcesList) {
        mMainView.onSourcesFetched(sourcesList);
    }
}
