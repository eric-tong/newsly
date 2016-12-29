package xyz.muggr.newsly.Managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import xyz.muggr.newsly.BuildConfig;

public class RedditManager {

    public static String getArticles() throws IOException {

        // Open connection
        URLConnection connection = (new URL("https://www.reddit.com/r/worldnews/.json")).openConnection();
        connection.setRequestProperty("User-Agent",
                String.format("User-Agent: android:xyz.muggr.newsly:v%1$s (by /u/regimme)", BuildConfig.VERSION_NAME));
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        // Read inputStream
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder jsonBuilder = new StringBuilder();
        String inputString;
        while ((inputString = streamReader.readLine()) != null)
            jsonBuilder.append(inputString);

        return jsonBuilder.toString();

    }

}
