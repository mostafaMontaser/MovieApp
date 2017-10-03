package udacity.movieapp.database.ormlite.dao;

import udacity.movieapp.model.detail.Review;

/**
 * Created by Mostafa Montaser on 2/5/2017.
 */

public class ReviewDao extends BaseDao {

    public ReviewDao() {
        super(Review.class);
    }
}
