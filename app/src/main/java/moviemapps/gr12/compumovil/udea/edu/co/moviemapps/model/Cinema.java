package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model;

/**
 * Created by Brian on 05/05/2016.
 */
public class Cinema {

    String nombre;
    Double latitud, longitud;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
