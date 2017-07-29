package xyz.muggr.newsly.Managers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    //region Database methods
    //=======================================================================================

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(String.format(
                "CREATE TABLE IF NOT EXISTS %1\$s (Created INTEGER, UNIQUE(Created) ON CONFLICT IGNORE);",
                Table.DISMISSED_ARTICLES
        ))
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        // Drop all tables and recreate tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.DISMISSED_ARTICLES)
        onCreate(sqLiteDatabase)
    }


    //=======================================================================================
    //endregion

    //region Keys
    //=======================================================================================

    object Table {
        val DISMISSED_ARTICLES = "DISMISSED_ARTICLES"
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "newsly_database.db"
        private var instance: DatabaseManager? = null

        @Synchronized fun getInstance(context: Context): DatabaseManager {
            if (instance == null) {
                instance = DatabaseManager(context.applicationContext)
            }
            return instance as DatabaseManager
        }
    }

    //=======================================================================================
    //endregion

}
