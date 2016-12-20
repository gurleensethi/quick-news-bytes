package app.com.thetechnocafe.quicknewsbytes.Database;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.Networking.NetworkRequests;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class DataManager {

    private static DataManager mInstance;

    private DataManager() {

    }

    //Interface for callbacks
    public interface NewsFetchListener {
        void onOfflineNewsFetched(List<ArticleModel> list);

        void onNewsFetched(boolean isSuccessful, List<ArticleModel> articleList);

        Context getContext();
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    //Send request to fetch latest news
    public void fetchLatestNewsBySource(final Context context, final NewsFetchListener listener) {
        //TODO: Remove this from production code
        new NetworkRequests().fetchSources(context, null);

        //Initially send news that is stored in Realm
        listener.onOfflineNewsFetched(RealmDatabase.getInstance(context).getSavedArticles());

        //Fetch latest news from network
        new NetworkRequests().fetchNewsFromSource(context, new NetworkRequests.SourceNewsListener() {
            @Override
            public void onNewsFetched(boolean isSuccessful) {
                listener.onNewsFetched(isSuccessful, RealmDatabase.getInstance(context).getSavedArticles());
            }

            @Override
            public Context getContext() {
                return listener.getContext();
            }
        }, "the-verge");
    }

    //Insert new Article in Realm
    public boolean insertNewArticle(Context context, ArticleModel model) {
        return RealmDatabase.getInstance(context).saveNewArticle(model);
    }

    //Insert new Source in Realm
    public boolean insertNewSource(Context context, SourceModel model) {
        return RealmDatabase.getInstance(context).saveNewSource(model);
    }

    //Get source model from id
    public SourceModel getSourceFromId(Context context, String sourceId) {
        return RealmDatabase.getInstance(context).getSourceModel(sourceId);
    }

    //Remove all articles related to a single source
    public void removeArticlesOfSource(Context context, String source) {
        RealmDatabase.getInstance(context).deleteArticlesFromSource(source);
    }
}
