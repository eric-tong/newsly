package xyz.muggr.newsly.Managers

import android.database.sqlite.SQLiteDatabase
import xyz.muggr.newsly.Articles.ArticleList
import xyz.muggr.newsly.NewslyActivity
import xyz.muggr.newsly.Tasks.GetArticleListTask
import java.lang.ref.WeakReference

class ArticleQueueManager(private val activity: NewslyActivity) {

    private var articleQueue: ArticleList
    private val database: SQLiteDatabase

    init {
        database = DatabaseManager.getInstance(activity).writableDatabase
        articleQueue = ArticleList()
    }

    //region Article queue modifiers
    //=======================================================================================

    fun load(listener: GetArticleListTask.ArticleListListener) {
        if (articleQueue.isEmpty()) {
            val queryParams = ApiManager.QueryParams(type = ApiManager.QueryTypes.CARDS)
            GetArticleListTask(queryParams, WeakReference(listener), articleQueue).execute()
        } else {
            listener.onArticleListLoadSuccess(articleQueue)
        }
    }

    fun dismiss(state: Int) {
        if (!articleQueue.isEmpty()) {
            articleQueue.removeAt(0)
        }
    }

    //=======================================================================================
    //endregion

}
