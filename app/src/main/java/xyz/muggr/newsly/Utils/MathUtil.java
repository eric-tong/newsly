package xyz.muggr.newsly.Utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MathUtil {

    public static double pythagoras(float a, float b) {
        return Math.sqrt(a * a + b * b);
    }

    public static String getTimeDifference(long pastTime) {
        long timeDifference = new Date().getTime() - pastTime;
        String timeText;
        if (timeDifference < TimeUnit.MINUTES.toMillis(10))
            timeText = "just now";
        else if (timeDifference < TimeUnit.HOURS.toMillis(1))
            timeText = TimeUnit.MILLISECONDS.toMinutes(timeDifference) + " mins ago";
        else if (timeDifference < TimeUnit.HOURS.toMillis(2))
            timeText = "last hour";
        else if (timeDifference < TimeUnit.DAYS.toMillis(1))
            timeText = TimeUnit.MILLISECONDS.toHours(timeDifference) + " hours ago";
        else if (timeDifference < TimeUnit.DAYS.toMillis(2))
            timeText = "yesterday";
        else if (timeDifference < TimeUnit.DAYS.toMillis(7))
            timeText = TimeUnit.MILLISECONDS.toDays(timeDifference) + " days ago";
        else if (timeDifference < TimeUnit.DAYS.toMillis(14))
            timeText = "last week";
        else if (timeDifference < TimeUnit.DAYS.toMillis(30))
            timeText = TimeUnit.MILLISECONDS.toDays(timeDifference) / 7 + " weeks ago";
        else if (timeDifference < TimeUnit.DAYS.toMillis(60))
            timeText = "last month";
        else if (timeDifference < TimeUnit.DAYS.toMillis(365))
            timeText = TimeUnit.MILLISECONDS.toDays(timeDifference) / 30 + " months ago";
        else if (timeDifference < TimeUnit.DAYS.toMillis(730))
            timeText = TimeUnit.MILLISECONDS.toDays(timeDifference) / 30 + " months ago";
        else
            timeText = TimeUnit.MILLISECONDS.toDays(timeDifference) / 365 + " years ago";
        return timeText;
    }
}
