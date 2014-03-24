package io.github.mmichaelis.selenium.client.provider;

import io.github.mmichaelis.selenium.client.provider.internal.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

import static java.lang.Runtime.getRuntime;

/**
 * @since 2014-03-21.
 */
public abstract class AbstractWebDriverProvider implements WebDriverProvider {
  private static final Logger LOG = LoggerFactory.getLogger(RemoteWebDriverProvider.class);

  private static final String SHUTDOWN_GROUP_NAME = "WebDriverShutdownGroup";
  private static final ThreadGroup WEBDRIVER_SHUTDOWN_GROUP = new ThreadGroup(SHUTDOWN_GROUP_NAME);
  private static final ThreadLocal<WebDriverInformation> WEB_DRIVER_INFORMATION_THREAD_LOCAL = new ThreadLocal<>();
  private static final Augmenter DEFAULT_AUGMENTER = new SilentAugmenter();

  /**
   * <p>
   * Needs to instantiate a WebDriver instance. If anything fails use
   * {@link WebDriverInstantiationException} to
   * inform on this failure.
   * </p>
   * <p>
   * Instances of RemoteWebDriver will be augmented in order to provide additional abilities like
   * Web Storage access.
   * </p>
   *
   * @return instantiate WebDriver instance
   * @throws WebDriverInstantiationException
   */
  @Nonnull
  protected abstract WebDriver instantiateDriver();

  @Override
  @Nonnull
  public final WebDriver get() {
    if (!isStarted()) {
      startWebDriver();
    }
    return WEB_DRIVER_INFORMATION_THREAD_LOCAL.get().getDriver();
  }

  @Override
  public boolean isStarted() {
    return WEB_DRIVER_INFORMATION_THREAD_LOCAL.get() != null;
  }

  @Override
  public final void reset() {
    if (isStarted()) {
      final WebDriverInformation driverInformation = WEB_DRIVER_INFORMATION_THREAD_LOCAL.get();
      new CloseOtherWindowsAction(driverInformation.getDriver()).perform();
      goHome();
      new ClearStorageAction(driverInformation.getDriver()).perform();
    }
  }

  /**
   * <p>
   * Returns to the initial page the browser has been started with. If the browser has not been started yet, it will
   * be started.
   * </p>
   */
  private void goHome() {
    final WebDriverInformation driverInformation = WEB_DRIVER_INFORMATION_THREAD_LOCAL.get();
    final WebDriver driver = driverInformation.getDriver();
    final String initialUrl = driverInformation.getInitialUrl();
    LOG.info("Going back to URL: {}", initialUrl);
    driver.navigate().to(initialUrl);
  }

  @Override
  public final void quit() {
    if (isStarted()) {
      final Thread hook = WEB_DRIVER_INFORMATION_THREAD_LOCAL.get().getShutdownHook();
      try {
        getRuntime().removeShutdownHook(hook);
      } finally {
        hook.start();
        WEB_DRIVER_INFORMATION_THREAD_LOCAL.remove();
      }
    }
  }

  protected WebDriverInformation createWebDriverInformation(final WebDriver driver, final Thread hook) {
    return new WebDriverInformationImpl(driver, hook);
  }

  protected Runnable createQuitWebDriverRunnable(final WebDriver driver) {
    return new QuitWebDriverRunnable(driver);
  }

  protected WebDriver augment(final WebDriver driver) {
    return DEFAULT_AUGMENTER.augment(driver);
  }

  private void startWebDriver() {
    final WebDriver driver = augment(instantiateDriver());
    final Thread hook = new Thread(WEBDRIVER_SHUTDOWN_GROUP, createQuitWebDriverRunnable(driver));
    getRuntime().addShutdownHook(hook);
    WEB_DRIVER_INFORMATION_THREAD_LOCAL.set(createWebDriverInformation(driver, hook));
  }


}
