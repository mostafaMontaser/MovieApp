package udacity.movieapp.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.movieapp.NavigationManager;
import udacity.movieapp.R;
import udacity.movieapp.error.ErrorListener;
import udacity.movieapp.view.mainscreen.DashBoardFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.contentFrame)
    FrameLayout contentFrame;
    @BindView(R.id.loadingFrame)
    FrameLayout loadingFrame;
    @BindView(R.id.errorFrame)
    FrameLayout errorFrame;
    @BindView(R.id.errorText)
    TextView errorText;
    private NavigationManager mNavigationManager;
    private ErrorListener errorListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigationManager = NavigationManager.getInstance(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setFirstLevelFragment(DashBoardFragment.getInstance());
    }

    public void setFragment(Fragment fragment, Bundle args) {
        mNavigationManager.pushFragment(fragment, args);
    }

    public void setFragment(Fragment fragment) {
        setFragment(fragment, null);
    }

    public void setFirstLevelFragment(Fragment firstLevelFragment) {
        mNavigationManager.setFirstLevelFragment(firstLevelFragment, null);

    }

    public ErrorListener getErrorListener() {
        return errorListener;
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public void showContent() {
        loadingFrame.setVisibility(View.GONE);
        errorFrame.setVisibility(View.GONE);
        contentFrame.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        contentFrame.setVisibility(View.GONE);
        errorFrame.setVisibility(View.GONE);
        loadingFrame.setVisibility(View.VISIBLE);
    }
    public void showError() {
        loadingFrame.setVisibility(View.GONE);
        contentFrame.setVisibility(View.GONE);
        errorFrame.setVisibility(View.VISIBLE);
    }
    public void setErrorText(String text) {
        errorText.setText(text);
    }
    @OnClick(R.id.retry)
    public void retry(){
        if(errorListener!=null)
        errorListener.onError();
    }
}
