package udacity.movieapp.network;



import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import udacity.movieapp.model.detail.ReviewsModel;
import udacity.movieapp.model.detail.TrailersModel;
import udacity.movieapp.model.mainscreen.MoviesModel;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 1/23/2017.
 */

public interface API {

    @GET(BuildConfig.POPULAR)
    Observable<MoviesModel> getPopularMovie(@Query("api_key") String key);
    @GET(BuildConfig.TOP_RATED)
    Observable<MoviesModel> getTopRatedMovie(@Query("api_key") String key);
    @GET(BuildConfig.REVIEWS)
    Observable<ReviewsModel> getMovieReview(@Path("id") String id,@Query("api_key") String key);
    @GET(BuildConfig.TRAILERS)
    Observable<TrailersModel> getMovieTrailer(@Path("id") String id, @Query("api_key") String key);

}
