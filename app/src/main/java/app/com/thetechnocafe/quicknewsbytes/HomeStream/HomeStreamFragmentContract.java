package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class HomeStreamFragmentContract {
    public interface Presenter {
        void start();

        void refreshNews();
    }

    public interface View {
        Context getViewContext();

        void displayNewsList(List<ArticleModel> list);

        void startRefreshing();

        void stopRefreshing();
    }
}
