package app.com.thetechnocafe.quicknewsbytes.Networking;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.Utils.Constants;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class NetworkRequests {

    //Interfaces for Callbacks
    public interface SourceNewsListener {
        void onNewsFetched(boolean isSuccessful);

        Context getContext();
    }

    /**
     * Fetch news for a single source,
     * Store directly to Realm Database
     */
    public void fetchNewsFromSource(final SourceNewsListener listener, String sourceId) {
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

                        //Iterate and convert using GSON
                        for (int count = 0; count < articlesArray.length(); count++) {
                            Gson gson = new Gson();
                            ArticleModel model = gson.fromJson(articlesArray.getJSONObject(count).toString(), ArticleModel.class);
                            Toast.makeText(listener.getContext(), model.getTitle(), Toast.LENGTH_SHORT).show();
                        }

                        listener.onNewsFetched(true);
                    } else {
                        listener.onNewsFetched(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onNewsFetched(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onNewsFetched(false);
            }
        });

        //Add to request queue
        VolleyRequestQueue.getInstance(listener.getContext()).getRequestQueue().add(request);
    }
}
