package co.edu.udea.moviemapps.test;


import android.database.sqlite.SQLiteConstraintException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import co.edu.udea.moviemapps.model.Cinema;
import co.edu.udea.moviemapps.persistence.CinemaDataManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



public class CinemaPersistenceTest {
    CinemaDataManager cinemaDataManager;

    @Before
    public void setUp() {
        cinemaDataManager =  CinemaDataManager.getInstance();
    }

    @Test
    public void testGetCinemas() {
        List<Cinema> Cinemas = cinemaDataManager.getAllCinemas();
        assertEquals(4 , Cinemas.size());
    }

}
