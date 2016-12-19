package app.com.thetechnocafe.quicknewsbytes.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.Utils.Constants;
import app.com.thetechnocafe.quicknewsbytes.Utils.DateFormattingUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by gurleensethi on 19/12/16.
 */

public class RealmDatabase {
    private static RealmDatabase mInstance;
    private Realm mRealm;

    private RealmDatabase(Context context) {
        //Initialize Realm
        Realm.init(context);

        mRealm = Realm.getDefaultInstance();
    }

    //Get the instance of database
    public static RealmDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RealmDatabase(context);
        }
        return mInstance;
    }

    /**
     * Save the new Article model object
     * Catch exception if the article already exists
     */
    public boolean saveNewArticle(final ArticleModel model) {
        try {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(model);
                }
            });
        } catch (RealmPrimaryKeyConstraintException e) {
            e.printStackTrace();
            //Return if already exists
            return false;
        }
        return true;
    }

    /**
     * Get a list of all saved articles
     */
    public List<ArticleModel> getSavedArticles() {
        List<ArticleModel> mArticlesList = new ArrayList<>();

        //Get the list from database
        mRealm.beginTransaction();
        RealmResults realmResults = mRealm.where(ArticleModel.class).findAll();

        mArticlesList.addAll(realmResults);

        Collections.sort(mArticlesList, new Comparator<ArticleModel>() {
            @Override
            public int compare(ArticleModel model, ArticleModel t1) {
                long dateModel = DateFormattingUtils.getInstance().convertToDate(model.getPublishedAt()).getTime();
                long dateT1 = DateFormattingUtils.getInstance().convertToDate(t1.getPublishedAt()).getTime();

                if (dateModel < dateT1) {
                    return 1;
                } else if (dateModel > dateT1) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        mRealm.commitTransaction();

        return mArticlesList;
    }

    /**
     * Store new Source model
     * Check for exception if already exists
     */
    public boolean saveNewSource(final SourceModel model) {
        try {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(model);
                }
            });
        } catch (RealmPrimaryKeyConstraintException e) {

            return false;
        }

        return true;
    }

    /**
     * Get Source Model from id
     */
    public SourceModel getSourceModel(String sourceId) {
        SourceModel source;

        mRealm.beginTransaction();
        source = mRealm.where(SourceModel.class).equalTo(Constants.REALM_SOURCE_ID, sourceId).findFirst();
        mRealm.commitTransaction();

        return source;
    }

    /**
     * Remove all the articles related to a particular sourceID
     */
    public void deleteArticlesFromSource(final String sourceID) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(ArticleModel.class).equalTo(Constants.REALM_ARTICLE_SOURCE_ID, sourceID).findAll().deleteAllFromRealm();
            }
        });
    }
}

