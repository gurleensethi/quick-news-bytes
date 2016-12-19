package app.com.thetechnocafe.quicknewsbytes.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gurleensethi on 19/12/16.
 */

public class DateFormattingUtils {
    private static DateFormattingUtils mInstance;

    //Singleton Class
    private DateFormattingUtils() {

    }

    //Get singleton instance
    public static DateFormattingUtils getInstance() {
        if (mInstance == null) {
            mInstance = new DateFormattingUtils();
        }
        return mInstance;
    }

    /**
     * Convert string to date
     */
    public Date convertToDate(String string) {
        //Create data formatter
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return dateFormatter.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    /**
     * Convert date to time ago
     */
    public String convertToTimeElapsedString(String stringDate) {
        //Get date from string
        Date date = convertToDate(stringDate);

        long timeDifference = new Date().getTime() - date.getTime();

        boolean isLargerThanADay = timeDifference >= Constants.MILLIS_IN_A_DAY;

        //If lager than a day then calculate days, else calculate the hours elapsed
        if (isLargerThanADay) {
            int numOfDays = (int) (timeDifference / Constants.MILLIS_IN_A_DAY);

            return numOfDays + " " + Constants.DAYS_AGO;
        } else {
            boolean isLargerThanAnHour = timeDifference >= Constants.MILLIS_IN_AN_HOUR;

            if (isLargerThanAnHour) {
                int numOfHours = (int) (timeDifference / Constants.MILLIS_IN_AN_HOUR);

                return numOfHours + " " + Constants.HOURS_AGO;
            } else {
                int numOfMinutes = (int) (timeDifference / Constants.MILLIS_IN_A_SECOND);
                return numOfMinutes + " " + Constants.MINUTES_AGO;
            }
        }
    }
}
