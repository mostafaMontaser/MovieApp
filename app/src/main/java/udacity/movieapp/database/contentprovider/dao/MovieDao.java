package udacity.movieapp.database.contentprovider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import udacity.movieapp.database.contentprovider.MyProvider;
import udacity.movieapp.model.detail.Review;
import udacity.movieapp.model.detail.Trailer;
import udacity.movieapp.model.mainscreen.Movie;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 2/5/2017.
 */

public class MovieDao extends BaseDao {
Context context;
    public MovieDao(Context context) {
        super(context);
        this.context=context;

    }

    public void save(Movie movie) {
        try {
            if (movie.getReviews() != null) {
                ReviewDao reviewDao = new ReviewDao(context);
                reviewDao.saveAll(new ArrayList<Review>(movie.getReviews()), movie.getId());
            }
            if (movie.getTrailers() != null) {
                TrailerDao trailerDao = new TrailerDao(context);
                trailerDao.saveAll(new ArrayList<Trailer>(movie.getTrailers()), movie.getId());
            }
            ContentValues values = new ContentValues();
            values.put(BuildConfig.TABLE_MOVIE_ID, movie.getId());
            values.put(BuildConfig.TABLE_MOVIE_BACKDROP_PATH, movie.getBackdrop_path());
            values.put(BuildConfig.TABLE_MOVIE_IS_FAVOURITE, movie.isFavourite());
            values.put(BuildConfig.TABLE_MOVIE_OVERVIEW, movie.getOverview());
            values.put(BuildConfig.TABLE_MOVIE_POSTER_PATH, movie.getPoster_path());
            values.put(BuildConfig.TABLE_MOVIE_RELEASE_DATE, movie.getRelease_date());
            values.put(BuildConfig.TABLE_MOVIE_TITLE, movie.getTitle());
            values.put(BuildConfig.TABLE_MOVIE_VOTE_AVERAGE, movie.getVote_average());
            myProvider.insert(MyProvider.CONTENT_URI_MOVIE, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isFavourite(String id) {
        Movie temp = null;
        boolean flag = false;
        Uri uri = MyProvider.CONTENT_URI_MOVIE.buildUpon().appendPath(id).build();
        Cursor c = myProvider.query(uri, null, null, null, null);
        if (c.moveToFirst()) {
            flag = true;
        }
        return flag;
    }

    public Movie getMovie(String id, String[] projection,
                          String sortOrder) {
        Movie temp = null;
        Uri uri = MyProvider.CONTENT_URI_MOVIE.buildUpon().appendPath(id).build();
        Cursor c = myProvider.query(uri, projection, BuildConfig.TABLE_MOVIE_ID+"=?", new String[]{id}, sortOrder);
        if (c.moveToFirst()) {
            ReviewDao reviewDao = new ReviewDao(context);
            TrailerDao trailerDao = new TrailerDao(context);
            temp = new Movie(
                    c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_ID)),
                    c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_TITLE)),
                    c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_RELEASE_DATE)),
                    c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_POSTER_PATH)),
                    c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_BACKDROP_PATH)),
                    c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_OVERVIEW)),
                    c.getFloat(c.getColumnIndex(BuildConfig.TABLE_MOVIE_VOTE_AVERAGE)),
                    c.getInt(c.getColumnIndex(BuildConfig.TABLE_MOVIE_IS_FAVOURITE)) == 0 ? false : true);
            temp.setReviews(reviewDao.getAll(null, id, null));
            temp.setTrailers(trailerDao.getAll(null, id, null));
        }
        return temp;
    }

    public ArrayList<Movie> getAll(String[] projection,
                                   String sortOrder) {
        ArrayList<Movie> movies = new ArrayList<>();
        Cursor c = myProvider.query(MyProvider.CONTENT_URI_MOVIE, projection, null, null, sortOrder);
        if (c.moveToFirst()) {
            do {
                ReviewDao reviewDao = new ReviewDao(context);
                TrailerDao trailerDao = new TrailerDao(context);
                String id=c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_ID));
                Movie temp = new Movie(
                        id,
                        c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_TITLE)),
                        c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_RELEASE_DATE)),
                        c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_POSTER_PATH)),
                        c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_BACKDROP_PATH)),
                        c.getString(c.getColumnIndex(BuildConfig.TABLE_MOVIE_OVERVIEW)),
                        c.getFloat(c.getColumnIndex(BuildConfig.TABLE_MOVIE_VOTE_AVERAGE)),
                        c.getInt(c.getColumnIndex(BuildConfig.TABLE_MOVIE_IS_FAVOURITE)) == 0 ? false : true);
                temp.setReviews(reviewDao.getAll(null, id, null));
                temp.setTrailers(trailerDao.getAll(null, id, null));
                movies.add(temp);
            } while (c.moveToNext());
        }
        return movies;
    }

    public void deleteMovie(Movie movie) {
        try {

            if (movie.getReviews() != null &&movie.getReviews().size()!=0) {
                myProvider.delete(MyProvider.CONTENT_URI_REVIEW, BuildConfig.TABLE_COMMAN_ID+"=?", new String[]{movie.getId()});
            }
            if (movie.getTrailers() != null&&movie.getTrailers().size()!=0) {
                myProvider.delete(MyProvider.CONTENT_URI_TRAILER,BuildConfig.TABLE_COMMAN_ID+"=?", new String[]{movie.getId()});
            }
            Uri uri = MyProvider.CONTENT_URI_MOVIE.buildUpon().appendPath(movie.getId()).build();
            myProvider.delete(uri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
