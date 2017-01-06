package xyz.muggr.newsly;

import org.junit.Test;

import java.io.IOException;

import xyz.muggr.newsly.Articles.Article;
import xyz.muggr.newsly.Managers.ApiManager;


public class MainUnitTest {
    @Test
    public void getArticles() {
        try {
            for (Article article : ApiManager.getRedditArticles()) {
                System.out.print(article.getuId() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUuid() {
        try {
            System.out.print(ApiManager.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}