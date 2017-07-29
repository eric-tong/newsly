package xyz.muggr.newsly.Managers

import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.util.Log

import xyz.muggr.newsly.Articles.ArticleList
import xyz.muggr.newsly.BuildConfig
import xyz.muggr.newsly.NewslyActivity

class ArticleQueueManager(private val activity: NewslyActivity) {

    private val articleQueue: ArticleList
    private val database: SQLiteDatabase

    init {
        database = DatabaseManager.getInstance(activity).writableDatabase
        articleQueue = ArticleList()
    }

    //region Article queue modifiers
    //=======================================================================================

    fun load(listener: ArticleQueueListener) {

        if (articleQueue.size < 2)
            object : AsyncTask<Void, Void, ArticleList>() {
                override fun doInBackground(vararg params: Void): ArticleList? {
                    try {
                        return ApiManager.redditArticles
                    } catch (e: Exception) {
                        if (BuildConfig.DEBUG)
                            e.printStackTrace()
                        return null
                    }

                }

                override fun onPostExecute(articleList: ArticleList?) {
                    super.onPostExecute(articleList)
                    if (articleList != null) {
                        articleQueue.addAll(articleList)
                    }
                    listener.onArticleQueueLoaded(articleQueue)

                    if (BuildConfig.DEBUG)
                        for (i in articleQueue.indices) {
                            Log.d(
                                    String.format("Queue %02d", i),
                                    articleQueue[i].redditTitle
                            )
                        }
                }

            }.execute()
        else
            listener.onArticleQueueLoaded(articleQueue)

    }

    fun dismiss(state: Int) {
        if (!articleQueue.isEmpty()) {
            articleQueue.removeAt(0)
        }
    }

    //=======================================================================================
    //endregion

    //region Interfaces
    //=======================================================================================

    interface ArticleQueueListener {
        fun onArticleQueueLoaded(articleQueue: ArticleList)
    }

    //=======================================================================================
    //endregion

}
