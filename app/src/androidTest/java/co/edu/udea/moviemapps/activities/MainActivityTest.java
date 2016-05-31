package co.edu.udea.moviemapps.activities;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static android.support.test.InstrumentationRegistry.*;

import android.support.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.runner.RunWith;
import org.junit.Rule;
import org.junit.Test;

import android.view.View;
import android.view.ViewGroup;

import co.edu.udea.moviemapps.R;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGenerated() {

        // Click at ImageButton with child index 1 of parent with id R.id.toolbar
        onView(nthChildOf(withId(R.id.toolbar), 1)).perform(click());

        // Click at NavigationMenuItemView with child index 3 of parent with id R.id.design_navigation_view
        onView(withId(R.id.design_navigation_view)).perform(scrollToPosition(3));
        onView(nthChildOf(withId(R.id.design_navigation_view), 3)).perform(click());

        // Click at ImageButton with child index 1 of parent with id R.id.toolbar
        onView(nthChildOf(withId(R.id.toolbar), 1)).perform(click());

        // Click at NavigationMenuItemView with child index 1 of parent with id R.id.design_navigation_view
        onView(withId(R.id.design_navigation_view)).perform(scrollToPosition(1));
        onView(nthChildOf(withId(R.id.design_navigation_view), 1)).perform(click());

    }


    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return false;
                }
                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(group) && view.equals(group.getChildAt(childPosition));
            }
        };
    }


    public static ViewAction scrollToPosition(final int pos) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(android.support.v7.widget.RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "scroll to position";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((android.support.v7.widget.RecyclerView) view).scrollToPosition(pos);
            }
        };
    }
}