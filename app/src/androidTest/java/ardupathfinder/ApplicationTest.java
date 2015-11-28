package ardupathfinder;

import android.app.Application;
import android.nfc.Tag;
import android.test.ApplicationTestCase;
import android.util.Log;

import ardupathfinder.message.Message;
import ardupathfinder.message.StraightMessage;
import ardupathfinder.message.TurnMessage;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "ApplicationTest";

    public ApplicationTest() {
        super(Application.class);
    }

}