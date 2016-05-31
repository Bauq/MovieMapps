package co.edu.udea.moviemapps.test;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;



@SuppressWarnings("rawtypes")
public class MapTest extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "co.edu.udea.moviemapps.activities.MainActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public MapTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testNearbyCinemas() throws UiObjectNotFoundException {
		solo.waitForActivity("MainActivity", 2000);
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
		solo.clickInRecyclerView(3, 1);
		Timeout.setSmallTimeout(15721);
		UiDevice device = UiDevice.getInstance(getInstrumentation());
		UiObject marker = device.findObject(new UiSelector().descriptionContains("Google Map"));
		assertEquals(true,marker.click());
	}

}
