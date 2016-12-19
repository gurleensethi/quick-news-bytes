package app.com.thetechnocafe.quicknewsbytes.Database;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.Networking.NetworkRequests;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class DataManager {

    private static DataManager mInstance;
    private Context mContext;

    private DataManager(Context context) {
        mContext = context;
    }

    //Interface for callbacks
    public interface NewsFetchListener {
        void onNewsFetched(boolean isSuccessful, List<ArticleModel> articleList);

        Context getContext();
    }

    public static DataManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataManager(context);
        }
        return mInstance;
    }

    //Send request to fetch latest news
    public void fetchLatestNewsBySource(final NewsFetchListener listener) {
        new NetworkRequests().fetchNewsFromSource(mContext, new NetworkRequests.SourceNewsListener() {
            @Override
            public void onNewsFetched(boolean isSuccessful, List<ArticleModel> list) {
                listener.onNewsFetched(isSuccessful, list);
            }

            @Override
            public Context getContext() {
                return listener.getContext();
            }
        }, "techcrunch");
    }

    //Insert new Article in Realm
    public boolean insertNewArticle(ArticleModel model) {
        return RealmDatabase.getInstance(mContext).saveNewArticle(model);
    }
}
