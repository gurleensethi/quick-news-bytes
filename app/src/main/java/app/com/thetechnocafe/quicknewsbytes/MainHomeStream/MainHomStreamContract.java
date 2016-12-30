package app.com.thetechnocafe.quicknewsbytes.MainHomeStream;

import android.content.Context;

/**
 * Created by gurleensethi on 30/12/16.
 */

public class MainHomStreamContract {
    public interface View {
        Context getContext();
    }

    public interface Presenter {
        void onStart();

        void onDestroy();
    }
}
