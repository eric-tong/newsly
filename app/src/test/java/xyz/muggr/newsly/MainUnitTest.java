package xyz.muggr.newsly;

import org.junit.Test;

import xyz.muggr.newsly.Articles.RedditArticle;
import xyz.muggr.newsly.Managers.ApiManager;


public class MainUnitTest {
    @Test
    public void getArticles() {
        try {
            for (RedditArticle article : ApiManager.getRedditArticles()) {
                System.out.print(article.getRedditId() + "\n");
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