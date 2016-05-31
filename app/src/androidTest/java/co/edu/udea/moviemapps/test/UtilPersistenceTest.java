package co.edu.udea.moviemapps.test;

import android.graphics.Bitmap;

import org.junit.Test;

import co.edu.udea.moviemapps.util.MovieMappsUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class UtilPersistenceTest {

    @Test
    public void getImageTest(){
        String imageName = "/zSouWWrySXshPCT4t3UKCQGayyo.jpg";
        Bitmap image = MovieMappsUtils.getImage(imageName);
        assertNotNull(image);
    }

    @Test
    public void getInexistentImageTest(){
        String imageName = "/aaa.jp";
        Bitmap image = MovieMappsUtils.getImage(imageName);
        assertNull(image);
    }
}
