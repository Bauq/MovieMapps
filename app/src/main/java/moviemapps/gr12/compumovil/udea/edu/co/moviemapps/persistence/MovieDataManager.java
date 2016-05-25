package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.activities.MovieMapps;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Movie;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.config.DataManager;

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


    private synchronized Movie getMovieFromCursor(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(COL_ID));
        movie.setTitle(cursor.getString(COL_TITLE));
        movie.setOverview(cursor.getString(COL_OVERVIEW));
        movie.setPosterPath(cursor.getString(COL_POSTER_PATCH));
        movie.setReleaseDate(cursor.getString(COL_RELEASEDATE));
        return movie;
    }

    private synchronized ContentValues getContentValues(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMNS[COL_ID], movie.getId());
        cv.put(COLUMNS[COL_TITLE], movie.getTitle());
        cv.put(COLUMNS[COL_OVERVIEW], movie.getOverview());
        cv.put(COLUMNS[COL_POSTER_PATCH], movie.getPosterPath());
        cv.put(COLUMNS[COL_RELEASEDATE], movie.getReleaseDate());
        return cv;
    }


    public synchronized int guardar(Movie movie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int id = (int) db.insertOrThrow(TABLE_NAME, null, getContentValues(movie));
        db.close();
        helper.close();
        movie.setId(id);
        return id;
    }

    public synchronized void update(Movie movie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABLE_NAME,
                getContentValues(movie),
                COLUMNS[COL_ID] + "=?",
                new String[]{String.valueOf(movie.getId())}
        );
        db.close();
        helper.close();
    }

    public Movie getMovieById(int e) {
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



