package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.test.moviemapps.persistence;



import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.MovieDataManager;
@RunWith(MockitoJUnitRunner.class)
public class MovieDataManagerTest {
    MovieDataManager movieDataManager;
    Pelicula pelicula;
    @Before
    public void setUp() {
        movieDataManager = Mockito.mock(MovieDataManager.class);
        pelicula = new Pelicula();
        pelicula.setId(1);
        pelicula.setTitle("Civil war");
        Mockito.when(movieDataManager.getMovieById(1)).thenReturn(pelicula);
        Mockito.when(movieDataManager.guardar(pelicula)).thenReturn(1);
        Mockito.when(movieDataManager.update(pelicula)).thenReturn(1);
    }

	@Test
	public void testGetInstance() {
		MovieDataManager movieDataManager = MovieDataManager.getInstance();
		assertNotNull(movieDataManager);
	}

	@Test
	public void testGuardar() {
        int idPelicula = movieDataManager.guardar(pelicula);
        assertEquals(1, idPelicula);
    }

	@Test
	public void testUpdate() {
        int filasModificadas = movieDataManager.update(pelicula);
        assertEquals(1, filasModificadas);
	}

	@Test
	public void testGetMovieById() {
        Pelicula peliculaRetornada = movieDataManager.getMovieById(1);
        assertEquals(pelicula, peliculaRetornada);
	}

}
