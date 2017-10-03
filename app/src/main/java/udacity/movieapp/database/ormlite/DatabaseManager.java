package udacity.movieapp.database.ormlite;

import android.content.Context;

/**
 * Created by Mostafa Montaser on 2/5/2017.
 */

public class DatabaseManager {

    static private DatabaseManager instance;
    private DataBaseHelper mDatabaseHelper;

    private DatabaseManager(Context context, String DatabaseName, int version) {
        synchronized (this) {
            mDatabaseHelper = DataBaseHelper.getInstance(context, DatabaseName, version);
        }
    }

    static public void init(Context ctx , String databaseName, int version) {
        if (instance == null) {
            instance = new DatabaseManager(ctx, databaseName , version);
        }
    }

    static public DatabaseManager getInstance() {
        if (instance == null){
            throw new RuntimeException("You must initialize the database manager before using it");
        }
        return instance;
    }

    public DataBaseHelper getDatabaseHelper() {
        return mDatabaseHelper;
    }
}
