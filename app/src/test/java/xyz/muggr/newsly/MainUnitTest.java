package xyz.muggr.newsly;

import org.junit.Test;

import java.io.IOException;

import xyz.muggr.newsly.Managers.RedditManager;


public class MainUnitTest {
    @Test
    public void getArticles() {
        try {
            System.out.print(RedditManager.getArticles());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}