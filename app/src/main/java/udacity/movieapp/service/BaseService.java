package udacity.movieapp.service;

import android.support.annotation.StringRes;

import java.util.HashMap;

import udacity.movieapp.network.RequestManager;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public abstract class BaseService<T> implements ContractBaseService{
    protected RequestManager mRequestManager;
    public T startService(){
        return startService(null);
    };
    @Override
    public abstract T startService(HashMap params);
}
