package xyz.muggr.newsly

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Fade
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.act_article.*
import kotlinx.android.synthetic.main.act_article_header.*
import xyz.muggr.newsly.Adapters.ArticleAdapter
import xyz.muggr.newsly.Articles.Article
import xyz.muggr.newsly.Utils.TransitionUtil

class ArticleActivity : NewslyActivity(), AppBarLayout.OnOffsetChangedListener {

    private lateinit var currentArticle: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_article)

        // Get resources
        currentArticle = intent.getParcelableExtra<Article>("currentArticle")

        // Set screen
        setLightStatusBar()

        // Setup RecyclerView
        val recyclerView = findViewById<View>(R.id.act_article_rv) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ArticleAdapter()

        // Set hero image
        articleTopImageIv.setColorFilter(0x33000000)
        if (!currentArticle.articleTopImage.isNullOrEmpty())
            Picasso.with(this).load(currentArticle.articleTopImage).into(articleTopImageIv)

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
        val offset = verticalOffset.toFloat() / appBarHeight.toFloat() * -1

        // Animate header
        header.alpha = 1 - offset
        articleTopImageIv.translationY = appBarHeight.toFloat() * offset * 0.5f
        redditTitleTv.translationY = appBarHeight.toFloat() * offset * -0.2f
        navBkg.alpha = 0.2f + 0.5f * offset
    }

    //=======================================================================================
    //endregion

}
