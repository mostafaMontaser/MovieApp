package udacity.movieapp.service;

import java.util.HashMap;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public interface ContractBaseService<T> {
    T startService(HashMap<String, Object> params);
}
