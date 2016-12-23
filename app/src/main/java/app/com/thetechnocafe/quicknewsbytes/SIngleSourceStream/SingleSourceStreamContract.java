package app.com.thetechnocafe.quicknewsbytes.SIngleSourceStream;

import android.content.Context;

/**
 * Created by gurleensethi on 23/12/16.
 */

public class SingleSourceStreamContract {
    public interface View {
        Context getViewContext();
    }

    public interface Presenter {
        void onStart();
    }
}
