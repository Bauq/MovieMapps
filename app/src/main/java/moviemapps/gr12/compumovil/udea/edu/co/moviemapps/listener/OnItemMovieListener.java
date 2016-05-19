package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.listener;

import android.view.View;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Movie;

/**
 * Created by Carolina on 29/03/2016.
 */
public interface OnItemMovieListener {

    void onItemClick(Movie movie, View view, int position);

    void onItemLongClick(Movie movie, View view, int position);
}
