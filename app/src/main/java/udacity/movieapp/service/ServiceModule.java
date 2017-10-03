package udacity.movieapp.service;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import udacity.movieapp.network.RequestManager;
import udacity.movieapp.service.mainscreen.DashBoardService;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */
@Module
public class ServiceModule {
    @Singleton
    @Provides
    RequestManager provideRequestManager() {
        return RequestManager.getInstance();
    }
}
