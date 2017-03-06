package xyz.muggr.newsly;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import xyz.muggr.newsly.Articles.Article;

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
    }
}
