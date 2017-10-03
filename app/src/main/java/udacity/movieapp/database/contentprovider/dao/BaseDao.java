package udacity.movieapp.database.contentprovider.dao;

import android.content.ContentResolver;
import android.content.Context;


/**
 * Created by Mostafa Montaser on 2/5/2017.
 */
public abstract class BaseDao {

    protected ContentResolver myProvider;

    public BaseDao(Context context) {
            myProvider= context.getContentResolver();
    }


}
