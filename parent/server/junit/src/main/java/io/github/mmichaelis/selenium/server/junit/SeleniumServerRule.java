package io.github.mmichaelis.selenium.server.junit;

import org.junit.rules.TestRule;

import javax.annotation.Nullable;
import java.net.URL;

/**
 * <p>
 * Rule which starts a Selenium Server before tests and stops it afterwards. Best to be used
 * as ClassRule if you don't need to start and stop the Selenium Server between tests.
 * </p>
 *
 * @since 2014-03-23.
 */
public interface SeleniumServerRule extends TestRule {
  @Nullable
  URL getUrl();
}
