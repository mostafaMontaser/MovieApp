package udacity.movieapp.view;

import udacity.movieapp.presenter.ContractBasePresenter;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public interface ContractBaseView<T extends ContractBasePresenter> {
    void setPresenter(T presenter);
}
