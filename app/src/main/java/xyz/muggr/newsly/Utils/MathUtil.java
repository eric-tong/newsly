package xyz.muggr.newsly.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MathUtil {

    private static final String[] numNames = {
            "",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen"
    };

    public static double pythagoras(float a, float b) {
        return Math.sqrt(a * a + b * b);
    }

    public static String getHumanTime(long pastTime) {

        Calendar currentCalendar = Calendar.getInstance();
        Calendar pastCalendar = Calendar.getInstance();
        pastCalendar.setTimeInMillis(pastTime + TimeZone.getDefault().getRawOffset());

        long timeDifference = new Date().getTime() - pastTime;

        // Less than 1 hour
        if (timeDifference < TimeUnit.HOURS.toMillis(1))
            return "just now";

        // Between 1 and 2 hours
        if (timeDifference < TimeUnit.HOURS.toMillis(2))
            return "last hour";

        // Between 2 and 5 hours
        if (timeDifference < TimeUnit.HOURS.toMillis(5))
            return numNames[(int) TimeUnit.MILLISECONDS.toHours(timeDifference)] + " hours ago";

        // Between 5 hours and 1 day
        if (pastCalendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)) {
            int hour = pastCalendar.get(Calendar.HOUR_OF_DAY);
            if (hour < 12)
                return "this morning";
            if (hour < 6)
                return "this afternoon";
            else
                return "this evening";
        }

        // Yesterday
        currentCalendar.add(Calendar.DATE, -1);
        if (pastCalendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR))
            return "yesterday";
        else
            currentCalendar.add(Calendar.DATE, 1);

        // Show name of day
        if (timeDifference < TimeUnit.DAYS.toMillis(6)) {
            Date date = new Date();
            date.setTime(pastTime);
            return new SimpleDateFormat("EEEE", Locale.US).format(date).toLowerCase();
        }

        // Last week
        if (timeDifference < TimeUnit.DAYS.toMillis(14))
            return "last week";

        // Between 2 and 4 weeks
        if (timeDifference < TimeUnit.DAYS.toMillis(31))
            return numNames[(int) (TimeUnit.MILLISECONDS.toDays(timeDifference) / 7)] + " weeks ago";

        // Show month
        if (pastCalendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) || timeDifference < TimeUnit.DAYS.toMillis(90)) {
            Date date = new Date();
            date.setTime(pastTime);
            return new SimpleDateFormat("MMMM", Locale.US).format(date).toLowerCase();
        }

        // Show years
        if (timeDifference < TimeUnit.DAYS.toMillis(730))
            return "last year";

        // Many years
        return TimeUnit.MILLISECONDS.toDays(timeDifference) / 365 + " years ago";
    }
}
