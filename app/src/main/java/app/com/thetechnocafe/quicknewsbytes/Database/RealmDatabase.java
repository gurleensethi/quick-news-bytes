package app.com.thetechnocafe.quicknewsbytes.Database;

import android.content.Context;

import app.com.thetechnocafe.quicknewsbytes.Models.ArticleModel;
import io.realm.Realm;
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
}
