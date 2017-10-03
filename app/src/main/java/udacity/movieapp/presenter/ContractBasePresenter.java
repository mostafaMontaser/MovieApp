package udacity.movieapp.presenter;

import udacity.movieapp.view.ContractBaseView;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public interface ContractBasePresenter<T extends ContractBaseView> {
    void attachView(T view);
    T getView();
    void detachView();
}
