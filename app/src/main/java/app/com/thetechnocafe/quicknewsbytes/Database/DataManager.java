package app.com.thetechnocafe.quicknewsbytes.Database;

import android.content.Context;

import java.util.ArrayList;
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

        //Fetch latest news from network
        new NetworkRequests().fetchArticlesFromSingleSource(context, new NetworkRequests.SingleSourceFetchListener() {
            @Override
            public void onArticlesFetched(boolean isSuccessful, List<ArticleModel> articlesList) {
                //Check if activity has not been destroyed
                if(context != null) {
                    listener.onNewsFetched(isSuccessful, articlesList);
                }
            }
        }, sourceID);
    }

    //Send request to fetch all the sources that are saved
    public void fetchArticlesForNewsFeed(final Context context, final NewsFetchListener listener) {
        //Send offline list
        sendListOfArticles(context, listener);

        //Get all the saved sources
        List<SourceModel> mSourceList = RealmDatabase.getInstance(context).getSavedSources();

        //Iterate and get all sources
        /*for (SourceModel model : mSourceList) {
            new NetworkRequests().fetchNewsFromSource(context, new NetworkRequests.SourceNewsListener() {
                @Override
                public void onNewsFetched(boolean isSuccessful) {
                    //listener.onNewsFetched(isSuccessful, RealmDatabase.getInstance(context).getSavedArticles());
                }
            }, model.getID());
        }
        */
    }

    //Get all the saved sources
    public void getAllUserSavedSource(Context context, SourcesFetchListener listener) {
        listener.onSourcesFetched(RealmDatabase.getInstance(context).getSavedSources());
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
    public void getAllSources(final Context context, final SourcesFetchListener listener) {
        //Get all source from realm database
        List<SourceModel> sourcesList = RealmDatabase.getInstance(context).getAllSources();

        listener.onSourcesFetched(sourcesList);
    }

    //Get all the sources that match a substring
    public void getSourcesWithSearch(Context context, String searchWord, SourcesFetchListener listener) {
        String lowerCaseSearchWord = searchWord.toLowerCase();

        //Get list of sources
        List<SourceModel> sourcesList = RealmDatabase.getInstance(context).getAllSources();

        List<SourceModel> finalList = new ArrayList<>();

        //Filter out sources that match
        for (SourceModel sourceModel : sourcesList) {
            if (sourceModel.getName().toLowerCase().contains(lowerCaseSearchWord)) {
                finalList.add(sourceModel);
            }
        }

        listener.onSourcesFetched(finalList);
    }

    //Change the state of the source, if selected change to unselected and vice versa
    public void changeSourceSelection(Context context, SourceModel model) {
        RealmDatabase.getInstance(context).changeSourceSelection(model.getID(), !model.isSaved());
    }

    //Clear all the articles that belong to unselected sources
    public void deleteArticlesFromUnsavedSources(Context context) {
        RealmDatabase.getInstance(context).deleteUnrelatedArticlesFromSources();
    }

    //Send a list of articles with part of articles from every source
    private void sendListOfArticles(Context context, NewsFetchListener listener) {
        //Create list the will be sent
        List<ArticleModel> articlesList = new ArrayList<>();

        //Get all the saved sources
        List<SourceModel> mSourceList = RealmDatabase.getInstance(context).getSavedSources();

        //Extract two news from every source
        for (int count = 0; count < mSourceList.size(); count++) {
            List<ArticleModel> articlesFromSingleSource = RealmDatabase.getInstance(context).
                    getSavedArticlesFromSource(mSourceList.get(count).getID());

            if (articlesFromSingleSource.size() > 2) {
                articlesList.addAll(articlesFromSingleSource.subList(0, 2));
            }
        }

        listener.onOfflineNewsFetched(articlesList);
    }

}
