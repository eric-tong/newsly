package xyz.muggr.newsly;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.transition.Transition;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.Utils.TransitionUtil;

public class ArticleActivity extends NewslyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_article);

        // Get resources
        Article currentArticle = getIntent().getParcelableExtra("currentArticle");

        // Set screen
        setLightStatusBar();

        // Get views
        ImageView heroIv = (ImageView) findViewById(R.id.act_article_hero_iv);
        ImageView headlineBkg = (ImageView) findViewById(R.id.act_article_headline_bkg);
        TextView headlineTv = (TextView) findViewById(R.id.act_article_headline_tv);

        // Set hero image
        heroIv.setColorFilter(0x33000000);
        Picasso.with(this).load(currentArticle.getHeroImageUrl()).into(heroIv);

        // Set headline
        headlineTv.setText(currentArticle.getHeadline());
        headlineTv.getLayoutParams().width = getIntent().getIntExtra("headlineTvWidth", ConstraintLayout.LayoutParams.MATCH_PARENT);

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
        ViewCompat.setTransitionName(getNavbar(), TransitionUtil.navbarTransition);
    }
}
