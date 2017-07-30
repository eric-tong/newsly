package xyz.muggr.newsly.Managers

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import xyz.muggr.newsly.Articles.Article
import xyz.muggr.newsly.Articles.ArticleList
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

object ApiManager {

    val ROOT: String = "http://newsly.muggr.xyz/api/v1/"

    //region Getters
    //=======================================================================================
    @Throws(IOException::class, JSONException::class)
    fun getArticleListFromUrl(url: String): ArticleList {
        val connection = URL(url).openConnection()
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

    fun getArticle(redditId: String): ArticleList {
        return getArticleListFromUrl(ROOT + "article/" + redditId)
    }

    fun getCards(): ArticleList {
        return getArticleListFromUrl(ROOT + "cards")
    }

    //=======================================================================================
    //endregion

}
