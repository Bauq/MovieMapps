package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.test;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Iterator;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Usuario;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.UsuarioDataManager;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
/**
 * Created by SA on 17/04/2016.
 */
public class test1 {


    @Test
    public void testId()  {
        //  create mock
        Pelicula test = Mockito.mock(Pelicula.class);

        // define return value for method getUniqueId()
        when(test.getId()).thenReturn(43);

        // use mock in test....
        assertEquals((long)test.getId(), 43);
    }


    // Demonstrates the return of multiple values
    @Test
    public void testMoreThanOneReturnValue()  {
        Iterator i= mock(Iterator.class);
        when(i.next()).thenReturn("Mockito").thenReturn("rocks");
        String result=i.next()+" "+i.next();
        //assert
        assertEquals("Mockito rocks", result);
    }

    // this test demonstrates how to return values based on the input
    @Test
    public void testReturnValueDependentOnMethodParameter()  {
        Comparable c= mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        //assert
        assertEquals(1,c.compareTo("Mockito"));
    }

// this test demonstrates how to return values independent of the input value

    @Test
    public void testReturnValue()  {
        Comparable c= mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        //assert
        assertEquals(-1 ,c.compareTo(9));
    }

// return a value based on the type of the provide parameter

    @Test
    public void testReturnValueInDependentOnMethodParameter()  {
        Comparable c= mock(Comparable.class);
        when(c.compareTo(isA(Pelicula.class))).thenReturn(0);
        //assert
        Pelicula todo = new Pelicula();
        assertEquals(todo ,c.compareTo(new Pelicula()));
    }

    @Test
    public void UsuarioExiste(){
        Usuario usuario =UsuarioDataManager.getInstance().getUsuarioById(1);
        assertEquals("Samuel Arenas",usuario.getNombre());
    }

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
}
