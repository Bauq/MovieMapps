package co.edu.udea.udea.edu.co.moviemapps.listener;

import android.view.View;

import co.edu.udea.udea.edu.co.moviemapps.model.Pelicula;

/**
 * Created by Carolina on 29/03/2016.
 */
public interface OnItemMovieListener {

    void onItemClick(Pelicula pelicula, View view, int position);

}
