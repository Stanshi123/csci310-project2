package unitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SignUpTest.class, LoginTest.class, SaveToHistoryServletTest.class,
		DeleteCollageTest.class,
		APICommunicatorTest.class, CollageBuilderTest.class,
		ServerTest.class, ServletEngineTest.class, JSPContentTest.class,
	 	CollageBuilderBugTest.class,  CollageHistoryServletTest.class
})
public class AllTests {

}
