package pkc.trafficquest.sccapstone.trafficquest;

/**
 * Created by Keegan on 4/24/2017.
 */

import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;




@RunWith(JUnit4.class)
@LargeTest
public class JUnitTestT {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);




    @Test
    public void TesttheApp() {
       // onView(withId(R.id.action_settings)).perform(click());
    }




}