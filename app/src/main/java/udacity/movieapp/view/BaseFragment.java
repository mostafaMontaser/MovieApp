package udacity.movieapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.ButterKnife;
import udacity.movieapp.presenter.ContractBasePresenter;
import udacity.movieapp.util.PicassoTarget;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public abstract class BaseFragment<P extends ContractBasePresenter> extends Fragment implements ContractBaseView<P> {
    protected P mPresenter;
    protected Context mContext;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, rootView);
        if (mPresenter == null) {
            mPresenter = getPresenter();
        }

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        return rootView;
    }

    protected abstract int getLayoutRes();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    @Override
    public void setPresenter(P mPresenter) {
        this.mPresenter = mPresenter;
    }


    /**
     * Creates a new presenter instance, if needed. Will reuse the previous presenter instance if
     */
    protected P getPresenter() {
        return mPresenter;
    }


    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    public void showContent() {
        ((MainActivity) getActivity()).showContent();
    }

    public void showLoading() {
        ((MainActivity) getActivity()).showLoading();
    }

    public void showError() {
        ((MainActivity) getActivity()).showError();
    }


}
