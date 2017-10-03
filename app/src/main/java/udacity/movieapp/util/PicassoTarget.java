package udacity.movieapp.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import udacity.movieapp.R;

/**
 * Created by Mostafa Montaser on 1/31/2017.
 */

public class PicassoTarget implements Target {
    ImageView imageView;
    ProgressBar progressBar;
    public PicassoTarget(ProgressBar progressBar,ImageView imageView) {
        this.imageView = imageView;
        this.progressBar=progressBar;
    }
    Bitmap bitmap;
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        imageView.setImageBitmap(bitmap);
        Drawable image = imageView.getDrawable();
        this.bitmap = bitmap;
        if(this.progressBar!=null)
            progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
    }
    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
