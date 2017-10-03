package udacity.movieapp.service;

import javax.inject.Singleton;

import dagger.Component;
import udacity.movieapp.ApplicationModule;
import udacity.movieapp.service.detail.DetailService;
import udacity.movieapp.service.mainscreen.DashBoardService;
import udacity.movieapp.view.detail.DetailFragment;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */
@Singleton
@Component(modules = {ServiceModule.class, ApplicationModule.class})
public interface ServiceComponent {
    DashBoardService getDashBoardService();
    DetailService getDetailService();
}
