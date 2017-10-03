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
import udacity.movieapp.model.detail.Trailer;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 2/5/2017.
 */

public class TrailerDao extends BaseDao {
    public TrailerDao(Context context) {
        super(context);
    }
    public List<Trailer> getAll(String[] projection,
                               String movieId, String sortOrder) {
        ArrayList<Trailer> trailers = new ArrayList<>();
        Cursor c = myProvider.query(MyProvider.CONTENT_URI_TRAILER, projection,BuildConfig.TABLE_COMMAN_ID+"=?",new String[]{ movieId}, sortOrder);
        if (c.moveToFirst()) {
            do {
               Trailer trailer=new Trailer(
                       c.getString(c.getColumnIndex(BuildConfig.TABLE_TRAILER_ID)),
                       c.getString(c.getColumnIndex(BuildConfig.TABLE_TRAILER_NAME)),
                       c.getString(c.getColumnIndex(BuildConfig.TABLE_TRAILER_KEY))
                       );
                trailers.add(trailer);
            } while (c.moveToNext());
        }
        return trailers;
    }

    public int saveAll(final List<Trailer> trailers, String movieId) throws Exception {
        int count = 0;
        if (trailers != null && trailers.size() != 0) {
            ContentValues[] contentValues = new ContentValues[trailers.size()];
            for (int i = 0; i < trailers.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(BuildConfig.TABLE_TRAILER_ID, trailers.get(i).getId());
                values.put(BuildConfig.TABLE_TRAILER_KEY, trailers.get(i).getKey());
                values.put(BuildConfig.TABLE_TRAILER_NAME, trailers.get(i).getName());
                values.put(BuildConfig.TABLE_COMMAN_ID, movieId);
                contentValues[i] = values;
            }
            count = myProvider.bulkInsert(MyProvider.CONTENT_URI_TRAILER, contentValues);
        }
        return count;
    }

    public int deleteReview(String id,String selection, String[] selectionArgs) throws SQLException {
        Uri uri=MyProvider.CONTENT_URI_TRAILER.buildUpon().appendPath(id).build();
        return  myProvider.delete(uri,selection,selectionArgs);
    }
}
