package xyz.muggr.newsly.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MathUtil {

    public static double pythagoras(float a, float b) {
        return Math.sqrt(a * a + b * b);
    }

    public static String getHumanTime(long pastTime) {
        long timeDifference = new Date().getTime() - pastTime;
        String timeText;
        if (timeDifference < TimeUnit.HOURS.toMillis(2))
            timeText = "just now";
        else if (timeDifference < TimeUnit.DAYS.toMillis(1))
            timeText = "today";
        else if (timeDifference < TimeUnit.DAYS.toMillis(2))
            timeText = "yesterday";
        else if (timeDifference < TimeUnit.DAYS.toMillis(6)) {
            Date date = new Date();
            date.setTime(pastTime);
            timeText = new SimpleDateFormat("EEEE", Locale.US).format(date).toLowerCase();
        } else if (timeDifference < TimeUnit.DAYS.toMillis(14))
            timeText = "last week";
        else if (timeDifference < TimeUnit.DAYS.toMillis(31))
            timeText = TimeUnit.MILLISECONDS.toDays(timeDifference) / 7 + " weeks ago";
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(pastTime);
            if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) || timeDifference < TimeUnit.DAYS.toMillis(90)) {
                Date date = new Date();
                date.setTime(pastTime);
                timeText = new SimpleDateFormat("MMMM", Locale.US).format(date).toLowerCase();
            } else if (timeDifference < TimeUnit.DAYS.toMillis(730))
                timeText = "last year";
            else
                timeText = TimeUnit.MILLISECONDS.toDays(timeDifference) / 365 + " years ago";
        }
        return timeText;
    }
}
