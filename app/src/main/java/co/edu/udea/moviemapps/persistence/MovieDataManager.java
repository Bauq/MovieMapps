package co.edu.udea.moviemapps.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.model.Pelicula;
import co.edu.udea.moviemapps.persistence.config.DataManager;

/**
 * Created by Samuel on 12/04/2016.
 */
public class MovieDataManager extends DataManager {
    private static MovieDataManager instance;

    public static final String TABLE_NAME = "movie";

    public static final int COL_ID = 0,
            COL_TITLE = 1,
            COL_OVERVIEW = 2,
            COL_RELEASEDATE = 3,
            COL_POSTER_PATCH = 4;

    public static final String[] COLUMNS = {
            "id",
            "title",
            "overview",
            "releasedate",
            "posterpatch"
    };

    private MovieDataManager(Context context) {
        super(context);
    }

    public static MovieDataManager getInstance() {
        if (instance == null) {
            instance = new MovieDataManager(MovieMapps.getContext());
        }
        return instance;
    }


    private synchronized Pelicula getMovieFromCursor(Cursor cursor) {
        Pelicula pelicula = new Pelicula();
        pelicula.setId(cursor.getInt(COL_ID));
        pelicula.setTitle(cursor.getString(COL_TITLE));
        pelicula.setOverview(cursor.getString(COL_OVERVIEW));
        pelicula.setPosterPath(cursor.getString(COL_POSTER_PATCH));
        pelicula.setReleaseDate(cursor.getString(COL_RELEASEDATE));
        return pelicula;
    }

    private synchronized ContentValues getContentValues(Pelicula pelicula) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMNS[COL_ID], pelicula.getId());
        cv.put(COLUMNS[COL_TITLE], pelicula.getTitle());
        cv.put(COLUMNS[COL_OVERVIEW], pelicula.getOverview());
        cv.put(COLUMNS[COL_POSTER_PATCH], pelicula.getPosterPath());
        cv.put(COLUMNS[COL_RELEASEDATE], pelicula.getReleaseDate());
        return cv;
    }


    public synchronized int guardar(Pelicula pelicula) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int id = (int) db.insertOrThrow(TABLE_NAME, null, getContentValues(pelicula));
        db.close();
        helper.close();
        pelicula.setId(id);
        return id;
    }

    public synchronized int update(Pelicula pelicula) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int update = db.update(TABLE_NAME,
                getContentValues(pelicula),
                COLUMNS[COL_ID] + "=?",
                new String[]{String.valueOf(pelicula.getId())}
        );
        db.close();
        helper.close();
        return update;
    }

    public Pelicula getMovieById(int e) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                "id = ?", new String[]{String.valueOf(e)}, null, null, COLUMNS[COL_ID]);

        if (cursor.moveToNext()) {
            return getMovieFromCursor(cursor);
        }

        cursor.close();
        helper.close();
        return null;
    }

}



