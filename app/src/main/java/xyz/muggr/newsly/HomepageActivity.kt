package xyz.muggr.newsly

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.act_homepage.*
import xyz.muggr.newsly.Articles.ArticleList
import xyz.muggr.newsly.Managers.ArticleQueueManager
import xyz.muggr.newsly.Managers.CardSwipeManager
import xyz.muggr.newsly.Managers.DatabaseManager
import xyz.muggr.newsly.Tasks.GetArticleListTask

class HomepageActivity : NewslyActivity(), CardSwipeManager.SwipableCards, GetArticleListTask.ArticleListListener {

    private lateinit var databaseManager: DatabaseManager
    private lateinit var cardSwipeManager: CardSwipeManager
    private lateinit var articleQueueManager: ArticleQueueManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_homepage)

        // Set theme
        setLightStatusBar()

        // Get handlers
        databaseManager = DatabaseManager.getInstance(this)
        cardSwipeManager = CardSwipeManager(this, swipableCard, transitionCard)
        articleQueueManager = ArticleQueueManager(this)

        // Load CARDS
        articleQueueManager.load(this)
    }

    //region User interaction methods
    //=======================================================================================

    override fun onTouchEvent(event: MotionEvent): Boolean {
        cardSwipeManager.onTouch(null, event)
        return super.onTouchEvent(event)
    }

    override fun onSwipe(state: Int) {
        articleQueueManager.dismiss(state)
        articleQueueManager.load(this)
    }

    //=======================================================================================
    //endregion

    //region Article queue methods
    //=======================================================================================

    override fun onArticleListLoadSuccess(articleQueue: ArticleList) {
        if (articleQueue.isEmpty()) {
            // TODO SHOW CONNECTION ERROR
            return
        }

        val currentArticle = articleQueue[0]
        swipableCard.setArticle(currentArticle)

        if (articleQueue.size > 1) {
            transitionCard.visibility = View.VISIBLE
            transitionCard.setArticle(articleQueue[1])
        } else
            transitionCard.visibility = View.INVISIBLE

    }

    override fun onArticleListLoadFail() {

    }

    //=======================================================================================
    //endregion

}
