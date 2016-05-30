package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;

import java.util.ArrayList;
import java.util.List;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.activities.MovieMapps;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Cinema;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.config.DataBaseHelper;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.config.DataManager;

/**
 * Created by hp on 29/05/2016.
 */
public class CinemaDataManager extends DataManager {

    private static CinemaDataManager instance;

    public static final String TABLE_NAME = "cinema";

    public static final int COL_ID = 0,
            COL_NOMBRE = 1,
            COL_LATITUD = 2,
            COL_LONGITUD = 3;

    public static final String[] COLUMNS = {
            "id",
            "nombre",
            "latitud",
            "longitud"
    };

    public CinemaDataManager(Context context) {
        super(context);
    }

    public static CinemaDataManager getInstance() {
        if (instance == null) {
            instance = new CinemaDataManager(MovieMapps.getContext());
        }
        return instance;
    }

    private synchronized  Cinema getCinemaFromCursor(Cursor cursor){
        Cinema cinema = new Cinema();
        cinema.setId(cursor.getInt(COL_ID));
        cinema.setNombre(cursor.getString(COL_NOMBRE));
        cinema.setLatitud(cursor.getDouble(COL_LATITUD));
        cinema.setLongitud(cursor.getDouble(COL_LONGITUD));
        return cinema;
    }

    private synchronized ContentValues getContentValues(Cinema cinema){
        ContentValues cv = new ContentValues();
        cv.put(COLUMNS[COL_ID], cinema.getId());
        cv.put(COLUMNS[COL_NOMBRE], cinema.getNombre());
        cv.put(COLUMNS[COL_LATITUD], cinema.getLatitud());
        cv.put(COLUMNS[COL_LONGITUD], cinema.getLongitud());
        return cv;
    }

    public List<Cinema> getAllCinemas() {
        List<Cinema> cinemas = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMNS[COL_ID]);

        Cinema cinema;
        while (cursor.moveToNext()) {
            cinema = getCinemaFromCursor(cursor);
            cinemas.add(cinema);
        }

        cursor.close();
        helper.close();
        return cinemas;
    }

}