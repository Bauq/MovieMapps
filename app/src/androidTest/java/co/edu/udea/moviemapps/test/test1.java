package co.edu.udea.moviemapps.test;
import org.junit.Test;

import co.edu.udea.moviemapps.model.User;
import co.edu.udea.moviemapps.persistence.UserDataManager;

import static org.junit.Assert.*;

public class test1 {

    @Test
    public void Insertar(){
        User user = new User();
        user.setEmail("samuelarenas@hotmail.es");
        user.setPhoto("");
        user.setName("Samuel Arenas");
        user.setId((long)123456789);
        long id = UserDataManager.getInstance().insert(user);
        assertEquals(id,(long) user.getId());
    }

    @Test
    public void UsuarioExiste(){
        User user = UserDataManager.getInstance().getUserById(1);
        assertEquals("Samuel Arenas", user.getName());
    }
}
