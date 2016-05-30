package co.edu.udea.udea.edu.co.moviemapps.model;

/**
 * Created by Samuel on 12/04/2016.
 */

public class Usuario {

    private Long id;
    private String nombre;
    private String foto;
    private String correo;

    public Usuario() {
        this.id = (long) 1;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}