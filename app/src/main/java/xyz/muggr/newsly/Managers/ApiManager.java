package xyz.muggr.newsly.Managers;

import android.util.Log;

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

public class ApiManager {

    private static final String ROOT = "http://newsly-api.herokuapp.com/api/";

    //region Getters
    //=======================================================================================

    private static String getFromUrl(String Url) throws IOException {

        // Open connection
        URLConnection connection = (new URL(Url)).openConnection();
        connection.setRequestProperty("User-Agent",
                String.format("android:xyz.muggr.newsly:v%1$s (by /u/regimme)", BuildConfig.VERSION_NAME));
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();

        // Read inputStream
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder jsonBuilder = new StringBuilder();
        String inputString;
        while ((inputString = streamReader.readLine()) != null)
            jsonBuilder.append(inputString);
        return jsonBuilder.toString();
    }

    public static String getUuid() throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(getFromUrl(ROOT + "register"));
        if (isValid(jsonObject))
            return jsonObject.getString("uuid");
        else
            return null;
    }

    //=======================================================================================
    //endregion

    //region Check methods
    //=======================================================================================

    private static boolean isValid(JSONObject jsonObject) throws JSONException {
        JSONObject status = jsonObject.getJSONObject("status");
        if (status.getInt("code") == 200)
            return true;
        else {
            // TODO Add Firebase error logger
            if (BuildConfig.DEBUG)
                Log.e("Json Error", status.toString());
            return false;
        }
    }

    //=======================================================================================
    //endregion

    //region Testing methods
    //=======================================================================================

    public static ArticleList getRedditArticles() throws IOException, JSONException {

        // Open connection
        URLConnection connection = (new URL("https://www.reddit.com/r/worldnews/.json?sort=top&t=all&limit=100")).openConnection();
        connection.setRequestProperty("User-Agent",
                String.format("android:xyz.muggr.newsly:v%1$s (by /u/regimme)", BuildConfig.VERSION_NAME));
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
            JSONObject articleJson = listingJson.getJSONObject(i);
            if (articleJson.getString("kind").equals("t3"))
                articles.add(new Article(articleJson.getJSONObject("data")));
        }

        return articles;
    }

    //=======================================================================================
    //endregion

}
