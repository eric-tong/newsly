package xyz.muggr.newsly.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.NewslyActivity;
import xyz.muggr.newsly.R;

public class ArticleCard extends FrameLayout {

    private int[] SCREEN_SIZE;
    private int DP_1;

    private ImageView primaryIv;
    private TextView titleTv;

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

        // GET RESOURCES
        final NewslyActivity activity = (NewslyActivity) getContext();
        SCREEN_SIZE = activity.SCREEN_SIZE;
        DP_1 = activity.DP_1;

        // SET PARAMS
        final int SHADOW_RADIUS = 10 * DP_1;
        final int PADDING_HORIZONTAL = 16 * DP_1;
        final int PADDING_BOTTOM = 16 * DP_1;
        final int PADDING_TOP = SHADOW_RADIUS - (PADDING_BOTTOM - SHADOW_RADIUS);
        final float CARD_RADIUS = activity.getResources().getDimension(R.dimen.radius_card_product);

        // SETUP PRODUCT CARD
        inflate(activity, R.layout.vie_card_article, this);
        setPadding(PADDING_HORIZONTAL, PADDING_TOP, PADDING_HORIZONTAL, PADDING_BOTTOM);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // GET VIEWS
        titleTv = (TextView) findViewById(R.id.vie_card_product_title_tv);

        // SETUP BACKGROUND
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

        titleTv.setText(article.getHeadline());
    }

    //=======================================================================================
    //endregion

}
