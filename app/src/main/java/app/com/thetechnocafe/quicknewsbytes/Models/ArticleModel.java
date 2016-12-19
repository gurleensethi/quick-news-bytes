package app.com.thetechnocafe.quicknewsbytes.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gurleensethi on 18/12/16.
 */

public class ArticleModel extends RealmObject {
    @Expose
    @SerializedName("author")
    private String mAuthorName;

    @Expose
    @PrimaryKey
    @SerializedName("title")
    private String mTitle;

    @Expose
    @SerializedName("description")
    private String mDescription;

    @Expose
    @SerializedName("url")
    private String mUrl;

    @Expose
    @SerializedName("urlToImage")
    private String mUrlToImage;

    @Expose
    @SerializedName("publishedAt")
    private String mPublishedAt;

    @Expose
    private String mSourceId;

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String authorName) {
        mAuthorName = authorName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    public String getSourceId() {
        return mSourceId;
    }

    public void setSourceId(String sourceId) {
        mSourceId = sourceId;
    }
}
