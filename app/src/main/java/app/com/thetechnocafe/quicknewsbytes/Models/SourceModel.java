package app.com.thetechnocafe.quicknewsbytes.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class SourceModel extends RealmObject {
    @Expose
    @SerializedName("id")
    private String mID;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("description")
    private String mDescription;

    @Expose
    @SerializedName("url")
    private String mUrl;

    @Expose
    @SerializedName("category")
    private String mCategory;

    @Expose
    @SerializedName("language")
    private String mLanguage;

    @Expose
    @SerializedName("country")
    private String mCountry;

    @Expose
    @SerializedName("urlsToImage")
    private UrlsToLogoModel mUrlsToLogos;
}
