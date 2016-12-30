package app.com.thetechnocafe.quicknewsbytes.HomeStream;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;

/**
 * Created by gurleensethi on 22/12/16.
 */

public class HomeStreamActivityContract {
    public interface View {
        void onSourcesFetched(List<SourceModel> sourcesList);

        Context getContext();

        void loadMainHomeStream();

        void loadSourceHomeStream(String sourceID);

        void setUpArticleViewPager(List<ArticleModel> articlesList);
    }

    public interface Presenter {
        void onStart();

        void refreshListOnSearch(String searchString);

        void loadFragmentInContainer(String sourceID);
    }
}
