package io.github.mmichaelis.selenium.client.provider.internal;

import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;

import static com.google.common.base.Objects.toStringHelper;
import static io.github.mmichaelis.selenium.common.internal.PreconditionMessage.MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * Runnable which will end the given WebDriver instance. Typically to be used as shutdown hook
 * to ensure that the WebDriver instance eventually quits.
 *
 * @since 2014-03-21.
 */
public final class QuitWebDriverRunnable implements Runnable {
  private static final String P_DRIVER = "driver";
  @Nonnull
  private final WebDriver driver;

  /**
   * Constructor providing the WebDriver instance to quit.
   *
   * @param driver WebDriver instance to quit
   * @throws java.lang.NullPointerException if driver is {@code null}
   */
  public QuitWebDriverRunnable(@Nonnull final WebDriver driver) {
    requireNonNull(driver, MUST_NOT_BE_NULL.format(P_DRIVER));
    this.driver = driver;
  }

  /**
   * When running quits the provided WebDriver instance.
   */
  @Override
  public void run() {
    driver.quit();
  }

  @Override
  public String toString() {
    return toStringHelper(this)
            .add(P_DRIVER, driver)
            .toString();
  }
}
