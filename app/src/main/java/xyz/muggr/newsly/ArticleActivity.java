package xyz.muggr.newsly;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

public class ArticleActivity extends NewslyActivity {

    private Article currentArticle;

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
        ImageView heroIv = (ImageView) findViewById(R.id.act_article_hero_iv);
        ImageView headlineBkg = (ImageView) findViewById(R.id.act_article_headline_bkg);
        TextView headlineTv = (TextView) findViewById(R.id.act_article_headline_tv);
        ImageView flairBkg = (ImageView) findViewById(R.id.act_article_flair_bkg);
        TextView flairTv = (TextView) findViewById(R.id.act_article_flair_tv);

        // Set hero image
        heroIv.setColorFilter(0x33000000);
        Picasso.with(this).load(currentArticle.getArticleTopImage()).into(heroIv);

        // Set headline
        headlineTv.setText(currentArticle.getRedditTitle());
        headlineTv.getLayoutParams().width = getIntent().getIntExtra("headlineTvWidth", ConstraintLayout.LayoutParams.MATCH_PARENT);
        headlineBkg.getLayoutParams().height = getIntent().getIntExtra("headlineTvHeight", 0);

        // Set flair
        if (currentArticle.getRedditFlair() != null) {
            flairBkg.setVisibility(View.VISIBLE);
            flairTv.setVisibility(View.VISIBLE);
            flairTv.setText(currentArticle.getRedditFlair());
        }

        // Setup transitions
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(R.id.nav_bg, true);
        getWindow().setEnterTransition(fade);

        // Set transition names
        ViewCompat.setTransitionName(heroIv, TransitionUtil.heroIvTransition);
        ViewCompat.setTransitionName(headlineBkg, TransitionUtil.headlineBkgTransition);
        ViewCompat.setTransitionName(headlineTv, TransitionUtil.headlineTvTransition);
        ViewCompat.setTransitionName(flairBkg, TransitionUtil.flairBkgTransition);
        ViewCompat.setTransitionName(flairTv, TransitionUtil.tagTvTransition);
        ViewCompat.setTransitionName(getNavbar(), TransitionUtil.navbarTransition);
    }

}
