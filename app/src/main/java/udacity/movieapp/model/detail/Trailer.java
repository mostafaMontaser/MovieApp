package udacity.movieapp.model.detail;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

import udacity.movieapp.model.mainscreen.Movie;

/**
 * Created by Mostafa Montaser on 2/3/2017.
 */

public class Trailer implements Serializable{
    @DatabaseField(id = true,columnName = "id")
    private String id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "key")
    private String key;
    @DatabaseField(foreign=true, foreignAutoRefresh = true)
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie= movie;
    }

    public Trailer(String name) {
        this.name = name;
    }
    public Trailer() {

    }

    public Trailer(String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
