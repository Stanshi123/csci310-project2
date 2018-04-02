package unitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ APICommunicatorTest.class, CollageBuilderTest.class,
		ServerTest.class, ServletEngineTest.class, JSPContentTest.class, LoginTest.class,
	SignUpTest.class
})
public class AllTests {

}
