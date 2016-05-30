package co.edu.udea.moviemapps.test.persistence;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;


import co.edu.udea.moviemapps.activities.MainActivity;
import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.persistence.MovieDataManager;


/**
 * Created by SA on 17/04/2016.
 */
public class testJUnit extends AndroidTestCase {

    MovieDataManager movieDataManager = null;

    Movie movie = null;
    String name = "Batman v Superman";
    MainActivity mainActivity;
    int i;
    @Before
    public void initValidString() {
        // Specify a valid string.

        movie = new Movie();
        i = 21312;
    }
    @Test
    public void insertMovie(){
        movie.setId(i);
        movieDataManager = MovieDataManager.getInstance();
        int idNew = movieDataManager.insert(movie);
        assertTrue(movie.getId().equals(idNew));
    }

    @Test
    public void updateMovie(){
            movie.setId(i);
            movie.setTitle(name);
            movieDataManager = MovieDataManager.getInstance();
            movieDataManager.update(movie);
            movie = null;
            movie = movieDataManager.getMovieById(i);
            assertTrue(movie.getTitle().equals(name));
    }



    @Test
    public void getMovie(){
            movie.setId(i);
            movie.setTitle(name);
            movieDataManager = MovieDataManager.getInstance();
            movie = movieDataManager.getMovieById(i);
            assertTrue(movie.getTitle().equals(name));
    }
}
