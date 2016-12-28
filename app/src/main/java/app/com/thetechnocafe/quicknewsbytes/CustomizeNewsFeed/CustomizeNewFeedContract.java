package app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;

/**
 * Created by gurleensethi on 28/12/16.
 */

public class CustomizeNewFeedContract {
    public interface View {
        Context getContext();

        void displaySourcesList(List<SourceModel> list);
    }

    public interface Presenter {
        void onStart();
    }
}
