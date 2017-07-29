package xyz.muggr.newsly.Managers

import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import xyz.muggr.newsly.Articles.Article
import xyz.muggr.newsly.Articles.ArticleList
import xyz.muggr.newsly.BuildConfig
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

object ApiManager {

    private val ROOT = "http://newsly-api.herokuapp.com/api/"

    //region Getters
    //=======================================================================================

    @Throws(IOException::class)
    private fun getFromUrl(Url: String): String {

        // Open connection
        val connection = URL(Url).openConnection()
        connection.setRequestProperty("User-Agent",
                String.format("android:xyz.muggr.newsly:v%1\$s (by /u/regimme)", BuildConfig.VERSION_NAME))
        connection.connectTimeout = 10000
        connection.readTimeout = 10000
        connection.connect()

        // Read inputStream
        val streamReader = BufferedReader(InputStreamReader(connection.getInputStream(), "UTF-8"))
        val jsonBuilder = StringBuilder()
        var inputString: String? = streamReader.readLine()
        while (inputString != null) {
            jsonBuilder.append(inputString)
            inputString = streamReader.readLine()
        }
        return jsonBuilder.toString()
    }

    val uuid: String?
        @Throws(IOException::class, JSONException::class)
        get() {
            val jsonObject = JSONObject(getFromUrl(ROOT + "register"))
            if (isValid(jsonObject))
                return jsonObject.getString("uuid")
            else
                return null
        }

    //=======================================================================================
    //endregion

    //region Check methods
    //=======================================================================================

    @Throws(JSONException::class)
    private fun isValid(jsonObject: JSONObject): Boolean {
        val status = jsonObject.getJSONObject("status")
        if (status.getInt("code") == 200)
            return true
        else {
            // TODO Add Firebase error logger
            if (BuildConfig.DEBUG)
                Log.e("Json Error", status.toString())
            return false
        }
    }

    //=======================================================================================
    //endregion

    //region Testing methods
    //=======================================================================================

    // Open connection
    // Read inputStream
    // Parse json to ArticleList
    val redditArticles: ArticleList
        @Throws(IOException::class, JSONException::class)
        get() {
            val connection = URL("http://newsly.muggr.xyz/api/v1/cards").openConnection()
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.connect()
            val streamReader = BufferedReader(InputStreamReader(connection.getInputStream(), "UTF-8"))
            val jsonBuilder = StringBuilder()
            var inputString: String? = streamReader.readLine()
            while (inputString != null) {
                jsonBuilder.append(inputString)
                inputString = streamReader.readLine()
            }
            println(jsonBuilder.toString())
            val articles = ArticleList()
            val listingJson = JSONArray(jsonBuilder.toString())
            val gson = Gson()
            for (i in 0..listingJson.length() - 1) {
                articles.add(gson.fromJson(listingJson.getJSONObject(i).toString(), Article::class.java))
            }

            return articles
        }

    //=======================================================================================
    //endregion

}
