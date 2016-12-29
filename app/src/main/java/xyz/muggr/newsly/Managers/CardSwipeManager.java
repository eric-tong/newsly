package xyz.muggr.newsly.Managers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.animation.OvershootInterpolator;

import xyz.muggr.newsly.NewslyActivity;
import xyz.muggr.newsly.Views.ArticleCard;


public class CardSwipeManager implements View.OnTouchListener {

    private int[] SCREEN_SIZE;
    private int DP_1;
    private int FINAL_FLING_TRANSLATION;
    private int MINIMUM_DRAG_DISTANCE;
    private int MINIMUM_FLING_VELOCITY;
    private int MAXIMUM_FLING_VELOCITY;
    private final float FINAL_ROTATION = 26f;
    private final float TRANSITION_CARD_SCALE = 0.95f;

    private int touchState = State.NONE;
    private int flingState = State.NONE;
    private float[][] lastTouchCoords = {{0f, 0f}, {0f, 0f}};
    private VelocityTracker velocityTracker;

    private SwipableCards activity;

    private ArticleCard swipableCard;
    private ArticleCard transitionCard;

    //region CONSTRUCTORS
    //=======================================================================================

    public CardSwipeManager(SwipableCards activity, final ArticleCard swipableCard, ArticleCard transitionCard) {

        // GET RESOURCES
        this.activity = activity;
        SCREEN_SIZE = ((NewslyActivity) activity).SCREEN_SIZE;
        DP_1 = activity.DP_1;
        MINIMUM_DRAG_DISTANCE = SCREEN_SIZE[0] / 3;
        MINIMUM_FLING_VELOCITY = SCREEN_SIZE[0] * 2;
        MAXIMUM_FLING_VELOCITY = ViewConfiguration.get((Context) activity).getScaledMaximumFlingVelocity();
        swipableCard.post(new Runnable() {
            @Override
            public void run() {
                FINAL_FLING_TRANSLATION = (int) ((swipableCard.getLeft() + swipableCard.getRight())
                        + NewslyActivity.pythagoras(swipableCard.getWidth(), swipableCard.getHeight())) / 2;
            }
        });

        // GET VIEWS
        this.swipableCard = swipableCard;
        this.transitionCard = transitionCard;

        // ADD ON TOUCH LISTENER
        swipableCard.setOnTouchListener(this);

        // SET TRANSITION CARD SCALE
        transitionCard.setScaleX(TRANSITION_CARD_SCALE);
        transitionCard.setScaleY(TRANSITION_CARD_SCALE);
    }

    //=======================================================================================
    //endregion

    //region GESTURE METHODS
    //=======================================================================================

    public void scroll(MotionEvent motionEvent) {

        // SCROLL AND ROTATE SWIPABLE CARD
        swipableCard.setTranslationX(swipableCard.getTranslationX() + motionEvent.getRawX() - lastTouchCoords[0][0]);
        swipableCard.setTranslationY(swipableCard.getTranslationY() + motionEvent.getRawY() - lastTouchCoords[0][1]);
        swipableCard.setRotation(swipableCard.getTranslationX() / SCREEN_SIZE[0] * FINAL_ROTATION * (touchState == State.TOP ? 1f : -1f));

        // SET TRANSITION CARD SCALE
        float completion = Math.min(Math.abs(swipableCard.getTranslationX()) / SCREEN_SIZE[0], 1f);
        transitionCard.setScaleX(TRANSITION_CARD_SCALE + (1f - TRANSITION_CARD_SCALE) * completion);
        transitionCard.setScaleY(TRANSITION_CARD_SCALE + (1f - TRANSITION_CARD_SCALE) * completion);

    }

    public void fling(int state, float velocityX, float velocityY) {

        // CHECK IF FLINGING FROM REST (IF TRUE, POSITION Y WILL DETERMINE VELOCITY Y)
        boolean flingFromRest = velocityX == 0 && velocityY == 0;

        // GET RESOURCES
        float finalFlingTranslation = FINAL_FLING_TRANSLATION * (state == State.LEFT ? -1 : 1);

        // SET MINIMUM VELOCITY
        if ((state == State.LEFT && velocityX > MINIMUM_FLING_VELOCITY * -1)
                || (state == State.RIGHT && velocityX < MINIMUM_FLING_VELOCITY))
            velocityX = MINIMUM_FLING_VELOCITY * (state == State.LEFT ? -1 : 1);

        // GET DURATION
        long flingDuration = (long) Math.abs((finalFlingTranslation - swipableCard.getTranslationX()) * 1000 / velocityX);

        // SETUP ANIMATION
        ViewPropertyAnimator flingAnim = swipableCard.animate()
                .translationX(finalFlingTranslation)
                .rotation(FINAL_ROTATION * (state == State.LEFT ? -1 : 1) * (touchState == State.TOP ? 1 : -1))
                .setDuration(flingDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        resetCards();
                        activity.swipe(flingState);
                        touchState = State.NONE;
                        flingState = State.NONE;
                    }
                });

        // ADD VERTICAL VELOCITY
        if (flingFromRest)
            velocityY = swipableCard.getTranslationY() * Math.abs(velocityX / swipableCard.getTranslationX());
        if (velocityY != 0)
            flingAnim.translationY(velocityY * flingDuration / 1000);

        // SETUP TRANSITION CARD ANIM
        transitionCard.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(flingDuration)
                .setInterpolator(new LinearOutSlowInInterpolator());

    }

    //=======================================================================================
    //endregion

    //region ON TOUCH METHODS
    //=======================================================================================

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // GET VELOCITY TRACKER
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(motionEvent);

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // SET TOUCH STATE BASED ON WHETHER USER TOUCHES THE
                // TOP OR BOTTOM OF CARD
                if (touchState == State.NONE && flingState == State.NONE && view != null) {
                    if (motionEvent.getRawY() - view.getTop() < view.getHeight() / 2 + 32 * DP_1)
                        touchState = State.TOP;
                    else
                        touchState = State.BOTTOM;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchState != State.NONE)
                    scroll(motionEvent);
                break;
            case MotionEvent.ACTION_UP:

                // IF TRANSLATION IS ZERO, A CLICK WAS PERFORMED
                if (touchState != State.NONE && swipableCard.getTranslationX() == 0 && swipableCard.getTranslationY() == 0) {
                    // TODO ADD ONCLICK
                    break;
                }

                // CALCULATE VELOCITIES
                velocityTracker.computeCurrentVelocity(1000, MAXIMUM_FLING_VELOCITY);
                float velocityX = velocityTracker.getXVelocity();
                float velocityY = velocityTracker.getYVelocity();

                // CHECK FOR TOUCH STATE, MINIMUM FLING VELOCITY
                // AND FLING VELOCITY MATCHES TRANSLATION X DIRECTION
                if (touchState != State.NONE && NewslyActivity.pythagoras(velocityX, velocityY) > MINIMUM_FLING_VELOCITY
                        && swipableCard.getTranslationX() / velocityX >= 0) {

                    // SET NEW FLING STATE
                    flingState = velocityX < 0 ? State.LEFT : State.RIGHT;

                    // START FLING
                    fling(flingState, velocityX, velocityY);

                } else if (Math.abs(swipableCard.getTranslationX()) >= MINIMUM_DRAG_DISTANCE) {

                    // START FLING IF TRANSLATION MORE THAN THRESHOLD
                    flingState = swipableCard.getTranslationX() < 0 ? State.LEFT : State.RIGHT;
                    fling(flingState, 0, 0);
                } else {

                    // RETURN CARD TO ORIGINAL POSITION
                    swipableCard.animate()
                            .translationX(0)
                            .translationY(0)
                            .rotation(0)
                            .setDuration(300)
                            .setInterpolator(new OvershootInterpolator(1.5f))
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    touchState = State.NONE;
                                }
                            });
                }

                // RECYCLE VELOCITY TRACKER
                velocityTracker.recycle();
                velocityTracker = null;

                break;
        }

        // SAVE THE LAST TWO MOTION EVENTS
        lastTouchCoords[1][0] = lastTouchCoords[0][0];
        lastTouchCoords[1][1] = lastTouchCoords[0][1];
        lastTouchCoords[0][0] = motionEvent.getRawX();
        lastTouchCoords[0][1] = motionEvent.getRawY();

        return view == null;
    }

    //=======================================================================================
    //endregion

    //region CARD TRANSITION METHODS
    //=======================================================================================

    private void resetCards() {
        swipableCard.setTranslationX(0);
        swipableCard.setTranslationY(0);
        swipableCard.setRotation(0);
        transitionCard.setScaleX(TRANSITION_CARD_SCALE);
        transitionCard.setScaleY(TRANSITION_CARD_SCALE);
    }

//=======================================================================================
//endregion

    //region INTERFACES
    //=======================================================================================

    public interface SwipableCards {
        int DP_1 = 3;
        void swipe(int state);
    }

    //=======================================================================================
    //endregion

    //region KEYS
    //=======================================================================================

    public static class State {
        public static final int NONE = 0x0000;
        public static final int TOP = 0x0001;
        public static final int RIGHT = 0x0010;
        public static final int BOTTOM = 0x0100;
        public static final int LEFT = 0x1000;
    }

    //=======================================================================================
    //endregion
}
