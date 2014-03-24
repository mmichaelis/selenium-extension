package io.github.mmichaelis.selenium.server.inmemory;

import javax.annotation.Nullable;
import java.net.URL;

/**
 * <p>
 * Controls an In-Memory-Selenium-Server started at any free port.
 * </p>
 *
 * @since 2014-03-23.
 */
public interface InMemorySeleniumServer {
  /**
   * <p>
   * Starts the In-Memory-Selenium-Server. If called multiple times the server just keeps started.
   * </p>
   *
   * @throws InMemorySeleniumServerException if starting the Selenium Server fails
   */
  void start();

  /**
   * <p>
   * Stops the In-Memory-Selenium-Server. If already stopped nothing will happen.
   * </p>
   */
  void stop();

  /**
   * Indicate if the In-Memory-Server is started.
   *
   * @return {@code true} if it is started, {@code false} if not
   */
  boolean isStarted();

  /**
   * <p>
   * Retrieve the Selenium Server URL to contact for RemoteWebDriver.
   * </p>
   *
   * @return URL of Selenium Server or {@code null} if Selenium Server is not started
   */
  @Nullable
  URL getUrl();
}
