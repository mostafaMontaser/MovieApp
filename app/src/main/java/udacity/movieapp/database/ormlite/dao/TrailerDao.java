package udacity.movieapp.database.ormlite.dao;

import udacity.movieapp.model.detail.Trailer;

/**
 * Created by Mostafa Montaser on 2/5/2017.
 */

public class TrailerDao extends BaseDao {
    public TrailerDao() {
        super(Trailer.class);
    }
}
