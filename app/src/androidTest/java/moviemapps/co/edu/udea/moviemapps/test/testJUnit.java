package moviemapps.co.edu.udea.moviemapps.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.persistence.MovieDataManager;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Created by SA on 17/04/2016.
 */
public class testJUnit {
    MovieDataManager movieDataManager = null;
    Movie movie = null;
    String name = "Batman v Superman";
    int i;
    @Before
    public void setup() {
        movie = Mockito.mock(Movie.class);
        i = 21312;
        when(movie.getId()).thenReturn(i);
        when(movie.getTitle()).thenReturn(name);
    }

    @After
    public void tearDown(){

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
