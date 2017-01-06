package xyz.muggr.newsly.Managers;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import xyz.muggr.newsly.Articles.ArticleList;
import xyz.muggr.newsly.BuildConfig;
import xyz.muggr.newsly.NewslyActivity;

public class ArticleQueueManager {

    private ArticleList articleQueue;

    private NewslyActivity activity;
    private final SQLiteDatabase database;

    //region Constructors
    //=======================================================================================

    public ArticleQueueManager(NewslyActivity activity) {
        this.activity = activity;
        database = DatabaseManager.getInstance(activity).getWritableDatabase();
        articleQueue = new ArticleList();
    }

    //=======================================================================================
    //endregion

    //region Article queue modifiers
    //=======================================================================================

    public void load(final ArticleQueueListener listener) {

        if (articleQueue.size() < 10)
            new AsyncTask<Void, Void, ArticleList>() {
                @Override
                protected ArticleList doInBackground(Void... params) {
                    try {
                        return ApiManager.getArticles();
                    } catch (Exception e) {
                        if (BuildConfig.DEBUG)
                            e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(ArticleList articleList) {
                    super.onPostExecute(articleList);
                    if (articleList != null) {
                        articleQueue.addAll(articleList);
                    }
                    listener.onArticleQueueLoaded(articleQueue);

                    if (BuildConfig.DEBUG)
                        for (int i = 0; i < articleQueue.size(); i++) {
                            Log.d(
                                    String.format("Queue %02d", i),
                                    articleQueue.get(i).getHeadline()
                            );
                        }
                }

            }.execute();
        else
            listener.onArticleQueueLoaded(articleQueue);

    }

    public void dismiss(int state) {
        if (!articleQueue.isEmpty()) {
            articleQueue.remove(0);
        }
    }

    //=======================================================================================
    //endregion

    //region Interfaces
    //=======================================================================================

    public interface ArticleQueueListener {
        void onArticleQueueLoaded(ArticleList articleQueue);
    }

    //=======================================================================================
    //endregion

}
