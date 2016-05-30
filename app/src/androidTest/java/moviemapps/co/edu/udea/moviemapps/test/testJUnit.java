package moviemapps.co.edu.udea.moviemapps.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import co.edu.udea.moviemapps.activities.MainActivity;
import co.edu.udea.moviemapps.model.Pelicula;
import co.edu.udea.moviemapps.persistence.MovieDataManager;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Created by SA on 17/04/2016.
 */
public class testJUnit {
    MovieDataManager movieDataManager = null;
    Pelicula pelicula = null;
    String name = "Batman v Superman";
    int i;
    @Before
    public void setup() {
        pelicula = Mockito.mock(Pelicula.class);
        i = 21312;
        when(pelicula.getId()).thenReturn(i);
        when(pelicula.getTitle()).thenReturn(name);
    }

    @After
    public void tearDown(){

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
        pelicula.setId(i);
        pelicula.setTitle(name);
        movieDataManager = MovieDataManager.getInstance();
        pelicula = movieDataManager.getMovieById(i);
        assertTrue(pelicula.getTitle().equals(name));
    }
}
