package app.com.thetechnocafe.quicknewsbytes.Networking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Database.DataManager;
import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.Utils.Constants;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class NetworkRequests {

    //Interfaces for Callbacks
    public interface SourceNewsListener {
        void onNewsFetched(boolean isSuccessful);
    }

    public interface SourcesFetcherListener {
        void onSourcesFetched(boolean isSuccessful);
    }

    public interface SingleSourceFetchListener {
        void onArticlesFetched(boolean isSuccessful, List<ArticleModel> articlesList);
    }

    /**
     * Fetch articles from a Single Source
     */
    public void fetchArticlesFromSingleSource(final Context context, final SingleSourceFetchListener listener, final String sourceId) {
        String completeURL = Constants.BASE_ARTICLES_URL + sourceId + Constants.NEWS_API_KEY;

        //Create JSON Request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, completeURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Check for status
                try {
                    if (response.getString(Constants.STATUS).equals(Constants.STATUS_OK)) {

                        //Get the articles array
                        JSONArray articlesArray = response.getJSONArray(Constants.ARTICLES);

                        List<ArticleModel> articlesList = new ArrayList<>();

                        //Iterate and convert using GSON
                        for (int count = 0; count < articlesArray.length(); count++) {
                            //Get model using GSON
                            Gson gson = new Gson();
                            ArticleModel model = gson.fromJson(articlesArray.getJSONObject(count).toString(), ArticleModel.class);

                            //Set the source id
                            model.setSourceId(sourceId);

                            //Add to list
                            articlesList.add(model);
                        }

                        listener.onArticlesFetched(true, articlesList);
                    } else {
                        listener.onArticlesFetched(false, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onArticlesFetched(false, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onArticlesFetched(false, null);
            }
        });

        //Add to request queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(request);
    }

    /**
     * Fetch news for a single source,
     * Store directly to Realm Database
     */
    public void fetchNewsFromSource(final Context context, final SourceNewsListener listener, final String sourceId, final boolean isLast) {
        String completeURL = Constants.BASE_ARTICLES_URL + sourceId + Constants.NEWS_API_KEY;

        //Create JSON Request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, completeURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Check for status
                try {
                    if (response.getString(Constants.STATUS).equals(Constants.STATUS_OK)) {

                        //Remove already existing articles with source
                        DataManager.getInstance().removeArticlesOfSource(context, sourceId);

                        //Get the articles array
                        JSONArray articlesArray = response.getJSONArray(Constants.ARTICLES);

                        List<ArticleModel> models = new ArrayList<>();

                        //Iterate and convert using GSON
                        for (int count = 0; count < articlesArray.length(); count++) {
                            //Get model using GSON
                            Gson gson = new Gson();
                            ArticleModel model = gson.fromJson(articlesArray.getJSONObject(count).toString(), ArticleModel.class);
                            models.add(model);

                            //Set the source id
                            model.setSourceId(sourceId);

                            //Insert to database
                            DataManager.getInstance().insertNewArticle(context, model);
                        }

                        if (isLast) {
                            listener.onNewsFetched(true);
                        }
                    } else {
                        if (isLast) {
                            listener.onNewsFetched(false);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (isLast) {
                        listener.onNewsFetched(false);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onNewsFetched(false);
            }
        });

        //Add to request queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(request);
    }

    /**
     * Fetch and store sources from NEWSAPI
     * store to realm database
     */
    public void fetchSources(final Context context, final SourcesFetcherListener listener) {
        String completeURL = Constants.BASE_SOURCE_URL;

        //Create JSON request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, completeURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Check for status
                try {
                    if (response.getString("status").equals(Constants.STATUS_OK)) {

                        //Get the sources array
                        JSONArray array = response.getJSONArray(Constants.SOURCES);

                        //Iterate and store in realm database
                        for (int count = 0; count < array.length(); count++) {
                            //Get model from GSON and store to realm
                            Gson gson = new Gson();
                            SourceModel source = gson.fromJson(array.getJSONObject(count).toString(), SourceModel.class);
                            DataManager.getInstance().insertNewSource(context, source);
                        }
                    }

                    listener.onSourcesFetched(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onSourcesFetched(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onSourcesFetched(false);
            }
        });

        //Add to volley queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(request);
    }
}
