package app.com.thetechnocafe.quicknewsbytes.InitialSetUp.ViewPagerFragments.SelectSourcesFragment;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;

/**
 * Created by gurleensethi on 01/01/17.
 */

public class SelectSourcesFragmentContract {
    public interface View {
        Context getAppContext();

        void displaySourcesList(List<SourceModel> list);

        void displayError();

        void startNewsFeed();

        void displayToast(String error);
    }

    public interface Presenter {
        void onStart();

        void refreshListOnSearch(String searchString);

        void onSourceItemSelected(SourceModel source, String searchWord);

        void retryRequest();

        void checkForSubmission();
    }
}
