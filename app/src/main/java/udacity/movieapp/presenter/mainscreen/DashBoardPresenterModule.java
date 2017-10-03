package udacity.movieapp.presenter.mainscreen;

import dagger.Module;
import dagger.Provides;
import udacity.movieapp.view.mainscreen.DashBoardFragment;

/**
 * Created by Mostafa Montaser on 12/23/2016.
 */
@Module
public class DashBoardPresenterModule {
    private final DashBoardFragment mView;

    public DashBoardPresenterModule(DashBoardFragment view) {
        mView = view;
    }

    @Provides
    DashBoardFragment provideDashBoardView() {
        return mView;
    }
}
