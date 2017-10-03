package udacity.movieapp.model.detail;

import java.util.ArrayList;

/**
 * Created by Mostafa Montaser on 2/3/2017.
 */

public class TrailersModel {
   private ArrayList<Trailer> results;

    public ArrayList<Trailer> getTrailers() {
        return results;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.results = trailers;
    }
}
