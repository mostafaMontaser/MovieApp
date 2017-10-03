package udacity.movieapp.model.mainscreen;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

import udacity.movieapp.model.detail.Review;
import udacity.movieapp.model.detail.Trailer;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 1/25/2017.
 */
@DatabaseTable(tableName = BuildConfig.TABLE_MOVIE)
public class Movie implements Serializable{
    @DatabaseField(id = true,columnName ="id")
    private String id;
    @DatabaseField(columnName ="title")
    private String title;
    @DatabaseField(columnName ="release_date")
    private String release_date;
    @DatabaseField(columnName ="poster_path")
    private String poster_path;
    @DatabaseField(columnName ="backdrop_path")
    private String backdrop_path;
    @DatabaseField(columnName ="overview")
    private String overview;
    @DatabaseField(columnName ="vote_average")
    private float vote_average;
    @DatabaseField(columnName ="is_favourite")
    private boolean isFavourite;
    @ForeignCollectionField(eager = true)
    private Collection<Review> reviews;
    @ForeignCollectionField(eager = true)
    private Collection<Trailer> trailers;
    public Movie(String title, String poster_path, String overview) {
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
    }
    public Movie() {

    }

    public Movie(String id, String title, String release_date, String poster_path, String backdrop_path, String overview, float vote_average, boolean isFavourite) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.isFavourite = isFavourite;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return poster_path;
    }

    public void setPosterUrl(String posterUrl) {
        this.poster_path = posterUrl;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public Collection<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(Collection<Trailer> trailers) {
        this.trailers = trailers;
    }
}
