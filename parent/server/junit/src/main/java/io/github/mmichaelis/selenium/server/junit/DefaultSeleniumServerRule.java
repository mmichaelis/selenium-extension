package io.github.mmichaelis.selenium.server.junit;

import io.github.mmichaelis.selenium.server.inmemory.DefaultInMemorySeleniumServer;
import io.github.mmichaelis.selenium.server.inmemory.InMemorySeleniumServer;
import org.junit.rules.ExternalResource;

import javax.annotation.Nullable;
import java.net.URL;

/**
 * Default rule for Selenium Server which starts the server before the test(s) and shuts it down afterwards.
 *
 * @since 2014-03-23.
 */
public final class DefaultSeleniumServerRule extends ExternalResource implements SeleniumServerRule {
  private InMemorySeleniumServer seleniumServer;

  @Override
  protected void before() {
    if (seleniumServer == null) {
      seleniumServer = new DefaultInMemorySeleniumServer();
    }
    seleniumServer.start();
  }

  @Override
  protected void after() {
    if (seleniumServer != null) {
      seleniumServer.stop();
    }
  }

  @Override
  @Nullable
  public URL getUrl() {
    if (seleniumServer != null) {
      return seleniumServer.getUrl();
    }
    return null;
  }
}
