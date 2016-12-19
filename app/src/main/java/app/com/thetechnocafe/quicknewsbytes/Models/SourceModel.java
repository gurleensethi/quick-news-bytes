package app.com.thetechnocafe.quicknewsbytes.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class SourceModel extends RealmObject {
    @Expose
    @SerializedName("id")
    @PrimaryKey
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
    @SerializedName("urlsToLogos")
    private UrlsToLogoModel mUrlsToLogos;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public UrlsToLogoModel getUrlsToLogos() {
        return mUrlsToLogos;
    }

    public void setUrlsToLogos(UrlsToLogoModel urlsToLogos) {
        mUrlsToLogos = urlsToLogos;
    }
}
