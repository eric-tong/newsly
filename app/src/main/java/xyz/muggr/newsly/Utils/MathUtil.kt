package xyz.muggr.newsly.Utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object MathUtil {

    private val numNames = arrayOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")

    fun pythagoras(a: Float, b: Float): Double {
        return Math.sqrt((a * a + b * b).toDouble())
    }

    fun getHumanTime(pastTime: Long): String {

        val timeDifference = Date().time - pastTime
        val currentCalendar = Calendar.getInstance()
        val pastCalendar = Calendar.getInstance()
        if (timeDifference * -1 > Integer.MIN_VALUE)
            pastCalendar.add(Calendar.MILLISECOND, (timeDifference * -1).toInt())
        else
            pastCalendar.add(Calendar.SECOND, (timeDifference * -1000).toInt())

        // Less than 1 hour
        if (timeDifference < TimeUnit.HOURS.toMillis(1))
            return "just now"

        // Between 1 and 2 hours
        if (timeDifference < TimeUnit.HOURS.toMillis(2))
            return "last hour"

        // Between 2 and 5 hours
        if (timeDifference < TimeUnit.HOURS.toMillis(5))
            return numNames[TimeUnit.MILLISECONDS.toHours(timeDifference).toInt()] + " hours ago"

        // Between 5 hours and 1 day
        if (pastCalendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)) {
            val hour = pastCalendar.get(Calendar.HOUR_OF_DAY)
            if (hour < 12)
                return "this morning"
            else if (hour < 18)
                return "this afternoon"
            else
                return "this evening"
        }

        // Yesterday
        currentCalendar.add(Calendar.DATE, -1)
        if (pastCalendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR))
            return "yesterday"
        else
            currentCalendar.add(Calendar.DATE, 1)

        // Show name of day
        if (timeDifference < TimeUnit.DAYS.toMillis(6)) {
            val date = Date()
            date.time = pastTime
            return SimpleDateFormat("EEEE", Locale.US).format(date).toLowerCase()
        }

        // Last week
        if (timeDifference < TimeUnit.DAYS.toMillis(14))
            return "last week"

        // Between 2 and 4 weeks
        if (timeDifference < TimeUnit.DAYS.toMillis(31))
            return numNames[(TimeUnit.MILLISECONDS.toDays(timeDifference) / 7).toInt()] + " weeks ago"

        // Show month
        if (pastCalendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) || timeDifference < TimeUnit.DAYS.toMillis(90)) {
            val date = Date()
            date.time = pastTime
            return SimpleDateFormat("MMMM", Locale.US).format(date).toLowerCase()
        }

        // Show years
        if (timeDifference < TimeUnit.DAYS.toMillis(730))
            return "last year"

        // Many years
        return (TimeUnit.MILLISECONDS.toDays(timeDifference) / 365).toString() + " years ago"
    }
}
