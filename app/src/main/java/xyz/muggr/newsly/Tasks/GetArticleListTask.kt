package xyz.muggr.newsly.Tasks

import android.os.AsyncTask
import xyz.muggr.newsly.Articles.ArticleList
import xyz.muggr.newsly.BuildConfig
import xyz.muggr.newsly.Managers.ApiManager
import java.lang.ref.WeakReference

class GetArticleListTask(private val articleListListener: WeakReference<ArticleListListener>) : AsyncTask<Void, Void, ArticleList>() {

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
        if (articleList != null && articleList.isNotEmpty()) {
            articleListListener.get()?.onArticleListLoaded(articleList)
        } else
            articleListListener.get()?.onArticleListFail()
    }

    //region Interfaces
    //=======================================================================================

    interface ArticleListListener {
        fun onArticleListLoaded(articleQueue: ArticleList)
        fun onArticleListFail()
    }

    //=======================================================================================
    //endregion

}