package co.edu.udea.moviemapps.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.model.Usuario;
import co.edu.udea.moviemapps.persistence.config.DataManager;

/**
 * Created by Samuel on 12/04/2016.
 */
public class UsuarioDataManager extends DataManager {
    private static UsuarioDataManager instance;

    public static final String TABLE_NAME = "usuario";

    public static final int COL_ID = 0,
            COL_NOMBRE = 1, COL_CORREO= 3, COL_FOTO = 2;


    public static final String[] COLUMNS = {
            "id",
            "nombre",
            "foto",
            "correo"
    };

    private UsuarioDataManager(Context context) {
        super(context);
    }

    public static UsuarioDataManager getInstance() {
        if (instance == null) {
            instance = new UsuarioDataManager(MovieMapps.getContext());
        }
        return instance;
    }


    private synchronized Usuario getUsuarioFromCursor(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getLong(COL_ID));
        usuario.setName(cursor.getString(COL_NOMBRE));
        usuario.setPhoto(cursor.getString(COL_FOTO));
        usuario.setEmail(cursor.getString(COL_CORREO));
        return usuario;
    }

    private synchronized ContentValues getContentValues(Usuario usuario) {
        ContentValues cv = new ContentValues();

        cv.put(COLUMNS[COL_NOMBRE], usuario.getName());
        cv.put(COLUMNS[COL_FOTO], usuario.getPhoto());
        cv.put(COLUMNS[COL_CORREO], usuario.getEmail());
        cv.put(COLUMNS[COL_ID], 1);

        return cv;
    }


    public synchronized long guardar(Usuario usuario) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insertOrThrow(TABLE_NAME, null, getContentValues(usuario));
        db.close();
        helper.close();
        usuario.setId(id);
        return id;
    }

    public synchronized void update(Usuario usuario) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABLE_NAME,
                getContentValues(usuario),
                COLUMNS[COL_ID] + "=?",
                new String[]{String.valueOf(usuario.getId())}
        );
        db.close();
        helper.close();
    }

    public Usuario getUsuarioById(int e) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                "id = ?", new String[]{String.valueOf(e)}, null, null, COLUMNS[COL_ID]);

        if (cursor.moveToNext()) {
            return getUsuarioFromCursor(cursor);
        }

        cursor.close();
        helper.close();
        return null;
    }
}



