package udacity.movieapp.database.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 2/9/2017.
 */

public class MyProvider extends ContentProvider {
    static String MOVIE_URL = BuildConfig.PROVIDER_SCHEMA + BuildConfig.PROVIDER_NAME + "/movies";
    static String REVIEW_URL = BuildConfig.PROVIDER_SCHEMA + BuildConfig.PROVIDER_NAME + "/reviews";
    static String TRAILER_URL = BuildConfig.PROVIDER_SCHEMA + BuildConfig.PROVIDER_NAME + "/trailers";
    public static final Uri CONTENT_URI_MOVIE = Uri.parse(MOVIE_URL);
    public static final Uri CONTENT_URI_REVIEW = Uri.parse(REVIEW_URL);
    public static final Uri CONTENT_URI_TRAILER = Uri.parse(TRAILER_URL);
    static final UriMatcher uriMatcher;
    private static MyProvider instance;

    public MyProvider() {
    }

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, BuildConfig.PROVIDER_MOVIES_type, BuildConfig.PROVIDER_MOVIES);
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, BuildConfig.PROVIDER_MOVIE_type, BuildConfig.PROVIDER_MOVIE_ID);
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, BuildConfig.PROVIDER_REVIEWS_type, BuildConfig.PROVIDER_REVIEWS);
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, BuildConfig.PROVIDER_REVIEW_type, BuildConfig.PROVIDER_REVIEW_ID);
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, BuildConfig.PROVIDER_TRAILERS_type, BuildConfig.PROVIDER_TRAILERS);
        uriMatcher.addURI(BuildConfig.PROVIDER_NAME, BuildConfig.PROVIDER_TRAILER_type, BuildConfig.PROVIDER_TRAILER_ID);
    }
    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID;
        Uri CONTENT_URI;
        switch (uriMatcher.match(uri)) {
            case BuildConfig.PROVIDER_MOVIES:
                rowID = db.insert(BuildConfig.TABLE_MOVIE, "", values);
                CONTENT_URI = CONTENT_URI_MOVIE;
                break;
            case BuildConfig.PROVIDER_REVIEWS:
                rowID = db.insert(BuildConfig.TABLE_REVIEW, "", values);
                CONTENT_URI = CONTENT_URI_REVIEW;
                break;
            case BuildConfig.PROVIDER_TRAILERS:
                rowID = db.insert(BuildConfig.TABLE_TRAILER, "", values);
                CONTENT_URI = CONTENT_URI_TRAILER;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        long rowID = 0;
        int count = 0;
        for (int i = 0; i < values.length; i++) {
            Uri CONTENT_URI;
            switch (uriMatcher.match(uri)) {
                case BuildConfig.PROVIDER_MOVIES:
                    rowID = db.insert(BuildConfig.TABLE_MOVIE, "", values[i]);
                    CONTENT_URI = CONTENT_URI_MOVIE;
                    break;
                case BuildConfig.PROVIDER_REVIEWS:
                    rowID = db.insert(BuildConfig.TABLE_REVIEW, "", values[i]);
                    CONTENT_URI = CONTENT_URI_REVIEW;
                    break;
                case BuildConfig.PROVIDER_TRAILERS:
                    rowID = db.insert(BuildConfig.TABLE_TRAILER, "", values[i]);
                    CONTENT_URI = CONTENT_URI_TRAILER;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }

            /**
             * If record is added successfully
             */
            if (rowID > 0) {
                Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                count++;
            } else
                throw new SQLException("Failed to add a record into " + uri);
        }
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


        switch (uriMatcher.match(uri)) {
            case BuildConfig.PROVIDER_MOVIES:
                qb.setTables(BuildConfig.TABLE_MOVIE);
                break;

            case BuildConfig.PROVIDER_MOVIE_ID:
                qb.setTables(BuildConfig.TABLE_MOVIE);
                qb.appendWhere(BuildConfig.TABLE_MOVIE_ID + "=" + uri.getPathSegments().get(1));
                break;
            case BuildConfig.PROVIDER_REVIEWS:
                qb.setTables(BuildConfig.TABLE_REVIEW);
                break;

            case BuildConfig.PROVIDER_REVIEW_ID:
                qb.setTables(BuildConfig.TABLE_REVIEW);
                qb.appendWhere(BuildConfig.TABLE_REVIEW_ID + "=" + uri.getPathSegments().get(1));
                break;
            case BuildConfig.PROVIDER_TRAILERS:
                qb.setTables(BuildConfig.TABLE_TRAILER);
                break;

            case BuildConfig.PROVIDER_TRAILER_ID:
                qb.setTables(BuildConfig.TABLE_TRAILER);
                qb.appendWhere(BuildConfig.TABLE_TRAILER_ID + "=" + uri.getPathSegments().get(1));
                break;
        }

        if (sortOrder == null || sortOrder == "") {
            /**
             * By default sort on student names
             */
            //        sortOrder = NAME;
        }

        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;


        switch (uriMatcher.match(uri)) {
            case BuildConfig.PROVIDER_MOVIES:
                count = db.delete(BuildConfig.TABLE_MOVIE, selection, selectionArgs);
                break;

            case BuildConfig.PROVIDER_MOVIE_ID:
                String movieId = uri.getPathSegments().get(1);
                count = db.delete(BuildConfig.TABLE_MOVIE, BuildConfig.TABLE_MOVIE_ID + " = " + movieId +
                        (!TextUtils.isEmpty(selection) ? " AND(" + selection + ')' : ""), selectionArgs);
            case BuildConfig.PROVIDER_REVIEWS:
                count = db.delete(BuildConfig.TABLE_REVIEW, selection, selectionArgs);
                break;

            case BuildConfig.PROVIDER_REVIEW_ID:
                String reviewId = uri.getPathSegments().get(1);
                count = db.delete(BuildConfig.TABLE_MOVIE, BuildConfig.MOVIE_ID + " = " + reviewId +
                        (!TextUtils.isEmpty(selection) ? " AND(" + selection + ')' : ""), selectionArgs);
                break;
            case BuildConfig.PROVIDER_TRAILERS:
                count = db.delete(BuildConfig.TABLE_TRAILER, selection, selectionArgs);
                break;

            case BuildConfig.PROVIDER_TRAILER_ID:
                String trailerId = uri.getPathSegments().get(1);
                count = db.delete(BuildConfig.TABLE_MOVIE, BuildConfig.MOVIE_ID + " = " + trailerId +
                        (!TextUtils.isEmpty(selection) ? " AND(" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BuildConfig.PROVIDER_MOVIES:
                return "vnd.android.cursor.dir/vnd.example.movies";
            case BuildConfig.PROVIDER_MOVIE_ID:
                return "vnd.android.cursor.item/vnd.example.movies";
            case BuildConfig.PROVIDER_REVIEWS:
                return "vnd.android.cursor.dir/vnd.example.reviews";
            case BuildConfig.PROVIDER_REVIEW_ID:
                return "vnd.android.cursor.item/vnd.example.reviews";
            case BuildConfig.PROVIDER_TRAILERS:
                return "vnd.android.cursor.dir/vnd.example.trailers";
            case BuildConfig.PROVIDER_TRAILER_ID:
                return "vnd.android.cursor.item/vnd.example.trailers";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

}