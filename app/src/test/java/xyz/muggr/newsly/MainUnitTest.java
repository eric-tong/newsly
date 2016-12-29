package xyz.muggr.newsly;

import org.junit.Test;

import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.Managers.RedditManager;


public class MainUnitTest {
    @Test
    public void getArticles() {
        try {
            for (Article article : RedditManager.getArticles())
                System.out.print(article.getFlair() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}