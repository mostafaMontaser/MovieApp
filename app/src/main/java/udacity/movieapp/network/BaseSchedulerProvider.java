package udacity.movieapp.network;


import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by Mostafa Montaser on 1/25/2017.
 */

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}

