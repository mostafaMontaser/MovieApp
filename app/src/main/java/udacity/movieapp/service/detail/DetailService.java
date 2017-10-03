package udacity.movieapp.service.detail;

import java.util.HashMap;


import javax.inject.Inject;

import rx.Observable;
import udacity.movieapp.network.API;
import udacity.movieapp.network.RequestManager;
import udacity.movieapp.service.BaseService;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 1/26/2017.
 */

public class DetailService extends BaseService {
    @Inject
    public DetailService(RequestManager requestManager) {
        this.mRequestManager = requestManager;
    }
    @Override
    public Observable startService(HashMap params){
        API api = mRequestManager.startRequest(API.class);
        Observable observable = null;
        if (params != null) {
            String action = (String) params.get(BuildConfig.ACTION);
            String id = (String) params.get(BuildConfig.MOVIE_ID);
            if (action.equals(BuildConfig.ACTION_REVIEWS))
                observable = api.getMovieReview(id,BuildConfig.API_KEY);
            else if (action.equals(BuildConfig.ACTION_TRAILERS))
                observable = api.getMovieTrailer(id,BuildConfig.API_KEY);
        }
        return observable;
    }
}
