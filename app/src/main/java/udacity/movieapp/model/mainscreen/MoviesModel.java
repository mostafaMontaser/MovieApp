package udacity.movieapp.model.mainscreen;

import java.util.ArrayList;

/**
 * Created by Mostafa Montaser on 1/25/2017.
 */

public class MoviesModel {
    ArrayList<Movie>results;

    public ArrayList<Movie> getMovies() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
