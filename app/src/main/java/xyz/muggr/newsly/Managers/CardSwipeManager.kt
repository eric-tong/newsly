package xyz.muggr.newsly.Managers

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.OvershootInterpolator
import xyz.muggr.newsly.NewslyActivity
import xyz.muggr.newsly.Utils.MathUtil
import xyz.muggr.newsly.Views.ArticleCard


class CardSwipeManager(private val activity: SwipableCards, private val swipableCard: ArticleCard, private val transitionCard: ArticleCard) : View.OnTouchListener {

    private val SCREEN_SIZE: IntArray
    private val DP_1: Int
    private var FINAL_FLING_TRANSLATION: Int = 0
    private val MINIMUM_DRAG_DISTANCE: Int
    private val MINIMUM_FLING_VELOCITY: Int
    private val MAXIMUM_FLING_VELOCITY: Int
    private val FINAL_ROTATION = 26f
    private val TRANSITION_CARD_SCALE = 0.95f

    private var touchState = State.NONE
    private var flingState = State.NONE
    private val lastTouchCoords = arrayOf(floatArrayOf(0f, 0f), floatArrayOf(0f, 0f))
    private var velocityTracker: VelocityTracker? = null

    init {
        SCREEN_SIZE = (activity as NewslyActivity).SCREEN_SIZE
        DP_1 = activity.DP_1
        MINIMUM_DRAG_DISTANCE = SCREEN_SIZE[0] / 3
        MINIMUM_FLING_VELOCITY = SCREEN_SIZE[0] * 2
        MAXIMUM_FLING_VELOCITY = ViewConfiguration.get(activity as Context).scaledMaximumFlingVelocity
        swipableCard.post { FINAL_FLING_TRANSLATION = (swipableCard.left + swipableCard.right + MathUtil.pythagoras(swipableCard.width.toFloat(), swipableCard.height.toFloat())).toInt() / 2 }

        // ADD ON TOUCH LISTENER
        swipableCard.setOnTouchListener(this)

        // SET TRANSITION CARD SCALE
        transitionCard.scaleX = TRANSITION_CARD_SCALE
        transitionCard.scaleY = TRANSITION_CARD_SCALE
    }

    //region GESTURE METHODS
    //=======================================================================================

    fun scroll(motionEvent: MotionEvent) {

        // SCROLL AND ROTATE SWIPABLE CARD
        swipableCard.translationX = swipableCard.translationX + motionEvent.rawX - lastTouchCoords[0][0]
        swipableCard.translationY = swipableCard.translationY + motionEvent.rawY - lastTouchCoords[0][1]
        swipableCard.rotation = swipableCard.translationX / SCREEN_SIZE[0] * FINAL_ROTATION * if (touchState == State.TOP) 1f else -1f

        // SET TRANSITION CARD SCALE
        val completion = Math.min(Math.abs(swipableCard.translationX) / SCREEN_SIZE[0], 1f)
        transitionCard.scaleX = TRANSITION_CARD_SCALE + (1f - TRANSITION_CARD_SCALE) * completion
        transitionCard.scaleY = TRANSITION_CARD_SCALE + (1f - TRANSITION_CARD_SCALE) * completion

    }

    fun fling(state: Int, velocityX: Float, velocityY: Float) {
        var velocityX = velocityX
        var velocityY = velocityY

        // CHECK IF FLINGING FROM REST (IF TRUE, POSITION Y WILL DETERMINE VELOCITY Y)
        val flingFromRest = velocityX == 0f && velocityY == 0f

        // GET RESOURCES
        val finalFlingTranslation = (FINAL_FLING_TRANSLATION * if (state == State.LEFT) -1 else 1).toFloat()

        // SET MINIMUM VELOCITY
        if (state == State.LEFT && velocityX > MINIMUM_FLING_VELOCITY * -1 || state == State.RIGHT && velocityX < MINIMUM_FLING_VELOCITY)
            velocityX = (MINIMUM_FLING_VELOCITY * if (state == State.LEFT) -1 else 1).toFloat()

        // GET DURATION
        val flingDuration = Math.abs((finalFlingTranslation - swipableCard.translationX) * 1000 / velocityX).toLong()

        // SETUP ANIMATION
        val flingAnim = swipableCard.animate()
                .translationX(finalFlingTranslation)
                .rotation(FINAL_ROTATION * (if (state == State.LEFT) -1 else 1).toFloat() * (if (touchState == State.TOP) 1 else -1).toFloat())
                .setDuration(flingDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        resetCards()
                        activity.onSwipe(flingState)
                        touchState = State.NONE
                        flingState = State.NONE
                    }
                })

        // ADD VERTICAL VELOCITY
        if (flingFromRest)
            velocityY = swipableCard.translationY * Math.abs(velocityX / swipableCard.translationX)
        if (velocityY != 0f)
            flingAnim.translationY(velocityY * flingDuration / 1000)

        // SETUP TRANSITION CARD ANIM
        transitionCard.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(flingDuration).interpolator = LinearOutSlowInInterpolator()

    }

    //=======================================================================================
    //endregion

    //region ON TOUCH METHODS
    //=======================================================================================

    override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {

        // GET VELOCITY TRACKER
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.addMovement(motionEvent)

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN ->
                // SET TOUCH STATE BASED ON WHETHER USER TOUCHES THE
                // TOP OR BOTTOM OF CARD
                if (touchState == State.NONE && flingState == State.NONE && view != null) {
                    if (motionEvent.rawY - view.top < view.height / 2 + 32 * DP_1)
                        touchState = State.TOP
                    else
                        touchState = State.BOTTOM
                }
            MotionEvent.ACTION_MOVE -> if (touchState != State.NONE)
                scroll(motionEvent)
            MotionEvent.ACTION_UP -> {

                // IF TRANSLATION IS ZERO, A CLICK WAS PERFORMED
                if (touchState != State.NONE && swipableCard.translationX == 0f && swipableCard.translationY == 0f) {
                    swipableCard.onClick()
                } else {

                    // CALCULATE VELOCITIES
                    velocityTracker!!.computeCurrentVelocity(1000, MAXIMUM_FLING_VELOCITY.toFloat())
                    val velocityX = velocityTracker!!.xVelocity
                    val velocityY = velocityTracker!!.yVelocity

                    // CHECK FOR TOUCH STATE, MINIMUM FLING VELOCITY
                    // AND FLING VELOCITY MATCHES TRANSLATION X DIRECTION
                    if (touchState != State.NONE && MathUtil.pythagoras(velocityX, velocityY) > MINIMUM_FLING_VELOCITY
                            && swipableCard.translationX / velocityX >= 0) {

                        // SET NEW FLING STATE
                        flingState = if (velocityX < 0) State.LEFT else State.RIGHT

                        // START FLING
                        fling(flingState, velocityX, velocityY)

                    } else if (Math.abs(swipableCard.translationX) >= MINIMUM_DRAG_DISTANCE) {

                        // START FLING IF TRANSLATION MORE THAN THRESHOLD
                        flingState = if (swipableCard.translationX < 0) State.LEFT else State.RIGHT
                        fling(flingState, 0f, 0f)
                    } else {

                        // RETURN CARD TO ORIGINAL POSITION
                        swipableCard.animate()
                                .translationX(0f)
                                .translationY(0f)
                                .rotation(0f)
                                .setDuration(300)
                                .setInterpolator(OvershootInterpolator(1.5f))
                                .setListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator) {
                                        super.onAnimationEnd(animation)
                                        touchState = State.NONE
                                    }
                                })
                    }

                    // RECYCLE VELOCITY TRACKER
                    velocityTracker!!.recycle()
                    velocityTracker = null
                }
            }
        }

        // SAVE THE LAST TWO MOTION EVENTS
        lastTouchCoords[1][0] = lastTouchCoords[0][0]
        lastTouchCoords[1][1] = lastTouchCoords[0][1]
        lastTouchCoords[0][0] = motionEvent.rawX
        lastTouchCoords[0][1] = motionEvent.rawY

        return view == null
    }

    //=======================================================================================
    //endregion

    //region CARD TRANSITION METHODS
    //=======================================================================================

    private fun resetCards() {
        swipableCard.translationX = 0f
        swipableCard.translationY = 0f
        swipableCard.rotation = 0f
        transitionCard.scaleX = TRANSITION_CARD_SCALE
        transitionCard.scaleY = TRANSITION_CARD_SCALE
    }

    //=======================================================================================
    //endregion

    //region INTERFACES
    //=======================================================================================

    interface SwipableCards {
        fun onSwipe(state: Int)

        companion object {
            val DP_1 = 3
        }
    }

    //=======================================================================================
    //endregion

    //region KEYS
    //=======================================================================================

    object State {
        val NONE = 0x0000
        val TOP = 0x0001
        val RIGHT = 0x0010
        val BOTTOM = 0x0100
        val LEFT = 0x1000
    }

    //=======================================================================================
    //endregion
}
