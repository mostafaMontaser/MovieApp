package udacity.movieapp.util;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class BuildConfig {
    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String API_KEY = "3c3d662f3dfecdd191da7642c1b1e2af";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String FAVOURITE = "favourite";
    public static final String MOVIE_TYPE = "movie_type";
    public static final String ACTION = "action";
    public static final String ACTION_REVIEWS = "action_reviews";
    public static final String ACTION_TRAILERS = "action_trailers";
    public static final String MOVIE = "movie";
    public static final String MOVIE_ID = "movie_id";
    public static final String REVIEWS = "{id}/reviews";
    public static final String TRAILERS = "{id}/videos";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    ///Database Vars
    public static final String DATABASE_NAME = "movies_database";
    public static final int DATABASE_VERSION = 1;
    //Movie
    public static final String TABLE_MOVIE = "movie";
    public static final String TABLE_MOVIE_ID = "id";
    public static final String TABLE_MOVIE_TITLE = "title";
    public static final String TABLE_MOVIE_RELEASE_DATE = "release_date";
    public static final String TABLE_MOVIE_POSTER_PATH = "poster_path";
    public static final String TABLE_MOVIE_BACKDROP_PATH = "backdrop_path";
    public static final String TABLE_MOVIE_OVERVIEW = "overview";
    public static final String TABLE_MOVIE_VOTE_AVERAGE = "vote_average";
    public static final String TABLE_MOVIE_IS_FAVOURITE = "is_favourite";
    public static final String TABLE_COMMAN_ID = "movie_id";
    //Review
    public static final String TABLE_REVIEW = "review";
    public static final String TABLE_REVIEW_ID = "id";
    public static final String TABLE_REVIEW_AUTHOR = "author";
    public static final String TABLE_REVIEW_CONTENT = "content";
    //Trailer
    public static final String TABLE_TRAILER = "trailer";
    public static final String TABLE_TRAILER_ID = "id";
    public static final String TABLE_TRAILER_NAME = "name";
    public static final String TABLE_TRAILER_KEY = "key";
    public static final String PROVIDER_NAME = "com.movieapp";
    public static final int PROVIDER_MOVIES = 1;
    public static final int PROVIDER_MOVIE_ID = 2;
    public static final int PROVIDER_REVIEWS = 3;
    public static final int PROVIDER_REVIEW_ID = 4;
    public static final int PROVIDER_TRAILERS = 5;
    public static final int PROVIDER_TRAILER_ID = 6;
    public static final String PROVIDER_SCHEMA = "content://";
    public static final String PROVIDER_MOVIES_type = "movies";
    public static final String PROVIDER_MOVIE_type = "movies/#";
    public static final String PROVIDER_REVIEWS_type = "reviews";
    public static final String PROVIDER_REVIEW_type = "reviews/#";
    public static final String PROVIDER_TRAILERS_type = "trailers";
    public static final String PROVIDER_TRAILER_type = "trailers/#";

    public static final String IS_FAV = "is_fav";

}
