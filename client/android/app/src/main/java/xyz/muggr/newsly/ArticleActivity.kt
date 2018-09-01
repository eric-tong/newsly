package xyz.muggr.newsly

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Fade
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.act_article.*
import kotlinx.android.synthetic.main.act_article_header.*
import xyz.muggr.newsly.Adapters.ArticleAdapter
import xyz.muggr.newsly.Articles.Article
import xyz.muggr.newsly.Articles.ArticleList
import xyz.muggr.newsly.Managers.ApiManager
import xyz.muggr.newsly.Tasks.GetArticleListTask
import xyz.muggr.newsly.Utils.TransitionUtil
import java.lang.ref.WeakReference

class ArticleActivity : NewslyActivity(), AppBarLayout.OnOffsetChangedListener, GetArticleListTask.ArticleListListener {

    private lateinit var currentArticle: Article

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_article)

        // Get resources
        currentArticle = intent.getParcelableExtra<Article>("currentArticle")

        // Set screen
        setLightStatusBar()

        // Setup RecyclerView
        layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter(currentArticle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter

        // Get article content
        val queryParams = ApiManager.QueryParams(type = ApiManager.QueryTypes.ARTICLE, redditId = currentArticle.redditId)
        GetArticleListTask(queryParams, WeakReference(this)).execute()

        // Set hero image
        articleTopImageIv.setColorFilter(0x33000000)
        if (!currentArticle.articleTopImage.isNullOrEmpty())
            Picasso.with(this).load(currentArticle.articleTopImage).placeholder(R.drawable.bkg_loading_placeholder).into(articleTopImageIv)
        else
            articleTopImageIv.setImageResource(R.drawable.bkg_loading_placeholder)

        // Set headline
        redditTitleTv.text = currentArticle.redditTitle
        redditTitleTv.layoutParams.width = intent.getIntExtra("headlineTvWidth", ConstraintLayout.LayoutParams.MATCH_PARENT)
        redditTitleBkg.layoutParams.height = intent.getIntExtra("headlineTvHeight", 0)

        // Set flair
        if (currentArticle.redditFlair != null) {
            redditFlairBkg.visibility = View.VISIBLE
            redditFlairTv.visibility = View.VISIBLE
            redditFlairTv.text = currentArticle.redditFlair
        }

        // Setup transitions
        val fade = Fade()
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        fade.excludeTarget(R.id.navBkg, true)
        window.enterTransition = fade

        // Set transition names
        ViewCompat.setTransitionName(articleTopImageIv, TransitionUtil.heroIvTransition)
        ViewCompat.setTransitionName(redditTitleBkg, TransitionUtil.headlineBkgTransition)
        ViewCompat.setTransitionName(redditTitleTv, TransitionUtil.headlineTvTransition)
        ViewCompat.setTransitionName(redditFlairBkg, TransitionUtil.flairBkgTransition)
        ViewCompat.setTransitionName(redditFlairTv, TransitionUtil.tagTvTransition)
        ViewCompat.setTransitionName(navbar, TransitionUtil.navbarTransition)

        // Set scrolling animation
        appBarLayout.addOnOffsetChangedListener(this)
    }

    //region Listener methods
    //=======================================================================================

    internal var appBarHeight = 0

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        // Get total height
        if (appBarHeight == 0) appBarHeight = appBarLayout.height

        // Get percentage visibility
        var offset = verticalOffset.toFloat() / appBarHeight.toFloat()
        offset *= offset

        // Animate header
        header.alpha = 1 - offset
        articleTopImageIv.translationY = appBarHeight.toFloat() * offset * 0.5f
        redditTitleTv.translationY = appBarHeight.toFloat() * offset * -0.2f
        navBkg.alpha = 0.2f + 0.5f * offset
    }

    override fun onArticleListLoadSuccess(articleList: ArticleList) {
        articleAdapter.setCurrentArticle(articleList[0])
    }

    override fun onArticleListLoadFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //=======================================================================================
    //endregion

}
