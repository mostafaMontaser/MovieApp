package udacity.movieapp.presenter.detail;


import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import udacity.movieapp.database.contentprovider.dao.MovieDao;
import udacity.movieapp.model.detail.ReviewsModel;
import udacity.movieapp.model.detail.TrailersModel;
import udacity.movieapp.model.mainscreen.Movie;
import udacity.movieapp.model.mainscreen.MoviesModel;
import udacity.movieapp.network.SchedulerProvider;
import udacity.movieapp.presenter.BasePresenter;
import udacity.movieapp.service.detail.DetailService;
import udacity.movieapp.util.BuildConfig;
import udacity.movieapp.view.detail.DetailFragment;

/**
 * Created by Mostafa Montaser on 1/26/2017.
 */

public class DetailPresenter extends BasePresenter<DetailFragment> {
    public final DetailFragment detailFragment;
    public final DetailService detailService;
    @Inject
    public DetailPresenter(DetailFragment detailFragment,DetailService detailService){
        this.detailFragment=detailFragment;
        this.detailService=detailService;
    }
    @Inject
    void setupListeners() {
        detailFragment.setPresenter(this);
    }
    public void getReviews(String action,String id){
        HashMap hashMap=new HashMap();
        hashMap.put(BuildConfig.ACTION,action);
        hashMap.put(BuildConfig.MOVIE_ID,id);
        Subscription subscription = detailService.startService(hashMap )
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribeOn(SchedulerProvider.getInstance().io())
                .subscribe(new Subscriber<ReviewsModel>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        detailFragment.onErrorReviews();
                    }
                    @Override
                    public void onNext(final ReviewsModel reviewsModel) {
                       detailFragment.updateReviews(reviewsModel.getReviews());
                    }
                });
    }
    public void getTrailers(String action,String id) {
        HashMap hashMap = new HashMap();
        hashMap.put(BuildConfig.ACTION, action);
        hashMap.put(BuildConfig.MOVIE_ID, id);
        Subscription subscription = detailService.startService(hashMap)
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribeOn(SchedulerProvider.getInstance().io())
                .subscribe(new Subscriber<TrailersModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        detailFragment.onErrorTrailers();

                    }

                    @Override
                    public void onNext(final TrailersModel trailersModel) {
                        detailFragment.updateTrailers(trailersModel.getTrailers());
                    }
                });
    }
    public void saveMovie(Movie movie){
        MovieDao movieDao=new MovieDao(detailFragment.getContext());
        movieDao.save(movie);
    }
    public void deleteMovie(Movie movie){
        MovieDao movieDao=new MovieDao(detailFragment.getContext());
        try {
            movieDao.deleteMovie(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isFavouriteMovie(String id){
        MovieDao movieDao=new MovieDao(detailFragment.getContext());
        return movieDao.isFavourite(id);
    }
    public Movie getMovie(String id){
        MovieDao movieDao=new MovieDao(detailFragment.getContext());
        return movieDao.getMovie(id,null,null);
    }
}
