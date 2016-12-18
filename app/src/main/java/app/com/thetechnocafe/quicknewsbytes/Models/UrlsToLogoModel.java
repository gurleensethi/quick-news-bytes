package app.com.thetechnocafe.quicknewsbytes.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class UrlsToLogoModel extends RealmObject {
    @Expose
    @SerializedName("small")
    private String mSmallImageUrl;

    @Expose
    @SerializedName("medium")
    private String mMediumImageUrl;

    @Expose
    @SerializedName("large")
    private String mLargeImageUrl;

    public String getLargeImageUrl() {
        return mLargeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        mLargeImageUrl = largeImageUrl;
    }

    public String getMediumImageUrl() {
        return mMediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        mMediumImageUrl = mediumImageUrl;
    }

    public String getSmallImageUrl() {
        return mSmallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        mSmallImageUrl = smallImageUrl;
    }
}
