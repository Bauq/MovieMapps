package co.edu.udea.udea.edu.co.moviemapps.test.moviemapps.persistence;


<<<<<<< HEAD

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import static org.junit.Assert.*;
=======
import android.test.AndroidTestCase;
import static org.mockito.Mockito.when;
>>>>>>> 445bf9f362077a32d732eee917bb6f4138d79764

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.MovieDataManager;

<<<<<<< HEAD


=======
>>>>>>> 7cd10143c0915c87513e0fbf6fe31ac143189667
@RunWith(MockitoJUnitRunner.class)
<<<<<<< HEAD
public class MovieDataManagerTest extends AndroidTestCase{
=======
public class MovieDataManagerTest extends AndroidTestCase {
>>>>>>> 445bf9f362077a32d732eee917bb6f4138d79764
    MovieDataManager movieDataManager;
    SQLiteDatabase sqLiteDatabase;
    Pelicula pelicula;

    @Before
    public void setUp() {
<<<<<<< HEAD
        sqLiteDatabase = Mockito.mock(SQLiteDatabase.class);
=======
        movieDataManager = Mockito.mock(MovieDataManager.class);
>>>>>>> 445bf9f362077a32d732eee917bb6f4138d79764

        pelicula = new Pelicula();
        pelicula.setId(1);
        pelicula.setTitle("Civil war");
<<<<<<< HEAD

        ContentValues cv = new ContentValues();
        cv.put("id", pelicula.getId());
        cv.put("title", pelicula.getTitle());
       /* cv.put("overview", pelicula.getOverview());
        cv.put("releasedate", pelicula.getPosterPath());
        cv.put("posterpatch", pelicula.getReleaseDate());
        */
        Mockito.when(sqLiteDatabase.update("movie", cv, "id =?", new String[]{String.valueOf(pelicula.getId())})).thenReturn(1);
=======
        pelicula.setOverview("Muy buena");
        when(movieDataManager.getMovieById(1)).thenReturn(pelicula);
        when(movieDataManager.guardar(pelicula)).thenReturn(1);
        when(movieDataManager.update(pelicula)).thenReturn(1);
>>>>>>> 445bf9f362077a32d732eee917bb6f4138d79764
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
<<<<<<< HEAD
=======



>>>>>>> 7cd10143c0915c87513e0fbf6fe31ac143189667
}
