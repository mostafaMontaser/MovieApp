package udacity.movieapp.presenter.mainscreen;

import dagger.Component;
import udacity.movieapp.service.ServiceComponent;
import udacity.movieapp.util.FragmentScoped;
import udacity.movieapp.view.mainscreen.DashBoardFragment;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */
@FragmentScoped
@Component(dependencies = ServiceComponent.class, modules = {DashBoardPresenterModule.class})
public interface DashBoardPresenterComponent {
    void inject(DashBoardFragment dashBoardFragment);
}
