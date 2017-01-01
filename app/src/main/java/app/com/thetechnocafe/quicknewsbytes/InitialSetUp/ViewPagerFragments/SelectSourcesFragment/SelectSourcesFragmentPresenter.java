package app.com.thetechnocafe.quicknewsbytes.InitialSetUp.ViewPagerFragments.SelectSourcesFragment;

import android.util.Log;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Database.RealmDatabase;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.R;
import app.com.thetechnocafe.quicknewsbytes.Utils.SharedPreferencesUtils;

/**
 * Created by gurleensethi on 01/01/17.
 */

public class SelectSourcesFragmentPresenter implements SelectSourcesFragmentContract.Presenter, DataManager.SourcesFetchListener {

    private static final String TAG = SelectSourcesFragmentPresenter.class.getSimpleName();
    private SelectSourcesFragmentContract.View mMainView;

    public SelectSourcesFragmentPresenter(SelectSourcesFragmentContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {
        DataManager.getInstance().fetchSourcesFromNetwork(mMainView.getAppContext(), this);
    }

    @Override
    public void refreshListOnSearch(String searchString) {
        DataManager.getInstance().getSourcesWithSearch(mMainView.getAppContext(), searchString, this);
    }

    @Override
    public void onSourceItemSelected(SourceModel source, String searchWord) {
        DataManager.getInstance().changeSourceSelection(mMainView.getAppContext(), source);
        DataManager.getInstance().getSourcesWithSearch(mMainView.getAppContext(), searchWord, this);
    }

    @Override
    public void retryRequest() {
        DataManager.getInstance().fetchSourcesFromNetwork(mMainView.getAppContext(), this);
    }

    @Override
    public void checkForSubmission() {
        int sourcesSaveCount = RealmDatabase.getInstance(mMainView.getAppContext()).getSavedSources().size();
        if (sourcesSaveCount < 3) {
            mMainView.displayToast(mMainView.getAppContext().getString(R.string.minimum_sources));
        } else {
            mMainView.startNewsFeed();
        }

        //Change first run to false
        SharedPreferencesUtils.getInstance(mMainView.getAppContext()).setFirstRunValue(false);
    }

    @Override
    public void onSourcesFetched(List<SourceModel> sourcesList) {
        if (sourcesList.size() == 0) {
            Log.e(TAG, "Error fetching sources.");
            mMainView.displayError();
        } else {
            Log.d(TAG, "Number of sources fetched : " + sourcesList.size());
            mMainView.displaySourcesList(sourcesList);
        }
    }
}
