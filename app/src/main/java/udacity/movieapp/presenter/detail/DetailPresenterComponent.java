package udacity.movieapp.presenter.detail;

import dagger.Component;
import udacity.movieapp.service.ServiceComponent;
import udacity.movieapp.util.FragmentScoped;
import udacity.movieapp.view.detail.DetailFragment;

/**
 * Created by Mostafa Montaser on 1/26/2017.
 */
@FragmentScoped
@Component(dependencies = ServiceComponent.class, modules = DetailPresenterModule.class)
public interface DetailPresenterComponent {
    void inject(DetailFragment detailFragment);
}
