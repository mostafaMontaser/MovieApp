package udacity.movieapp.service.mainscreen;

import java.util.HashMap;


import javax.inject.Inject;

import rx.Observable;
import udacity.movieapp.network.API;
import udacity.movieapp.network.ApiClient;
import udacity.movieapp.network.RequestManager;
import udacity.movieapp.service.BaseService;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class DashBoardService extends BaseService{
    @Inject
    public DashBoardService(RequestManager requestManager) {
        this.mRequestManager = requestManager;
    }
    @Override
    public Observable startService(HashMap params) {
        API api = mRequestManager.startRequest(API.class);
        Observable observable = null;
        if (params != null) {
            String type = (String) params.get(BuildConfig.MOVIE_TYPE);
            if (type.equals(BuildConfig.POPULAR))
                observable = api.getPopularMovie(BuildConfig.API_KEY);
            else if (type.equals(BuildConfig.TOP_RATED))
                observable = api.getTopRatedMovie(BuildConfig.API_KEY);
        }
        return observable;
    }
}
