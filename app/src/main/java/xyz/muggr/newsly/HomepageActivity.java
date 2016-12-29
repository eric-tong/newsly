package xyz.muggr.newsly;

import android.os.Bundle;
import android.view.MotionEvent;

import xyz.muggr.newsly.Managers.CardSwipeManager;
import xyz.muggr.newsly.Managers.DatabaseManager;
import xyz.muggr.newsly.Views.ArticleCard;

public class HomepageActivity extends NewslyActivity implements CardSwipeManager.SwipableCards {

    private DatabaseManager databaseManager;
    private CardSwipeManager cardSwipeManager;

    private ArticleCard swipableCard;
    private ArticleCard transitionCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_homepage);

        // Get views
        swipableCard = (ArticleCard) findViewById(R.id.act_homepage_swipable_card);
        transitionCard = (ArticleCard) findViewById(R.id.act_homepage_transition_card);

        // Get handlers
        databaseManager = DatabaseManager.getInstance(this);
        cardSwipeManager = new CardSwipeManager(this, swipableCard, transitionCard);

    }

    //region CARD SWIPE METHODS
    //=======================================================================================

    @Override
    public void swipe(int state) {
        //productQueueHandler.dismiss(state == CardSwipeHandler.State.RIGHT ? Product.Status.ACCEPTED : Product.Status.REJECTED);
        //productQueueHandler.load(this);
    }

    //=======================================================================================
    //endregion

    //region ON TOUCH METHODS
    //=======================================================================================

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cardSwipeManager.onTouch(null, event);
        return super.onTouchEvent(event);
    }

    //=======================================================================================
    //endregion

}
