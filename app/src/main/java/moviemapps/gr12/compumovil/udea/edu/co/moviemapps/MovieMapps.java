package moviemapps.gr12.compumovil.udea.edu.co.moviemapps;

import android.app.Application;
import android.content.Context;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Usuario;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.UsuarioDataManager;


/**
 * @author Samuel Arenas
 * @version 1.0.0
 * @date  17/04/16

 */
public class MovieMapps extends Application {
    private static Context context;
    private static Usuario usuario;

    public static Usuario getUsuario() {
        if (usuario == null){
            usuario = UsuarioDataManager.getInstance().getUsuarioById(1);
        }
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        MovieMapps.usuario = usuario;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MovieMapps.context = context;
    }
}
