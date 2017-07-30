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
    fun getArticleList(queryParams: QueryParams): ArticleList {
        val connection = URL(ROOT + queryParams.toURL()).openConnection()
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
        val articles = ArticleList()
        val listingJson = JSONArray(jsonBuilder.toString())
        val gson = Gson()
        (0..listingJson.length() - 1).mapTo(articles) { gson.fromJson(listingJson.getJSONObject(it).toString(), Article::class.java) }

        return articles
    }

    //=======================================================================================
    //endregion

    //region Helpers
    //=======================================================================================

    class QueryParams(private val type: QueryTypes, private val redditId: String? = null) {
        fun toURL(): String {
            var url: String = ""
            when (type) {
                QueryTypes.ARTICLE -> {
                    url += "article/"
                    url += redditId
                }
                QueryTypes.CARDS -> url += "cards"
            }
            return url
        }
    }

    enum class QueryTypes {
        CARDS, ARTICLE
    }

    //=======================================================================================
    //endregion


}
