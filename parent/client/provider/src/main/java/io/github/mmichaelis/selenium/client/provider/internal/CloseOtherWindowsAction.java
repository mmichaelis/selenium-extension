package io.github.mmichaelis.selenium.client.provider.internal;

import io.github.mmichaelis.selenium.common.internal.PreconditionMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;

import javax.annotation.Nonnull;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 2014-03-22.
 */
public final class CloseOtherWindowsAction implements Action {
  private final WebDriver driver;

  public CloseOtherWindowsAction(@Nonnull final WebDriver driver) {
    checkNotNull(driver, PreconditionMessage.MUST_NOT_BE_NULL.format("driver"));
    this.driver = driver;
  }

  @Override
  public void perform() {
    final String currentWindowHandle = driver.getWindowHandle();
    final Set<String> windowHandles = driver.getWindowHandles();
    for (final String windowHandle : windowHandles) {
      if (!currentWindowHandle.equals(windowHandle)) {
        driver.switchTo().window(windowHandle);
        driver.close();
      }
    }
    driver.switchTo().window(currentWindowHandle);
  }
}
