package xyz.muggr.newsly.Views;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import xyz.muggr.newsly.ArticleActivity;
import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.NewslyActivity;
import xyz.muggr.newsly.R;
import xyz.muggr.newsly.Utils.MathUtil;
import xyz.muggr.newsly.Utils.TransitionUtil;

public class ArticleCard extends FrameLayout {

    private int[] SCREEN_SIZE;
    private int DP_1;

    private ImageView heroIv;
    private TextView headlineTv;
    private TextView tagTv;
    private TextView domainTv;
    private TextView flagTv;
    private TextView timeTv;

    private Article currentArticle;

    //region Constructor
    //=======================================================================================

    public ArticleCard(Context context) {
        super(context);
        if (!isInEditMode())
            init();
    }

    public ArticleCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init();
    }

    public ArticleCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init();
    }

    //=======================================================================================
    //endregion

    //region Init
    //=======================================================================================

    private void init() {

        // Get resources
        final NewslyActivity activity = (NewslyActivity) getContext();
        SCREEN_SIZE = activity.SCREEN_SIZE;
        DP_1 = activity.DP_1;

        // Set params
        final int SHADOW_RADIUS = 10 * DP_1;
        final int PADDING_HORIZONTAL = 16 * DP_1;
        final int PADDING_BOTTOM = 16 * DP_1;
        final int PADDING_TOP = SHADOW_RADIUS - (PADDING_BOTTOM - SHADOW_RADIUS);
        final float CARD_RADIUS = activity.getResources().getDimension(R.dimen.radius_card_product);

        // Setup product card
        inflate(activity, R.layout.vie_card_article, this);
        setPadding(PADDING_HORIZONTAL, PADDING_TOP, PADDING_HORIZONTAL, PADDING_BOTTOM);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // Get views
        heroIv = (ImageView) findViewById(R.id.vie_card_article_hero_iv);
        headlineTv = (TextView) findViewById(R.id.vie_card_article_title_tv);
        tagTv = (TextView) findViewById(R.id.vie_card_article_tag_tv);
        domainTv = (TextView) findViewById(R.id.vie_card_article_domain_tv);
        flagTv = (TextView) findViewById(R.id.vie_card_article_flag_tv);
        timeTv = (TextView) findViewById(R.id.vie_card_article_time_tv);

        // Set filter
        heroIv.setColorFilter(0x33000000);

        // Setup background
        setBackground(new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {

                // GET CANVAS BOUNDS
                Rect bounds = new Rect();
                canvas.getClipBounds(bounds);

                // SET PAINT
                paint.setColor(ContextCompat.getColor(activity, R.color.product_card_background));
                paint.setShadowLayer(SHADOW_RADIUS, 0, PADDING_BOTTOM - SHADOW_RADIUS, 0x22000000);

                // DRAW ROUNDED BACKGROUND
                canvas.drawRoundRect(
                        bounds.left + PADDING_HORIZONTAL,
                        bounds.top + PADDING_TOP,
                        bounds.right - PADDING_HORIZONTAL,
                        bounds.bottom - PADDING_BOTTOM,
                        CARD_RADIUS, CARD_RADIUS, paint);
            }
        }));

    }

    //=======================================================================================
    //endregion

    //region Setters
    //=======================================================================================

    public void setArticle(Article article) {
        this.currentArticle = article;

        // Set content
        headlineTv.setText(article.getHeadline());
        domainTv.setText(article.getDomain());

        // Set image
        Picasso.with(getContext()).load(article.getHeroImageUrl()).into(heroIv);

        // Set tag
        if (article.getTag() != null) {
            tagTv.setVisibility(View.VISIBLE);
            tagTv.setText(article.getTag());
        } else {
            tagTv.setVisibility(View.GONE);
        }

        // Set time
        timeTv.setText(MathUtil.getHumanTime(article.getTimePosted() * 1000));

        // Set flags
        if (article.getFlags() > 0) {
            flagTv.setVisibility(View.VISIBLE);
            if ((article.getFlags() & Article.Flag.IS_NSFW) == Article.Flag.IS_NSFW) {
                flagTv.setText("nsfw");
            } else if ((article.getFlags() & Article.Flag.IS_TOP_NEWS) == Article.Flag.IS_TOP_NEWS) {
                flagTv.setText("top news");
            }
        } else
            flagTv.setVisibility(View.GONE);
    }

    //=======================================================================================
    //endregion

    //region Interaction methods
    //=======================================================================================

    public void onClick() {
        // Get resources
        NewslyActivity activity = (NewslyActivity) getContext();

        // Create new activity intent
        Intent activityIntent = new Intent(activity, ArticleActivity.class);

        // Pass article into intent
        activityIntent.putExtra("currentArticle", currentArticle);

        // Setup transition bundle
        Bundle transitionBundle =
                ActivityOptions.makeSceneTransitionAnimation(activity,
                        new Pair<View, String>(heroIv, TransitionUtil.heroIvTransition),
                        new Pair<>(activity.getNavbar(), TransitionUtil.navbarTransition)
                ).toBundle();

        // Start activity
        activity.startActivity(activityIntent, transitionBundle);
    }

    //=======================================================================================
    //endregion

}
