package xyz.muggr.newsly.Managers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.Articles.ArticleList;
import xyz.muggr.newsly.BuildConfig;

public class RedditManager {

    public static ArticleList getArticles() throws IOException, JSONException {

        // Open connection
        URLConnection connection = (new URL("https://www.reddit.com/r/worldnews/.json")).openConnection();
        connection.setRequestProperty("User-Agent",
                String.format("User-Agent: android:xyz.muggr.newsly:v%1$s (by /u/regimme)", BuildConfig.VERSION_NAME));
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();

        // Read inputStream
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder jsonBuilder = new StringBuilder();
        String inputString;
        while ((inputString = streamReader.readLine()) != null)
            jsonBuilder.append(inputString);

        // Parse json to ArticleList
        ArticleList articles = new ArticleList();
        JSONArray listingJson = new JSONObject(jsonBuilder.toString()).getJSONObject("data").getJSONArray("children");
        for (int i = 0; i < listingJson.length(); i++) {
            articles.add(new Article(listingJson.getJSONObject(i).getJSONObject("data")));
        }

        return articles;
    }

}
