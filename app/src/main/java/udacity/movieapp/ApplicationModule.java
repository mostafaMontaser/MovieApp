package udacity.movieapp;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
