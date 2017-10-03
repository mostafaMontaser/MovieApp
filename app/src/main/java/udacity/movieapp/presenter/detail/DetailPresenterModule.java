package udacity.movieapp.presenter.detail;

import dagger.Module;
import dagger.Provides;
import udacity.movieapp.view.detail.DetailFragment;

/**
 * Created by Mostafa Montaser on 1/26/2017.
 */
@Module
public class DetailPresenterModule {
DetailFragment detailFragment;
    public DetailPresenterModule(DetailFragment detailFragment){
        this.detailFragment=detailFragment;
    }
    @Provides
    public DetailFragment provideDetailView() {
        return detailFragment;
    }
}
