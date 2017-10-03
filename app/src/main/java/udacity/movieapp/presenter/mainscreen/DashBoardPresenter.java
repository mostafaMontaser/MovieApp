package udacity.movieapp.presenter.mainscreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import udacity.movieapp.database.contentprovider.dao.MovieDao;
import udacity.movieapp.model.mainscreen.Movie;
import udacity.movieapp.model.mainscreen.MoviesModel;
import udacity.movieapp.network.SchedulerProvider;
import udacity.movieapp.presenter.BasePresenter;
import udacity.movieapp.service.mainscreen.DashBoardService;
import udacity.movieapp.util.BuildConfig;
import udacity.movieapp.view.mainscreen.DashBoardFragment;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class DashBoardPresenter extends BasePresenter {

    private final DashBoardFragment mView;
    private final DashBoardService mService;

    @Inject
    DashBoardPresenter(DashBoardFragment mView,DashBoardService mService) {
        this.mView = mView;
        this.mService=mService;
    }
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }
    public void getMovies(String key){
        HashMap hashMap=new HashMap();
        hashMap.put(BuildConfig.MOVIE_TYPE,key);
        Subscription subscription = mService.startService(hashMap )
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribeOn(SchedulerProvider.getInstance().io())
                .subscribe(new Subscriber<MoviesModel>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.retry();

                    }
                    @Override
                    public void onNext(final MoviesModel moviesModel) {
                        mView.showContent();
                        mView.updateView(moviesModel.getMovies());
                    }
                });
    }
    public void getAllFavouriteMovies(){
        MovieDao movieDao=new MovieDao(mView.getContext());
        Collection movies=movieDao.getAll(null,null);
        if(movies!=null)
        mView.updateView(new ArrayList<Movie>(movies));
    }
}
