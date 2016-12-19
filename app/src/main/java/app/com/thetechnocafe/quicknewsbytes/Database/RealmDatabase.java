package app.com.thetechnocafe.quicknewsbytes.Database;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import app.com.thetechnocafe.quicknewsbytes.Models.SourceModel;
import app.com.thetechnocafe.quicknewsbytes.Utils.Constants;
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
                //Create data formatter
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

                //Format dates
                try {
                    long dateModel = dateFormatter.parse(model.getPublishedAt()).getTime();
                    long dateT1 = dateFormatter.parse(t1.getPublishedAt()).getTime();

                    if (dateModel > dateT1) {
                        return 1;
                    } else if (dateModel < dateT1) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return 0;
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
            e.printStackTrace();
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
}

