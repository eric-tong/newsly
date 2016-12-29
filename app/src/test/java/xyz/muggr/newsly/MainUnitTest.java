package xyz.muggr.newsly;

import org.junit.Test;

import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.Managers.ApiManager;


public class MainUnitTest {
    @Test
    public void getArticles() {
        try {
            for (Article article : ApiManager.getArticles()) {
                System.out.print(article.getCreated() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}