package app.com.thetechnocafe.quicknewsbytes.InitialSetUp;

import android.content.Context;

/**
 * Created by gurleensethi on 01/01/17.
 */

public class InitialSetUpContract {
    public interface View {
        Context getAppContext();
    }

    public interface Presenter {
        void onStart();
    }
}
