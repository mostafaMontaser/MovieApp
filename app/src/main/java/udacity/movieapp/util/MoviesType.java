package udacity.movieapp.util;

/**
 * Created by Mostafa Montaser on 1/29/2017.
 */

public enum MoviesType {
    TOP_RATED,MOST_POPULAR,FAVOURITE;

    @Override
    public String toString() {
        String string="";
        if(this.equals(TOP_RATED))
            string="Top Rated Movies";
        else if(this.equals(MOST_POPULAR))
            string="Most Popular Movies";
        else if(this.equals(FAVOURITE))
            string="Favourite Movies";
        return string;
    }
    public String getValue(){
        String string="";
        if(this.equals(TOP_RATED))
            string=BuildConfig.TOP_RATED;
        else if(this.equals(MOST_POPULAR))
            string=BuildConfig.POPULAR;
        else if(this.equals(FAVOURITE))
            string=BuildConfig.FAVOURITE;
        return string;
    }

}
