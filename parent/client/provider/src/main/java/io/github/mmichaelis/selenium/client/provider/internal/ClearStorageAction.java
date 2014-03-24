package io.github.mmichaelis.selenium.client.provider.internal;

import io.github.mmichaelis.selenium.common.internal.PreconditionMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 2014-03-22.
 */
public final class ClearStorageAction implements Action {
  private static final Logger LOG = LoggerFactory.getLogger(ClearStorageAction.class);
  private static final String NOTE_POSSIBLY_UNEXPECTED_BEHAVIOR =
          "While this might be valid it might result in unexpected behavior of following tests.";

  private final WebDriver driver;

  public ClearStorageAction(@Nonnull final WebDriver driver) {
    checkNotNull(driver, PreconditionMessage.MUST_NOT_BE_NULL.format("driver"));
    this.driver = driver;
  }

  @Override
  public void perform() {
    driver.manage().deleteAllCookies();
    if (driver instanceof WebStorage) {
      final WebStorage storage = (WebStorage) driver;
      clearLocalStorage(storage);
      clearSessionStorage(storage);
    }

  }

  private void clearSessionStorage(final WebStorage storage) {
    try {
      storage.getSessionStorage().clear();
    } catch (final WebDriverException e) {
      LOG.info(
              "Failed to clear session storage. " +
                      NOTE_POSSIBLY_UNEXPECTED_BEHAVIOR);
      LOG.debug("{} does not seem to provide a session storage to clear. Thus ignoring exception.", e);
    }
  }

  private void clearLocalStorage(final WebStorage storage) {
    try {
      storage.getLocalStorage().clear();
    } catch (final WebDriverException e) {
      LOG.info(
              "Failed to clear local storage. " +
                      NOTE_POSSIBLY_UNEXPECTED_BEHAVIOR);
      LOG.debug("{} does not seem to provide a local storage to clear. Thus ignoring exception.", e);
    }
  }

}
