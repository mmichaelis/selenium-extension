package io.github.mmichaelis.selenium.client.provider.internal;

import com.google.common.base.Objects;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;

import static io.github.mmichaelis.selenium.common.internal.PreconditionMessage.MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * Holds information on a started WebDriver instance. Information are required for example to remove
 * shutdown hooks or to return to the browsers start URL.
 *
 * @since 2014-03-21.
 */
public final class WebDriverInformationImpl implements WebDriverInformation {
  private static final String P_DRIVER = "driver";
  private static final String P_SHUTDOWN_HOOK = "shutdownHook";
  private static final String P_INITIAL_URL = "initialUrl";

  @Nonnull
  private final WebDriver driver;
  @Nonnull
  private final Thread shutdownHook;
  private final String initialUrl;

  /**
   * <p>
   * Create WebDriverInformation holder.
   * </p>
   *
   * @param driver       WebDriver instance to store
   * @param shutdownHook shutdown hook responsible for closing the WebDriver instance
   * @throws java.lang.NullPointerException if any of the parameters is {@code null}
   */
  public WebDriverInformationImpl(@Nonnull final WebDriver driver, @Nonnull final Thread shutdownHook) {
    requireNonNull(driver, MUST_NOT_BE_NULL.format(P_DRIVER));
    requireNonNull(shutdownHook, MUST_NOT_BE_NULL.format(P_SHUTDOWN_HOOK));
    this.driver = driver;
    initialUrl = driver.getCurrentUrl();
    this.shutdownHook = shutdownHook;
  }

  @Override
  public String getInitialUrl() {
    return initialUrl;
  }

  @Override
  @Nonnull
  public WebDriver getDriver() {
    return driver;
  }

  @Override
  @Nonnull
  public Thread getShutdownHook() {
    return shutdownHook;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
            .add(P_DRIVER, driver)
            .add(P_SHUTDOWN_HOOK, shutdownHook)
            .add(P_INITIAL_URL, initialUrl)
            .toString();
  }
}
