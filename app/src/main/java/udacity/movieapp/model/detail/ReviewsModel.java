package udacity.movieapp.model.detail;

import java.util.ArrayList;

/**
 * Created by Mostafa Montaser on 2/2/2017.
 */

public class ReviewsModel {
    private ArrayList<Review> results;

    public ArrayList<Review> getReviews() {
        return results;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.results = reviews;
    }
}
