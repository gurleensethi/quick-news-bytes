package app.com.thetechnocafe.quicknewsbytes.MainHomeStream;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;

/**
 * Created by gurleensethi on 30/12/16.
 */

public class MainHomStreamContract {
    public interface View {
        Context getContext();

        void setUpViewPager(List<SourceModel> sourcesList);
    }

    public interface Presenter {
        void onStart();

        void onDestroy();
    }
}
