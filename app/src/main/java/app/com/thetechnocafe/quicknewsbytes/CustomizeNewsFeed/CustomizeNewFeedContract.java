package app.com.thetechnocafe.quicknewsbytes.CustomizeNewsFeed;

import android.content.Context;

/**
 * Created by gurleensethi on 28/12/16.
 */

public class CustomizeNewFeedContract {
    public interface View {
        Context getContext();
    }

    public interface Presenter {
        void onStart();
    }
}
