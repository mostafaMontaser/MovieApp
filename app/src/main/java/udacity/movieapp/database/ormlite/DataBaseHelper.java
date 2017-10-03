package udacity.movieapp.database.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


import udacity.movieapp.model.detail.Review;
import udacity.movieapp.model.detail.Trailer;
import udacity.movieapp.model.mainscreen.Movie;

/**
 * Created by Mostafa Montaser on 2/4/2017.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper{

    private static DataBaseHelper mDatabaseHelper;
    private Context mContext ;
    /**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     */
    private Dao<Movie, Long> movieDao;
    private Dao<Review, Long> dao;
    public static DataBaseHelper getInstance(Context context, String strDatabaseName, int version)  {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DataBaseHelper(context, strDatabaseName, null, version);
        }

        return mDatabaseHelper;
    }

    private DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)  {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            /**
             * creates the Todo database table
             */
            TableUtils.createTable(connectionSource, Movie.class);
            TableUtils.createTable(connectionSource, Review.class);
            TableUtils.createTable(connectionSource, Trailer.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            /**
             * Recreates the database when onUpgrade is called by the framework
             */
            TableUtils.dropTable(connectionSource, Movie.class, false);
            TableUtils.dropTable(connectionSource, Review.class, false);
            TableUtils.dropTable(connectionSource, Trailer.class, false);

            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an instance of the data access object
     * @return
     * @throws SQLException
     */
    public Dao<Movie, Long> getDao() throws SQLException {
        if(movieDao == null) {
            movieDao = getDao(Movie.class);
        }
        return movieDao;
    }
    public Dao<Review, Long> getReviewDao() throws SQLException {
        if(dao == null) {
            dao = getDao(Review.class);
        }
        return dao;
    }
}