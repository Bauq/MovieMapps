package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.test.moviemapps.persistence;

import org.junit.Before;
import org.junit.Test;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.activities.MainActivity;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Movie;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.MovieDataManager;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by SA on 17/04/2016.
 */
public class testJUnit {

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

        try {
            movie.setId(i);
            movieDataManager = MovieDataManager.getInstance();
            int idNew = movieDataManager.guardar(movie);
            assertTrue(movie.getId().equals(idNew));
        }catch (Exception e){
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void updateMovie(){

        try {
            movie.setId(i);
            movie.setTitle(name);
            movieDataManager = MovieDataManager.getInstance();
            movieDataManager.update(movie);
            movie = null;
            movie = movieDataManager.getMovieById(i);
            assertTrue(movie.getTitle().equals(name));
        }catch (Exception e){
            e.printStackTrace();
            fail(e.getMessage());
        }
    }



    @Test
    public void getMovie(){

        try {
            movie.setId(i);
            movie.setTitle(name);
            movieDataManager = MovieDataManager.getInstance();
            movie = movieDataManager.getMovieById(i);
            assertTrue(movie.getTitle().equals(name));
        }catch (Exception e){
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
