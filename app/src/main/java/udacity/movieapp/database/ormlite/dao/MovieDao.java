package udacity.movieapp.database.ormlite.dao;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

import udacity.movieapp.model.detail.Review;
import udacity.movieapp.model.detail.Trailer;
import udacity.movieapp.model.mainscreen.Movie;

/**
 * Created by Mostafa Montaser on 2/5/2017.
 */

public class MovieDao extends BaseDao {

    public MovieDao() {
        super(Movie.class);
    }


    public void save(Movie movie) {

        final ArrayList<Review> mReviews = new ArrayList<>();
        final ArrayList<Trailer> mTrailers = new ArrayList<>();
        try {
            if (movie.getReviews() != null) {
                for (Review reviewDataBase :
                        movie.getReviews()) {
                    reviewDataBase.setMovie(movie);
                    mReviews.add(reviewDataBase);
                }
            }
            if (movie.getTrailers() != null) {
                for (Trailer trailer :
                        movie.getTrailers()) {
                    trailer.setMovie(movie);
                    mTrailers.add(trailer);
                }
            }
            ReviewDao reviewDao = new ReviewDao();
            reviewDao.saveAll(mReviews);
            TrailerDao trailerDao = new TrailerDao();
            trailerDao.saveAll(mTrailers);
            create(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isFavourite(String id) {
        Movie temp = null;
        boolean flag = false;
        try {
            QueryBuilder<Movie, Long> qb = mDao.queryBuilder();
            qb.where().eq("is_favourite", true);
            qb.where().eq("id", id);
            PreparedQuery<Movie> pq = qb.prepare();
            temp = (Movie) mDao.queryForFirst(pq);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        if (temp != null)
            flag = true;
        return flag;
    }

    public Movie getMovie(String id) {
        Movie temp = null;
        boolean flag = false;
        try {
            QueryBuilder<Movie, Long> qb = mDao.queryBuilder();
            qb.where().eq("id", id);
            PreparedQuery<Movie> pq = qb.prepare();
            temp = (Movie) mDao.queryForFirst(pq);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return temp;
    }

    public void deleteMovie(Movie movie) {
        try {
            ReviewDao reviewDao = new ReviewDao();
            TrailerDao trailerDao = new TrailerDao();
            if (movie.getReviews() != null) {
                for (Review reviewDataBase :
                        movie.getReviews()) {
                    reviewDao.delete(reviewDataBase);
                }
            }
            if (movie.getTrailers() != null) {
                for (Trailer trailer :
                        movie.getTrailers()) {
                    trailerDao.delete(trailer);
                }
            }
            delete(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
