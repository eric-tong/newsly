package xyz.muggr.newsly;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.transition.Transition;
import android.widget.ImageView;

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

        // Set hero image
        Picasso.with(this).load(currentArticle.getHeroImageUrl()).into(heroIv);

        // Setup transition
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(R.id.nav_bg, true);
        getWindow().setEnterTransition(fade);
        ViewCompat.setTransitionName(heroIv, TransitionUtil.heroIvTransition);
        ViewCompat.setTransitionName(getNavbar(), TransitionUtil.navbarTransition);
    }
}
