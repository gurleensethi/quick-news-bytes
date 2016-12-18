package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class HomeStreamContract {
    public interface Presenter {
        void start();
    }

    public interface View {
        Context getViewContext();

        void displayNewsList(List<ArticleModel> list);
    }
}
