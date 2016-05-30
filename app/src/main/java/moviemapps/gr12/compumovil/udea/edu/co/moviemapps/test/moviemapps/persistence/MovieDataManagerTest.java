package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.test.moviemapps.persistence;



import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.MovieDataManager;

@RunWith(MockitoJUnitRunner.class)
public class MovieDataManagerTest extends AndroidTestCase{
    MovieDataManager movieDataManager;
    SQLiteDatabase sqLiteDatabase;
    Pelicula pelicula;
    @Before
    public void setUp() {
        sqLiteDatabase = Mockito.mock(SQLiteDatabase.class);

        pelicula = new Pelicula();
        pelicula.setId(1);
        pelicula.setTitle("Civil war");

        ContentValues cv = new ContentValues();
        cv.put("id", pelicula.getId());
        cv.put("title", pelicula.getTitle());
       /* cv.put("overview", pelicula.getOverview());
        cv.put("releasedate", pelicula.getPosterPath());
        cv.put("posterpatch", pelicula.getReleaseDate());
        */
        Mockito.when(sqLiteDatabase.update("movie", cv, "id =?", new String[]{String.valueOf(pelicula.getId())})).thenReturn(1);
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
