package udacity.movieapp;

import android.app.Application;

import udacity.movieapp.service.DaggerServiceComponent;
import udacity.movieapp.service.ServiceComponent;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class MovieAppApplication extends Application {
    private ServiceComponent mServiceComponent;
    public ServiceComponent getServiceComponent() {
        return mServiceComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mServiceComponent = DaggerServiceComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
    }
}
