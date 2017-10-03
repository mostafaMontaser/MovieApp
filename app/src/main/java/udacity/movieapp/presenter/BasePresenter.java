package udacity.movieapp.presenter;

import java.lang.ref.WeakReference;

import udacity.movieapp.view.ContractBaseView;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class BasePresenter<T extends ContractBaseView> implements ContractBasePresenter<T> {
    private WeakReference<T> viewRef;

    /**
     * Set or attach the view to this presenter
     */

    @Override
    public void attachView(ContractBaseView  view) {
        viewRef = new WeakReference<T>((T) view);
    }

    /**
     * Get the attached view. You should always call {@link #isViewAttached()} to check if the view
     * is
     * attached to avoid NullPointerExceptions
     */
    public T getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * Checks if a view is attached to this presenter. You should always call this method before
     * calling {@link #getView()} to get the view instance.
     */
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

}
