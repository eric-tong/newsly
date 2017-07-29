package xyz.muggr.newsly.Views

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Pair
import android.view.View
import android.widget.FrameLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.vie_card_article.view.*
import xyz.muggr.newsly.ArticleActivity
import xyz.muggr.newsly.Articles.Article
import xyz.muggr.newsly.NewslyActivity
import xyz.muggr.newsly.R
import xyz.muggr.newsly.Utils.MathUtil
import xyz.muggr.newsly.Utils.TransitionUtil
import java.util.*

class ArticleCard : FrameLayout {

    private lateinit var SCREEN_SIZE: IntArray
    private var DP_1: Int = 0

    private lateinit var currentArticle: Article

    //region Constructor
    //=======================================================================================

    constructor(context: Context) : super(context) {
        if (!isInEditMode)
            init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (!isInEditMode)
            init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!isInEditMode)
            init()
    }

    //=======================================================================================
    //endregion

    //region Init
    //=======================================================================================

    private fun init() {

        // Get resources
        val activity = context as NewslyActivity
        SCREEN_SIZE = activity.SCREEN_SIZE
        DP_1 = activity.DP_1

        // Set params
        val SHADOW_RADIUS = 10 * DP_1
        val PADDING_HORIZONTAL = 16 * DP_1
        val PADDING_BOTTOM = 16 * DP_1
        val PADDING_TOP = SHADOW_RADIUS - (PADDING_BOTTOM - SHADOW_RADIUS)
        val CARD_RADIUS = activity.resources.getDimension(R.dimen.radius_card_product)

        // Setup product card
        View.inflate(activity, R.layout.vie_card_article, this)
        setPadding(PADDING_HORIZONTAL, PADDING_TOP, PADDING_HORIZONTAL, PADDING_BOTTOM)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        // Set filter
        heroIv.setColorFilter(0x33000000)

        // Setup background
        background = ShapeDrawable(object : Shape() {
            override fun draw(canvas: Canvas, paint: Paint) {

                // GET CANVAS BOUNDS
                val bounds = Rect()
                canvas.getClipBounds(bounds)

                // SET PAINT
                paint.color = ContextCompat.getColor(activity, R.color.product_card_background)
                paint.setShadowLayer(SHADOW_RADIUS.toFloat(), 0f, (PADDING_BOTTOM - SHADOW_RADIUS).toFloat(), 0x22000000)

                // DRAW ROUNDED BACKGROUND
                canvas.drawRoundRect(
                        (bounds.left + PADDING_HORIZONTAL).toFloat(),
                        (bounds.top + PADDING_TOP).toFloat(),
                        (bounds.right - PADDING_HORIZONTAL).toFloat(),
                        (bounds.bottom - PADDING_BOTTOM).toFloat(),
                        CARD_RADIUS, CARD_RADIUS, paint)
            }
        })

    }

    //=======================================================================================
    //endregion

    //region Setters
    //=======================================================================================

    fun setArticle(article: Article) {
        this.currentArticle = article

        // Set content
        headlineTv.text = article.redditTitle
        domainTv.text = article.articleDomain

        // Set image
        headlineBkg.visibility = View.INVISIBLE
        Picasso.with(context).load(article.articleTopImage).into(heroIv, object : Callback {
            override fun onSuccess() {
                headlineBkg.visibility = View.VISIBLE
            }

            override fun onError() {

            }
        })

        // Set tag
        if (currentArticle.getRedditFlair() != null) {
            flairBkg.visibility = View.VISIBLE
            flairTv.visibility = View.VISIBLE
            flairTv.text = currentArticle.redditFlair
        } else {
            flairBkg.visibility = View.INVISIBLE
            flairTv.visibility = View.INVISIBLE
        }

        // Set time
        timeTv.text = MathUtil.getHumanTime(article.redditCreated * 1000)

        //        // Set flags
        //        if (article.get() > 0) {
        //            flagTv.setVisibility(View.VISIBLE);
        //            if ((article.getFlags() & Article.Flag.IS_NSFW) == Article.Flag.IS_NSFW) {
        //                flagTv.setText("nsfw");
        //            } else if ((article.getFlags() & Article.Flag.IS_TOP_NEWS) == Article.Flag.IS_TOP_NEWS) {
        //                flagTv.setText("top news");
        //            }
        //        } else
        //            flagTv.setVisibility(View.GONE);
    }

    //=======================================================================================
    //endregion

    //region Interaction methods
    //=======================================================================================

    fun onClick() {
        // Get resources
        val activity = context as NewslyActivity

        // Create new activity intent
        val activityIntent = Intent(activity, ArticleActivity::class.java)

        // Pass extras into intent
        activityIntent.putExtra("currentArticle", currentArticle)
        activityIntent.putExtra("headlineTvWidth", headlineTv.width)
        activityIntent.putExtra("headlineTvHeight", headlineTv.height)

        // Setup transition bundle
        val viewNameList = ArrayList<Pair<View, String>>()
        viewNameList.add(Pair<View, String>(heroIv, TransitionUtil.heroIvTransition))
        viewNameList.add(Pair<View, String>(headlineBkg, TransitionUtil.headlineBkgTransition))
        viewNameList.add(Pair<View, String>(headlineTv, TransitionUtil.headlineTvTransition))
        viewNameList.add(Pair(activity.navbar, TransitionUtil.navbarTransition))
        if (currentArticle.redditFlair != null) {
            viewNameList.add(Pair<View, String>(flairBkg, TransitionUtil.flairBkgTransition))
            viewNameList.add(Pair<View, String>(flairTv, TransitionUtil.tagTvTransition))
        }
        val array = viewNameList.toTypedArray()
        val transitionBundle = ActivityOptions.makeSceneTransitionAnimation(activity, *array).toBundle()

        // Start activity
        activity.startActivity(activityIntent, transitionBundle)
    }

    //=======================================================================================
    //endregion

}
