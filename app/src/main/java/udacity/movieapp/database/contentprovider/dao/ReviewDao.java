package udacity.movieapp.database.contentprovider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import udacity.movieapp.database.contentprovider.MyProvider;
import udacity.movieapp.model.detail.Review;
import udacity.movieapp.model.mainscreen.Movie;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 2/5/2017.
 */

public class ReviewDao extends BaseDao {

    public ReviewDao(Context context) {
        super(context);
    }

    public List<Review> getAll(String[] projection,
                               String movieId, String sortOrder) {
        ArrayList<Review> reviews = new ArrayList<>();
        Cursor c = myProvider.query(MyProvider.CONTENT_URI_REVIEW, projection, BuildConfig.TABLE_COMMAN_ID+"=?",new String[]{ movieId}, sortOrder);
        if (c.moveToFirst()) {
            do {
                Review review = new Review(
                        c.getString(c.getColumnIndex(BuildConfig.TABLE_REVIEW_ID)),
                        c.getString(c.getColumnIndex(BuildConfig.TABLE_REVIEW_AUTHOR)),
                        c.getString(c.getColumnIndex(BuildConfig.TABLE_REVIEW_CONTENT)));
                reviews.add(review);
            } while (c.moveToNext());
        }
        return reviews;
    }

    public int saveAll(final ArrayList<Review> reviews, String movieId) throws Exception {
        int count = 0;
        if (reviews != null && reviews.size() != 0) {
            ContentValues[] contentValues = new ContentValues[reviews.size()];
            for (int i = 0; i < reviews.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(BuildConfig.TABLE_REVIEW_ID, reviews.get(i).getId());
                values.put(BuildConfig.TABLE_REVIEW_AUTHOR, reviews.get(i).getAuthor());
                values.put(BuildConfig.TABLE_REVIEW_CONTENT, reviews.get(i).getContent());
                values.put(BuildConfig.TABLE_COMMAN_ID, movieId);
                contentValues[i] = values;
            }
            count = myProvider.bulkInsert(MyProvider.CONTENT_URI_REVIEW, contentValues);
        }
        return count;
    }

    public int deleteReview(String id,String selection, String[] selectionArgs) throws SQLException {
        Uri uri=MyProvider.CONTENT_URI_REVIEW.buildUpon().appendPath(id).build();
        return  myProvider.delete(uri,selection,selectionArgs);
    }
}
