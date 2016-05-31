package co.edu.udea.moviemapps.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class MapRobotium extends ActivityInstrumentationTestCase2 {
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
    public MapRobotium() throws ClassNotFoundException {
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
  
	public void testRun() {
        //Wait for activity: 'co.edu.udea.moviemapps.activities.MainActivity'
		solo.waitForActivity("MainActivity", 2000);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Nearby cinemas FrameLayout
		solo.clickInRecyclerView(3, 0);
        //Set default small timeout to 10975 milliseconds
		Timeout.setSmallTimeout(10975);
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Set default small timeout to 11750 milliseconds
		Timeout.setSmallTimeout(11750);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Showtimes FrameLayout
		solo.clickInRecyclerView(1, 0);
	}
}
