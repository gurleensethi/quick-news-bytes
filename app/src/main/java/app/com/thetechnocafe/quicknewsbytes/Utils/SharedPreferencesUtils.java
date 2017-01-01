package app.com.thetechnocafe.quicknewsbytes.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gurleensethi on 19/12/16.
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils mInstance;
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    private static final String SP_FILE_NAME = "sharedpreferences";
    private static final String SP_FIRST_RUN = "firstrun";

    /**
     * Singleton class with private constructor and
     * instance method
     */
    private SharedPreferencesUtils(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesUtils(context);
        }
        return mInstance;
    }

    /**
     * Change the value of first run (boolean)
     */
    public boolean getFirstRunValue() {
        return mSharedPreferences.getBoolean(SP_FIRST_RUN, true);
    }

    public void setFirstRunValue(boolean value) {
        //Get shared preferences editor and change value
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(SP_FIRST_RUN, value);
        editor.commit();
    }
}
