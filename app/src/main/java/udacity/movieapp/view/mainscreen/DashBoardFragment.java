package udacity.movieapp.view.mainscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import udacity.movieapp.MovieAppApplication;
import udacity.movieapp.R;
import udacity.movieapp.error.ErrorListener;
import udacity.movieapp.model.mainscreen.Movie;
import udacity.movieapp.presenter.mainscreen.DaggerDashBoardPresenterComponent;
import udacity.movieapp.presenter.mainscreen.DashBoardPresenter;
import udacity.movieapp.presenter.mainscreen.DashBoardPresenterModule;
import udacity.movieapp.util.BuildConfig;
import udacity.movieapp.util.MoviesType;
import udacity.movieapp.view.BaseFragment;
import udacity.movieapp.view.MainActivity;
import udacity.movieapp.view.detail.DetailFragment;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class DashBoardFragment extends BaseFragment implements ErrorListener, GridViewAdapter.MovieListener {
    @Inject
    DashBoardPresenter dashBoardPresenter;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.toolbar_dash)
    Toolbar toolbar;
    GridViewAdapter gridViewAdapter;
    MoviesType moviesType;
    private boolean fromFavourite=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerDashBoardPresenterComponent.builder().serviceComponent
                (((MovieAppApplication) getActivity().getApplication()).getServiceComponent())
                .dashBoardPresenterModule(new DashBoardPresenterModule(this))
                .build()
                .inject(this);
        ((MainActivity) getActivity()).setErrorListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            initUi();
            moviesType=MoviesType.MOST_POPULAR;
            makeConnection(BuildConfig.POPULAR);
        }
    }

    private void initUi() {
        gridViewAdapter = new GridViewAdapter(null, getContext(), this);
        gridView.setAdapter(gridViewAdapter);
        moviesType = MoviesType.MOST_POPULAR;
        //Set custom title
        toolbar.setTitle(moviesType.toString());
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.most_popular:
                if(moviesType!=MoviesType.MOST_POPULAR) {
                    moviesType=MoviesType.MOST_POPULAR;
                    fromFavourite=false;
                    makeConnection(moviesType.getValue());
                    toolbar.setTitle(moviesType.toString());
                }
                break;
            case R.id.top_rated:
                if(moviesType!=MoviesType.TOP_RATED) {
                    moviesType=MoviesType.TOP_RATED;
                    fromFavourite=false;
                    makeConnection(moviesType.getValue());
                    toolbar.setTitle(moviesType.toString());
                }
                break;
            case R.id.favourite:
                if(moviesType!=MoviesType.FAVOURITE) {
                    moviesType=MoviesType.FAVOURITE;
                    fromFavourite=true;
                    dashBoardPresenter.getAllFavouriteMovies();
                    toolbar.setTitle(moviesType.toString());
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void makeConnection(String type) {
        showLoading();
        dashBoardPresenter.getMovies(type);
    }

    public void updateView(ArrayList<Movie> movies) {
        gridView.invalidate();
        gridViewAdapter.setItems(movies);
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fly_in_from_center);
        gridView.setAnimation(anim);
        gridView.smoothScrollToPosition(0);
        anim.start();
    }

    public void retry() {
        showError();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_dashboard;
    }

    public static DashBoardFragment getInstance() {
        return new DashBoardFragment();
    }

    @Override
    public void onError() {
        makeConnection(moviesType.getValue());
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Bundle bundle=new Bundle();
        bundle.putSerializable(BuildConfig.IS_FAV,fromFavourite);
        bundle.putSerializable(BuildConfig.MOVIE,movie);
        ((MainActivity) getActivity()).setFragment(DetailFragment.getInstance(),bundle);
    }
}
