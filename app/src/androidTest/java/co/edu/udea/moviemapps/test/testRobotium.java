package co.edu.udea.moviemapps.test;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.robotium.solo.Timeout;


@SuppressWarnings("rawtypes")
public class testRobotium extends ActivityInstrumentationTestCase2 {
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
    public testRobotium() throws ClassNotFoundException {
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
        //Set default small timeout to 15721 milliseconds
		Timeout.setSmallTimeout(15721);
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
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Click on android.view.TextureView
		solo.clickOnView(solo.getView(android.view.TextureView.class, 0));
        //Set default small timeout to 22170 milliseconds
		Timeout.setSmallTimeout(22170);
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Showtimes FrameLayout
		solo.clickInRecyclerView(1, 0);
        //Click on High-Rise 2016-05-13
		solo.clickInRecyclerView(5, 0);
        //Press menu back key
		solo.goBack();
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Click on Movies with your friends FrameLayout
		solo.clickInRecyclerView(2, 0);
        //Click on Iniciar sesión con Facebook
		solo.clickOnView(solo.getView("login_button"));
	}
}
