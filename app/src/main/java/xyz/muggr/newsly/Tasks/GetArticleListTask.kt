package xyz.muggr.newsly.Tasks

import android.os.AsyncTask
import xyz.muggr.newsly.Articles.ArticleList
import xyz.muggr.newsly.BuildConfig
import xyz.muggr.newsly.Managers.ApiManager
import java.lang.ref.WeakReference

class GetArticleListTask(private val queryParams: ApiManager.QueryParams, private val articleListListener: WeakReference<ArticleListListener>, private var updateList: ArticleList? = null) : AsyncTask<Void, Void, ArticleList>() {

    override fun doInBackground(vararg params: Void): ArticleList? {
        try {
            return ApiManager.getArticleList(queryParams)
        } catch (e: Exception) {
            if (BuildConfig.DEBUG)
                e.printStackTrace()
            return null
        }

    }

    override fun onPostExecute(articleList: ArticleList?) {
        super.onPostExecute(articleList)
        if (articleList != null && articleList.isNotEmpty()) {
            articleListListener.get()?.onArticleListLoadSuccess(articleList)
            if (updateList != null) (updateList as ArticleList).addAll(articleList)
        } else
            articleListListener.get()?.onArticleListLoadFail()
    }

    //region Interfaces
    //=======================================================================================

    interface ArticleListListener {
        fun onArticleListLoadSuccess(articleQueue: ArticleList)
        fun onArticleListLoadFail()
    }

    //=======================================================================================
    //endregion

}