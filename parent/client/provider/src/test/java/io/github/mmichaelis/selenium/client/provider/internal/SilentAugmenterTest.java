package io.github.mmichaelis.selenium.client.provider.internal;

import io.github.mmichaelis.selenium.server.junit.DefaultSeleniumServerRule;
import io.github.mmichaelis.selenium.server.junit.SeleniumServerRule;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Tests {@link io.github.mmichaelis.selenium.client.provider.internal.SilentAugmenter}.
 *
 * @since 2014-03-22.
 */
public class SilentAugmenterTest {
  @ClassRule
  public static final SeleniumServerRule SELENIUM_SERVER_RULE = new DefaultSeleniumServerRule();
  @Rule
  public ErrorCollector errorCollector = new ErrorCollector();

  @Test
  public void augments_remote_webdriver_instance() throws Exception {
    final WebDriver driver = new RemoteWebDriver(SELENIUM_SERVER_RULE.getUrl(),
            DesiredCapabilities.htmlUnitWithJs());
    final WebDriver augmented1 = new SilentAugmenter().augment(driver);
    errorCollector.checkThat("First augmentation should have changed instance.", driver,
            Matchers.not(Matchers.sameInstance(augmented1)));
    final WebDriver augmented2 = new SilentAugmenter().augment(augmented1);
    errorCollector.checkThat("Second augmentation should have changed instance.", augmented1,
            Matchers.not(Matchers.sameInstance(augmented2)));
  }
}
