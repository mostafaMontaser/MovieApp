package udacity.movieapp.view.detail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udacity.movieapp.R;
import udacity.movieapp.model.detail.Trailer;

/**
 * Created by Mostafa Montaser on 2/4/2017.
 */

public class PagerAdapter extends android.support.v4.view.PagerAdapter {

    Context context;
    ArrayList<Trailer> trailers;

    public PagerAdapter(Context context, ArrayList<Trailer> trailers) {
        this.context = context;
        this.trailers = trailers;
    }

    @Override
    public int getCount() {
        return trailers == null ? 0 : trailers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        final Trailer trailer = trailers.get(position);
        view = LayoutInflater.from(context).inflate(R.layout.cell_trailer, container, false);
        TextView textView = (TextView) view.findViewById(R.id.movie_header);
        textView.setText(trailer.getName());
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //videoIntent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                //videoIntent.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
              //  context.startActivity(videoIntent);
                Intent intent = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                intent.setComponent(new ComponentName("com.google.android.youtube","com.google.android.youtube.PlayerActivity"));

                PackageManager manager = context.getPackageManager();
                List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
                if (infos.size() > 0) {
                    context.startActivity(intent);
                }else{
                    Intent videoIntent = new Intent(Intent.ACTION_VIEW ,Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                    context.startActivity(videoIntent);
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
