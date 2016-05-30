package co.edu.udea.udea.edu.co.moviemapps.test;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Iterator;

import co.edu.udea.udea.edu.co.moviemapps.model.Pelicula;
import co.edu.udea.udea.edu.co.moviemapps.model.Usuario;
import co.edu.udea.udea.edu.co.moviemapps.persistence.UsuarioDataManager;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
/**
 * Created by SA on 17/04/2016.
 */
public class test1 {

    @Test
    public void Insertar(){
        Usuario usuario = new Usuario();
        usuario.setCorreo("samuelarenas@hotmail.es");
        usuario.setFoto("");
        usuario.setNombre("Samuel Arenas");
        usuario.setId((long)123456789);
        long id =UsuarioDataManager.getInstance().guardar(usuario);
        assertEquals(id,(long)usuario.getId());
    }

    @Test
    public void UsuarioExiste(){
        Usuario usuario = UsuarioDataManager.getInstance().getUsuarioById(1);
        assertEquals("Samuel Arenas",usuario.getNombre());
    }
}
