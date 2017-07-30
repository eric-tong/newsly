package xyz.muggr.newsly.Managers

import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import xyz.muggr.newsly.Articles.ArticleList
import xyz.muggr.newsly.BuildConfig
import xyz.muggr.newsly.NewslyActivity

class ArticleQueueManager(private val activity: NewslyActivity) {

    private var articleQueue: ArticleList
    private val database: SQLiteDatabase

    init {
        database = DatabaseManager.getInstance(activity).writableDatabase
        articleQueue = ArticleList()
    }

    //region Article queue modifiers
    //=======================================================================================

    fun load(listener: ArticleQueueListener) {
        GetArticleListTask(articleQueue, listener).execute()
    }

    fun dismiss(state: Int) {
        if (!articleQueue.isEmpty()) {
            articleQueue.removeAt(0)
        }
    }

    //=======================================================================================
    //endregion

    //region Asynctasks
    //=======================================================================================

    class GetArticleListTask(private val articleQueue: ArticleList, private val listener: ArticleQueueListener) : AsyncTask<Void, Void, ArticleList>() {

        override fun doInBackground(vararg params: Void): ArticleList? {
            try {
                return ApiManager.getCards()
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
