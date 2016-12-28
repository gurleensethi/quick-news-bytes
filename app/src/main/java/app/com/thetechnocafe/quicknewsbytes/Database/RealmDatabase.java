package app.com.thetechnocafe.quicknewsbytes.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * Get list of all stored sources
     */
    public List<SourceModel> getAllSources() {
        final List<SourceModel> mSourcesList = new ArrayList<>();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mSourcesList.addAll(
                        realm.where(SourceModel.class)
                                .findAll());
            }
        });

        return mSourcesList;
    }

    /**
     * Get all the sources that have been saved,
     * to show up in the home stream
     */
    public List<SourceModel> getSavedSources() {
        final List<SourceModel> mSourcesList = new ArrayList<>();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mSourcesList.addAll(
                        realm.where(SourceModel.class)
                                .equalTo(Constants.REALM_SOURCE_SAVED, true)
                                .findAll()
                );
            }
        });

        return mSourcesList;
    }

    /**
     * Change the selection state of a source,
     * whether to show in news feed or not
     */
    public void changeSourceSelection(final String sourceID, final boolean shouldSave) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SourceModel source = realm.where(SourceModel.class).equalTo(Constants.REALM_SOURCE_ID, sourceID).findFirst();
                source.setSaved(shouldSave);
            }
        });
    }

    /**
     * Delete all the articles related to sources that are not saved
     */
    public void deleteUnrelatedArticlesFromSources() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //Get all saved sources
                List<SourceModel> sourcesList = realm.where(SourceModel.class).equalTo(Constants.REALM_SOURCE_SAVED, true).findAll();

                //Put all the sourceIDs in a set
                Set<String> sourceIDs = new HashSet<String>();
                for (SourceModel model : sourcesList) {
                    sourceIDs.add(model.getID());
                }

                //Get list of all saved articles
                List<ArticleModel> articlesList = realm.where(ArticleModel.class).findAll();

                //Remove all the articles whose sourceID is not in the set
                for (ArticleModel model : articlesList) {
                    if (!sourceIDs.contains(model.getSourceId())) {
                        model.deleteFromRealm();
                    }
                }
            }
        });
    }
}

