package udacity.movieapp.view.detail;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import udacity.movieapp.R;
import udacity.movieapp.model.detail.Review;
import udacity.movieapp.model.mainscreen.Movie;
import udacity.movieapp.util.BuildConfig;
import udacity.movieapp.util.PicassoTarget;
import udacity.movieapp.view.mainscreen.GridViewAdapter;

/**
 * Created by Mostafa Montaser on 2/3/2017.
 */

public class ReviewListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    List<Review> reviewList = new ArrayList<>();

    public ReviewListAdapter(Context context, ArrayList<Review> reviewList) {
        this._context = context;
        this.reviewList = reviewList;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.reviewList.get(childPosititon).getContent();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        View view = convertView;
        final Review review = reviewList.get(groupPosition);
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_review, parent, false);
            view.setTag(new ReviewListAdapter.ReviewHolder(view));
        }
        ReviewListAdapter.ReviewHolder reviewHolder = ( ReviewListAdapter.ReviewHolder) view.getTag();
        reviewHolder.getContent().setText(review.getContent());
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.reviewList.get(groupPosition).getAuthor();
    }

    @Override
    public int getGroupCount() {
        return this.reviewList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = convertView;
        final Review review = reviewList.get(groupPosition);
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_review_header, parent, false);
            view.setTag(new ReviewListAdapter.ReviewHeaderHolder(view));
        }
        ReviewListAdapter.ReviewHeaderHolder reviewHeaderHolder = ( ReviewListAdapter.ReviewHeaderHolder) view.getTag();
        reviewHeaderHolder.getTile().setText(review.getAuthor());
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ReviewHeaderHolder {
        protected TextView title;
        View view;

        public ReviewHeaderHolder(View view) {
            this.view = view;
        }

        public TextView getTile() {
            if (title == null)
                this.title = (TextView) view.findViewById(R.id.review_author);
            return title;
        }
    }
    class ReviewHolder {
        protected TextView content;
        View view;

        public ReviewHolder(View view) {
            this.view = view;
        }

        public TextView getContent() {
            if (content == null)
                this.content = (TextView) view.findViewById(R.id.movie_review);
            return content;
        }
    }
}
