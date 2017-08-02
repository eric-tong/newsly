package xyz.muggr.newsly.Adapters

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import org.json.JSONArray
import xyz.muggr.newsly.Articles.Article
import xyz.muggr.newsly.R

class ArticleAdapter(private var currentArticle: Article) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var paragraphs: ArrayList<String>

    //region ViewHolder methods
    //=======================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_article_title, parent, false)) {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as TextView).text = Html.fromHtml(paragraphs[position])
    }

    override fun getItemCount(): Int {
        if (currentArticle.articleText.isNullOrEmpty()) return 0
        else return paragraphs.size
    }

    //=======================================================================================
    //endregion

    //region Setters
    //=======================================================================================

    fun setCurrentArticle(article: Article) {
        // Set article
        currentArticle = article

        // Populate paragraphs array
        var paragraphsJson = JSONArray(article.articleText)
        paragraphs = ArrayList<String>(paragraphsJson.length())
        for (i in 0..(paragraphsJson.length() - 1)) {
            paragraphs.add(paragraphsJson[i].toString())
        }
        notifyItemRangeInserted(0, paragraphs.size)
    }

    //=======================================================================================
    //endregion


}
