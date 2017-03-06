package xyz.muggr.newsly;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.Articles.ArticleList;
import xyz.muggr.newsly.Managers.ArticleQueueManager;
import xyz.muggr.newsly.Managers.CardSwipeManager;
import xyz.muggr.newsly.Managers.DatabaseManager;
import xyz.muggr.newsly.Views.ArticleCard;

public class HomepageActivity extends NewslyActivity implements CardSwipeManager.SwipableCards, ArticleQueueManager.ArticleQueueListener {

    private DatabaseManager databaseManager;
    private CardSwipeManager cardSwipeManager;
    private ArticleQueueManager articleQueueManager;

    private ArticleCard swipableCard;
    private ArticleCard transitionCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_homepage);

        // Set theme
        setLightStatusBar();

        // Get views
        swipableCard = (ArticleCard) findViewById(R.id.act_homepage_swipable_card);
        transitionCard = (ArticleCard) findViewById(R.id.act_homepage_transition_card);

        // Get handlers
        databaseManager = DatabaseManager.getInstance(this);
        cardSwipeManager = new CardSwipeManager(this, swipableCard, transitionCard);
        articleQueueManager = new ArticleQueueManager(this);

        // Load cards
        articleQueueManager.load(this);
    }
    
    //region User interaction methods
    //=======================================================================================

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cardSwipeManager.onTouch(null, event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onSwipe(int state) {
        articleQueueManager.dismiss(state);
        articleQueueManager.load(this);
    }


    //=======================================================================================
    //endregion

    //region Article queue methods
    //=======================================================================================

    @Override
    public void onArticleQueueLoaded(ArticleList articleQueue) {

        if (articleQueue.isEmpty()) {
            // TODO SHOW CONNECTION ERROR
            return;
        }

        Article currentArticle = articleQueue.get(0);
        swipableCard.setArticle(currentArticle);

        if (articleQueue.size() > 1) {
            transitionCard.setVisibility(View.VISIBLE);
            transitionCard.setArticle(articleQueue.get(1));
        } else
            transitionCard.setVisibility(View.INVISIBLE);

    }

    //=======================================================================================
    //endregion

}
