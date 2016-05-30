package moviemapps.co.edu.udea.moviemapps.test;
import org.junit.Test;

import co.edu.udea.moviemapps.model.Usuario;
import co.edu.udea.moviemapps.persistence.UsuarioDataManager;

import static org.junit.Assert.*;
/**
 * Created by SA on 17/04/2016.
 */
public class test1 {

    @Test
    public void Insertar(){
        Usuario usuario = new Usuario();
        usuario.setEmail("samuelarenas@hotmail.es");
        usuario.setPhoto("");
        usuario.setName("Samuel Arenas");
        usuario.setId((long)123456789);
        long id =UsuarioDataManager.getInstance().guardar(usuario);
        assertEquals(id,(long)usuario.getId());
    }

    @Test
    public void UsuarioExiste(){
        Usuario usuario = UsuarioDataManager.getInstance().getUsuarioById(1);
        assertEquals("Samuel Arenas",usuario.getName());
    }
}
