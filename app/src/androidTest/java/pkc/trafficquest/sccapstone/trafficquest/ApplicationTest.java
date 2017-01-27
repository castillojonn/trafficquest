package pkc.trafficquest.sccapstone.trafficquest;

import android.app.Application;
import android.test.ApplicationTestCase;
import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    //Login stuff for an existing account
    public static final String loginEmail = "castilj@email.sc.edu";
    public static final String loginPW = "student";

    public void loginTest(String email, String password) {
        //Test to see if app can login to existing account
        //Click login
        //Enter login email
        //Enter password
        //Click login to login
        //Check to see if it logged in
    }

    public void signupTest() {
        //Create new account //Format will probably be student##@email.com
        //Create password //Will probably be same password
        //ReEnter password
        //Click Sign Up
        //Check to see if sign up successful
    }

    public void loginReject() {
        //Click login
        //Enter non-existent account //notastudent@email.com
        //enter password
        //Check to see if login failed
    }
    public void requestData() {
        //Login
        //Click Request Data
        //Check to see if it worked
    }
}