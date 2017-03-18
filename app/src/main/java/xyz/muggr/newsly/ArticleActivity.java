package xyz.muggr.newsly;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import xyz.muggr.newsly.Adapters.ArticleAdapter;
import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.Utils.TransitionUtil;

public class ArticleActivity extends NewslyActivity implements AppBarLayout.OnOffsetChangedListener {

    private Article currentArticle;
    private View header;
    private ImageView articleTopImageIv;
    private View navBkg;
    private AppBarLayout appBarLayout;
    private TextView redditTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_article);

        // Get resources
        currentArticle = getIntent().getParcelableExtra("currentArticle");

        // Set screen
        setLightStatusBar();

        // Setup RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.act_article_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ArticleAdapter());

        // Get views
        header = findViewById(R.id.act_article_header);
        articleTopImageIv = (ImageView) findViewById(R.id.act_article_hero_iv);
        ImageView redditTitleBkg = (ImageView) findViewById(R.id.act_article_headline_bkg);
        redditTitleTv = (TextView) findViewById(R.id.act_article_headline_tv);
        ImageView redditFlairBkg = (ImageView) findViewById(R.id.act_article_flair_bkg);
        TextView redditFlairTv = (TextView) findViewById(R.id.act_article_flair_tv);
        appBarLayout = (AppBarLayout) findViewById(R.id.act_article_appbarlayout);
        navBkg = findViewById(R.id.nav_bkg);

        // Set hero image
        articleTopImageIv.setColorFilter(0x33000000);
        Picasso.with(this).load(currentArticle.getArticleTopImage()).into(articleTopImageIv);

        // Set headline
        redditTitleTv.setText(currentArticle.getRedditTitle());
        redditTitleTv.getLayoutParams().width = getIntent().getIntExtra("headlineTvWidth", ConstraintLayout.LayoutParams.MATCH_PARENT);
        redditTitleBkg.getLayoutParams().height = getIntent().getIntExtra("headlineTvHeight", 0);

        // Set flair
        if (currentArticle.getRedditFlair() != null) {
            redditFlairBkg.setVisibility(View.VISIBLE);
            redditFlairTv.setVisibility(View.VISIBLE);
            redditFlairTv.setText(currentArticle.getRedditFlair());
        }

        // Setup transitions
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(R.id.nav_bkg, true);
        getWindow().setEnterTransition(fade);

        // Set transition names
        ViewCompat.setTransitionName(articleTopImageIv, TransitionUtil.heroIvTransition);
        ViewCompat.setTransitionName(redditTitleBkg, TransitionUtil.headlineBkgTransition);
        ViewCompat.setTransitionName(redditTitleTv, TransitionUtil.headlineTvTransition);
        ViewCompat.setTransitionName(redditFlairBkg, TransitionUtil.flairBkgTransition);
        ViewCompat.setTransitionName(redditFlairTv, TransitionUtil.tagTvTransition);
        ViewCompat.setTransitionName(getNavbar(), TransitionUtil.navbarTransition);

        // Set scrolling animation
        appBarLayout.addOnOffsetChangedListener(this);
    }


    //region Listener methods
    //=======================================================================================

    int appBarHeight = 0;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        // Get total height
        if (appBarHeight == 0) appBarHeight = appBarLayout.getHeight();

        // Get percentage visibility
        float offset = (float) verticalOffset / (float) appBarHeight * -1;

        // Animate header
        header.setAlpha(1 - offset);
        articleTopImageIv.setTranslationY(appBarHeight * offset * 0.5f);
        redditTitleTv.setTranslationY(appBarHeight * offset * -0.2f);
        navBkg.setAlpha(0.2f + 0.5f * offset);
    }

    //=======================================================================================
    //endregion

}
