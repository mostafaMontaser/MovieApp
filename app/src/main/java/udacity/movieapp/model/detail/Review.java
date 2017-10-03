package udacity.movieapp.model.detail;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import udacity.movieapp.model.mainscreen.Movie;

import java.io.Serializable;

/**
 * Created by Mostafa Montaser on 2/2/2017.
 */
@DatabaseTable(tableName = "review")
public class Review implements Serializable {
    @DatabaseField(id = true, columnName = "id")
    private String id;
    @DatabaseField(columnName = "author")
    private String author;
    @DatabaseField(columnName = "content")
    private String content;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Movie movie;
    public Movie getMovie() {
        return movie;
    }
    public Review(String author) {
        this.author = author;
    }

    public Review() {

    }

    public Review(String id, String author,String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMovie(Movie movieDataBase) {
        this.movie = movieDataBase;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
