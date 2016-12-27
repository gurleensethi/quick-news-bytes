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

    //Interface for News callbacks
    public interface NewsFetchListener {
        void onOfflineNewsFetched(List<ArticleModel> list);

        void onNewsFetched(boolean isSuccessful, List<ArticleModel> articleList);

        Context getContext();
    }

    //Interface for sources callbacks
    public interface SourcesFetchListener {
        void onSourcesFetched(List<SourceModel> sourcesList);
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    //Send request to fetch latest news
    public void fetchLatestNewsBySource(final Context context, String sourceID, final NewsFetchListener listener) {
        //TODO: Remove this from production code
        new NetworkRequests().fetchSources(context, null);

        //Fetch latest news from network
        new NetworkRequests().fetchArticlesFromSingleSource(context, new NetworkRequests.SingleSourceFetchListener() {
            @Override
            public void onArticlesFetched(boolean isSuccessful, List<ArticleModel> articlesList) {
                listener.onNewsFetched(isSuccessful, articlesList);
            }
        }, sourceID);
    }

    //Send request to fetch all the sources that are saved
    public void fetchArticlesForNewsFeed(final Context context, final NewsFetchListener listener) {
        //TODO: Remove this from production code
        new NetworkRequests().fetchSources(context, null);

        //Get all the saved sources
        List<SourceModel> mSourceList = RealmDatabase.getInstance(context).getSavedSources();

        //Iterate and get all sources
        for (SourceModel model : mSourceList) {
            new NetworkRequests().fetchNewsFromSource(context, new NetworkRequests.SourceNewsListener() {
                @Override
                public void onNewsFetched(boolean isSuccessful) {
                    listener.onNewsFetched(isSuccessful, RealmDatabase.getInstance(context).getSavedArticles());
                }
            }, model.getID());
        }
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

    //Fetch Offline Sources
    public void getAllStoredSources(final Context context, final SourcesFetchListener listener) {
        //Get all source from realm database
        List<SourceModel> sourcesList = RealmDatabase.getInstance(context).getAllSources();

        listener.onSourcesFetched(sourcesList);
    }
}
