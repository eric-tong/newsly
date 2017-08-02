package xyz.muggr.newsly.Adapters

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import org.json.JSONArray
import xyz.muggr.newsly.Articles.Article
import xyz.muggr.newsly.R
import java.util.AbstractMap.SimpleEntry

class ArticleAdapter(private var currentArticle: Article) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var contentList: ArrayList<SimpleEntry<Int, String>>

    //region ViewHolder methods
    //=======================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_article_title, parent, false)) {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as TextView).text = Html.fromHtml(contentList[position].value)
    }

    override fun getItemCount(): Int {
        if (currentArticle.articleText.isNullOrEmpty()) return 0
        else return contentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return contentList[position].key
    }

    //=======================================================================================
    //endregion

    //region Setters
    //=======================================================================================

    fun setCurrentArticle(article: Article) {
        // Set article
        currentArticle = article

        // Init
        contentList = ArrayList()

        // Populate content paragraphs
        val paragraphsJson = JSONArray(article.articleText)
        for (i in 0..(paragraphsJson.length() - 1)) {
            contentList.add(SimpleEntry(0, paragraphsJson[i].toString()))
        }

        // Add title
        if (!article.articleTitle.isNullOrEmpty())
            contentList.add(0, SimpleEntry(0, article.articleTitle!!))

        notifyItemRangeInserted(0, contentList.size)
    }

    //=======================================================================================
    //endregion

    //region Keys
    //=======================================================================================

    class ContentType {
        val ARTICLE_TITLE = 0
        val ARTICLE_PARAGRAPHS = 1
    }

    //=======================================================================================
    //endregion


}
