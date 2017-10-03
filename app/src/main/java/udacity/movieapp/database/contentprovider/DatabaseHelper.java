package udacity.movieapp.database.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 2/9/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    final String CREATE_MOVIE_TABLE =
            " CREATE TABLE " + BuildConfig.TABLE_MOVIE +
                    " (" + BuildConfig.TABLE_MOVIE_ID + " TEXT PRIMARY KEY, "
                    + BuildConfig.TABLE_MOVIE_TITLE + " TEXT, "
                    + BuildConfig.TABLE_MOVIE_BACKDROP_PATH + " TEXT, "
                    + BuildConfig.TABLE_MOVIE_IS_FAVOURITE + " INTEGER, "
                    + BuildConfig.TABLE_MOVIE_OVERVIEW + " TEXT, "
                    + BuildConfig.TABLE_MOVIE_RELEASE_DATE + " TEXT, "
                    + BuildConfig.TABLE_MOVIE_VOTE_AVERAGE + " REAL, "
                    + BuildConfig.TABLE_MOVIE_POSTER_PATH + " TEXT);";
    final String CREATE_REVIEW_TABLE =
            " CREATE TABLE " + BuildConfig.TABLE_REVIEW +
                    " (" + BuildConfig.TABLE_REVIEW_ID + " TEXT PRIMARY KEY, "
                    + BuildConfig.TABLE_REVIEW_AUTHOR + " TEXT, "
                    + BuildConfig.TABLE_COMMAN_ID + " TEXT, "
                    + BuildConfig.TABLE_REVIEW_CONTENT + " TEXT);";
    final String CREATE_TRAILER_TABLE =
            " CREATE TABLE " + BuildConfig.TABLE_TRAILER +
                    " (" + BuildConfig.TABLE_TRAILER_ID + " TEXT PRIMARY KEY, "
                    + BuildConfig.TABLE_TRAILER_KEY + " TEXT, "
                    + BuildConfig.TABLE_COMMAN_ID + " TEXT, "
                    + BuildConfig.TABLE_TRAILER_NAME + " TEXT);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */

    DatabaseHelper(Context context) {
        super(context, BuildConfig.DATABASE_NAME, null, BuildConfig.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);
        db.execSQL(CREATE_REVIEW_TABLE);
        db.execSQL(CREATE_TRAILER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BuildConfig.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + BuildConfig.TABLE_REVIEW);
        db.execSQL("DROP TABLE IF EXISTS " + BuildConfig.TABLE_TRAILER);
        onCreate(db);
    }
}