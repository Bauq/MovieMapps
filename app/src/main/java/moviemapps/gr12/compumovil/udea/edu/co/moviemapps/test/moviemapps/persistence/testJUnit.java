package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.test.moviemapps.persistence;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.activities.MainActivity;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.MovieDataManager;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.config.DataBaseHelper;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by SA on 17/04/2016.
 */
public class testJUnit {

    MovieDataManager movieDataManager = null;

    Pelicula pelicula = null;
    String name = "Batman v Superman";
    MainActivity mainActivity;
    int i;
    @Before
    public void initValidString() {
        // Specify a valid string.

        pelicula = new Pelicula();
        i = 21312;
    }
    @Test
    public void insertMovie(){
        pelicula.setId(i);
        movieDataManager = MovieDataManager.getInstance();
        int idNew = movieDataManager.guardar(pelicula);
        assertTrue(pelicula.getId().equals(idNew));
    }

    @Test
    public void updateMovie(){
            pelicula.setId(i);
            pelicula.setTitle(name);
            movieDataManager = MovieDataManager.getInstance();
            movieDataManager.update(pelicula);
            pelicula = null;
            pelicula = movieDataManager.getMovieById(i);
            assertTrue(pelicula.getTitle().equals(name));
    }



    @Test
    public void getMovie(){

        try {
            pelicula.setId(i);
            pelicula.setTitle(name);
            movieDataManager = MovieDataManager.getInstance();
            pelicula = movieDataManager.getMovieById(i);
            assertTrue(pelicula.getTitle().equals(name));
        }catch (Exception e){
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
