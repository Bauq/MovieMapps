package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.R;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.activities.MainActivity;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FrontEndTest {

    private String mStringToBetyped;
    private int idMovie;
    private Pelicula pelicula;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        idMovie = 209112;
        pelicula = new Pelicula();
        pelicula.setTitle("Batman v Superman: Dawn of Justice");
        mStringToBetyped = "Espresso";
    }


    @Test
    public void cargoRecicler() {
        // Type text and then press the button.

        onView(withId(R.id.my_recycler_view))
                .perform(click());


    }


}