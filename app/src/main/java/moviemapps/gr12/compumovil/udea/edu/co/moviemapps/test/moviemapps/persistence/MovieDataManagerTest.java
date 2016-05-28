package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.test.moviemapps.persistence;

import android.test.mock.MockResources;

import static org.junit.Assert.*;

import org.junit.Test;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.MovieDataManager;

public class MovieDataManagerTest {

	@Test
	public void testGetInstance() {
		MovieDataManager movieDataManager = MovieDataManager.getInstance();
		assertNotNull(movieDataManager);
	}

	@Test
	public void testGuardar() {
		MovieDataManager movieDataManager;

	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMovieById() {
		fail("Not yet implemented");
	}

}
