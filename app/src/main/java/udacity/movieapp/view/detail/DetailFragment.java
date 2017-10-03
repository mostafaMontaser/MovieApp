package udacity.movieapp.view.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import udacity.movieapp.MovieAppApplication;
import udacity.movieapp.R;
import udacity.movieapp.model.detail.Review;
import udacity.movieapp.model.detail.Trailer;
import udacity.movieapp.model.mainscreen.Movie;
import udacity.movieapp.presenter.detail.DaggerDetailPresenterComponent;
import udacity.movieapp.presenter.detail.DetailPresenter;
import udacity.movieapp.presenter.detail.DetailPresenterModule;
import udacity.movieapp.util.BuildConfig;
import udacity.movieapp.util.PicassoTarget;
import udacity.movieapp.view.BaseFragment;
import udacity.movieapp.view.MainActivity;

/**
 * Created by Mostafa Montaser on 1/26/2017.
 */

public class DetailFragment extends BaseFragment {
    @Inject
    DetailPresenter detailPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_rating)
    RatingBar movieRating;
    @BindView(R.id.movie_image)
    ImageView imageView;
    @BindView(R.id.add_fav)
    FloatingActionButton favButton;
    @BindView(R.id.description_text)
    TextView description;
    @BindView(R.id.contentFrameReviews)
    LinearLayout contentFrameReviews;
    @BindView(R.id.loadingReviews)
    FrameLayout loadingReviews;
    @BindView(R.id.errorReviews)
    FrameLayout errorReviews;
    @BindView(R.id.contentFrameTrailers)
    LinearLayout contentFrameTrailers;
    @BindView(R.id.loadingTrailers)
    FrameLayout loadingTrailers;
    @BindView(R.id.errorTrailers)
    FrameLayout errorTrailers;
    @BindView(R.id.reviewsList)
    NonScrollExpandableListView reviewsList;
    @BindView(R.id.videos)
    ViewPager viewPager;
    @BindView(R.id.tabDots)
    TabLayout tabLayout;
    @BindView(R.id.trailersView)
    LinearLayout trailersView;
    @BindView(R.id.noReviews)
    TextView noReviews;
    @BindView(R.id.noTrailers)
    TextView noTrailers;
    @BindView(R.id.backImage)
    ImageView backImage;
    @BindView(R.id.movie_date)
    TextView movieDate;
    Movie movie;
    boolean isFavourite;
    ReviewListAdapter reviewsAdapter;
    boolean fromFav;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerDetailPresenterComponent.builder()
                .serviceComponent(((MovieAppApplication) getActivity().getApplication()).getServiceComponent())
                .detailPresenterModule(new DetailPresenterModule(this))
                .build()
                .inject(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            movie = (Movie) bundle.getSerializable(BuildConfig.MOVIE);
            fromFav = (boolean) bundle.getBoolean(BuildConfig.IS_FAV);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            initUI();
        }
    }

    private void initUI() {
        if (movie != null) {
            toolbar.setTitle(movie.getTitle());
            Picasso.with(getContext()).load(BuildConfig.IMAGE_BASE_URL + movie.getPosterUrl()).placeholder( R.drawable.progress_animation).error(R.drawable.placeholder).into(imageView);
            description.setText(movie.getOverview());
            movieDate.setText(movie.getRelease_date());
            movieRating.setRating(movie.getVote_average());
            PicassoTarget picassoTarget = new PicassoTarget(null,backImage);
            Picasso.with(getContext()).load(BuildConfig.IMAGE_BASE_URL + movie.getBackdrop_path()).placeholder( R.drawable.progress_animation).error(R.drawable.placeholder).into(picassoTarget);

            if (detailPresenter.isFavouriteMovie(movie.getId())) {
                if(fromFav){
                    favButton.setVisibility(View.INVISIBLE);
                }
                movie = detailPresenter.getMovie(movie.getId());
                favButton.setSelected(true);
                updateReviews(new ArrayList<Review>(movie.getReviews()));
                updateTrailers(new ArrayList<Trailer>(movie.getTrailers()));
            } else {
                favButton.setSelected(false);
                getReviews();
                getTrailers();
            }
        }
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        //Show "back" button
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getReviews() {
        showLoadingReviews();
        detailPresenter.getReviews(BuildConfig.ACTION_REVIEWS, movie.getId());
    }

    private void getTrailers() {
        showLoadingTrailers();
        detailPresenter.getTrailers(BuildConfig.ACTION_TRAILERS, movie.getId());
    }

    public void onErrorReviews() {
        showErrorReviews();
    }

    public void onErrorTrailers() {
        showErrorTrailers();
    }

    public void updateReviews(ArrayList<Review> reviews) {
        showContentReviews();
        if (reviews != null && reviews.size() != 0) {
            reviewsAdapter = new ReviewListAdapter(getContext(), reviews);
            reviewsList.setAdapter(reviewsAdapter);
            noReviews.setVisibility(View.GONE);
            reviewsList.setVisibility(View.VISIBLE);
            movie.setReviews(reviews);
        } else {
            noReviews.setVisibility(View.VISIBLE);
            reviewsList.setVisibility(View.GONE);
        }
    }

    public void updateTrailers(ArrayList<Trailer> trailers) {
        showContentTrailers();
        if (trailers != null && trailers.size() != 0) {
            PagerAdapter pagerAdapter = new PagerAdapter(getContext(), trailers);
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            noTrailers.setVisibility(View.GONE);
            trailersView.setVisibility(View.VISIBLE);
            movie.setTrailers(trailers);
        } else {
            noTrailers.setVisibility(View.VISIBLE);
            trailersView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_detail;
    }

    public static DetailFragment getInstance() {
        return new DetailFragment();
    }

    @OnClick(R.id.add_fav)
    public void addFavourite() {
        if (movie.isFavourite()) {
            movie.setFavourite(false);
            detailPresenter.deleteMovie(movie);
            favButton.setSelected(false);
        } else {
            movie.setFavourite(true);
            detailPresenter.saveMovie(movie);
            favButton.setSelected(true);
        }
    }

    public void showContentReviews() {
        loadingReviews.setVisibility(View.GONE);
        errorReviews.setVisibility(View.GONE);
        contentFrameReviews.setVisibility(View.VISIBLE);
    }

    public void showLoadingReviews() {
        contentFrameReviews.setVisibility(View.GONE);
        errorReviews.setVisibility(View.GONE);
        loadingReviews.setVisibility(View.VISIBLE);
    }

    public void showErrorReviews() {
        loadingReviews.setVisibility(View.GONE);
        contentFrameReviews.setVisibility(View.GONE);
        errorReviews.setVisibility(View.VISIBLE);
    }

    public void showContentTrailers() {
        loadingTrailers.setVisibility(View.GONE);
        errorTrailers.setVisibility(View.GONE);
        contentFrameTrailers.setVisibility(View.VISIBLE);
    }

    public void showLoadingTrailers() {
        contentFrameTrailers.setVisibility(View.GONE);
        errorTrailers.setVisibility(View.GONE);
        loadingTrailers.setVisibility(View.VISIBLE);
    }

    public void showErrorTrailers() {
        loadingTrailers.setVisibility(View.GONE);
        contentFrameTrailers.setVisibility(View.GONE);
        errorTrailers.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.retryReviews)
    public void retryReviews() {
        getReviews();
    }

    @OnClick(R.id.retry_trailers)
    public void retryTrailers() {
        getTrailers();
    }
}
