package udacity.movieapp.view.mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import udacity.movieapp.R;
import udacity.movieapp.model.mainscreen.Movie;
import udacity.movieapp.util.BuildConfig;
import udacity.movieapp.util.PicassoTarget;
import udacity.movieapp.util.SerialBitmap;

/**
 * Created by Mostafa Montaser on 1/25/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Movie> {
    List<Movie> movieItems = new ArrayList<>();
    Context context;
    private String title;
    private int openingMode;
    private MovieListener mListener;


    public interface MovieListener {
        void onMovieClicked(Movie movie);
    }

    public GridViewAdapter(List<Movie> movieItems, Context context, MovieListener listener) {
        super(context, android.R.layout.simple_list_item_1, movieItems);
        this.context = context;
        if (movieItems != null)
            this.movieItems = movieItems;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return movieItems == null ? 0 : movieItems.size();
    }

    public void setItems(ArrayList<Movie> items) {
        movieItems.clear();
        movieItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final PicassoTarget picassoTarget;
        View view = convertView;
        final Movie movie = movieItems.get(position);
        final MovieViewHolder movieViewHolder;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_movie, parent, false);
            movieViewHolder = new MovieViewHolder(view);
            view.setTag(movieViewHolder);
        } else {
            movieViewHolder = (MovieViewHolder) view.getTag();

        }

        movieViewHolder.getImage().setImageDrawable(null);
        movieViewHolder.getTile().setText(movie.getTitle());
        //picassoTarget = new PicassoTarget(movieViewHolder.getProgressBar(),movieViewHolder.getImage());
        Picasso.with(context).
                load(BuildConfig.IMAGE_BASE_URL + movie.getPosterUrl())
                .
                placeholder(R.drawable.placeholder).into(movieViewHolder.getImage(), new Callback() {
            @Override
            public void onSuccess() {
                movieViewHolder.getProgressBar().setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMovieClicked(movie);
                }
            }
        });
        return view;
    }


    class MovieViewHolder {
        protected TextView tile;
        protected ImageView image;
        protected ProgressBar progressBar;
        View view;

        public MovieViewHolder(View view) {
            this.view = view;
        }

        public TextView getTile() {
            if (title == null)
                this.tile = (TextView) view.findViewById(R.id.movie_title);
            return tile;
        }

        public ImageView getImage() {
            if (image == null)
                this.image = (ImageView) view.findViewById(R.id.movie_img);
            return image;
        }

        public ProgressBar getProgressBar() {
            if (progressBar == null)
                this.progressBar = (ProgressBar) view.findViewById(R.id.progress);
            return progressBar;
        }
    }
}
