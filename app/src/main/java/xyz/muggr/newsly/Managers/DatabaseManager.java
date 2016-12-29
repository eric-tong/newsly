package xyz.muggr.newsly.Managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsly_database.db";
    private static DatabaseManager instance;

    //region Constructors
    //=======================================================================================

    private DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context.getApplicationContext());
        }
        return instance;
    }

    //=======================================================================================
    //endregion

    //region Database methods
    //=======================================================================================

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format(
                "CREATE TABLE IF NOT EXISTS %1$s (Created INTEGER, UNIQUE(Created) ON CONFLICT IGNORE);",
                Table.DISMISSED_ARTICLES
        ));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop all tables and recreate tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.DISMISSED_ARTICLES);
        onCreate(sqLiteDatabase);
    }


    //=======================================================================================
    //endregion

    //region keys
    //=======================================================================================

    public static final class Table {
        public static final String DISMISSED_ARTICLES = "DISMISSED_ARTICLES";
    }

    //=======================================================================================
    //endregion

}
