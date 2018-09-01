package xyz.muggr.newsly;

import org.junit.Test;

import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.Managers.ApiManager;


public class MainUnitTest {
    @Test
    public void getArticles() {
        try {
            for (Article article : ApiManager.INSTANCE.getRedditArticles()) {
                System.out.print(article.getRedditId() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUuid() {
        try {
            System.out.print(ApiManager.INSTANCE.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}