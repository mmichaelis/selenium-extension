/*
 * Copyright 2014 Mark Michaelis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mmichaelis.selenium.client.provider;

import io.github.mmichaelis.selenium.client.provider.internal.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import static java.lang.Runtime.getRuntime;

/**
 * <p>
 * Abstract provider for WebDriver instances. WebDriver instances will be available per Thread thus can
 * easily be shared among different wrappers like PageObjects etc. required for one test run.
 * </p>
 * <p>
 * All WebDriver instances are automatically registered to quit on shutdown thus you are not required
 * to call {@link #quit()} by yourself.
 * </p>
 *
 * @since 2014-03-21.
 */
public abstract class AbstractWebDriverProvider implements WebDriverProvider {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractWebDriverProvider.class);

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
  @CheckReturnValue
  protected abstract WebDriver instantiateDriver();

  @Override
  @Nonnull
  public final WebDriver get() {
    if (!isStarted()) {
      startWebDriver();
    }
    return WEB_DRIVER_INFORMATION_THREAD_LOCAL.get().getDriver();
  }

  @CheckReturnValue
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

  /**
   * <p>
   * Creates an WebDriverInformation object which will provide all necessary information to keep control
   * of the started WebDriver instance.
   * </p>
   *
   * @param driver WebDriver instance which just got started
   * @param hook   Shutdown Hook registered to automatically quit WebDriver instance on JVM shutdown
   * @return information object
   */
  @Nonnull
  @CheckReturnValue
  protected WebDriverInformation createWebDriverInformation(@Nonnull final WebDriver driver,
                                                            @Nonnull final Thread hook) {
    return new WebDriverInformationImpl(driver, hook);
  }

  /**
   * Create runnable to quit the WebDriver instance at shutdown (and when enforced via {@link #quit()}.
   *
   * @param driver WebDriver instance to quit on shutdown
   * @return runnable
   */
  @Nonnull
  @CheckReturnValue
  protected Runnable createQuitWebDriverRunnable(@Nonnull final WebDriver driver) {
    return new QuitWebDriverRunnable(driver);
  }

  /**
   * Augments the WebDriver instance directly after creation. By default
   * {@link #DEFAULT_AUGMENTER} will be used.
   *
   * @param driver WebDriver instance to augment
   * @return augmented WebDriver instance
   */
  protected WebDriver augment(final WebDriver driver) {
    return DEFAULT_AUGMENTER.augment(driver);
  }

  /**
   * WebDriver instance will be started, augmented and registered for quit on shutdown.
   */
  private void startWebDriver() {
    final WebDriver driver = augment(instantiateDriver());
    final Thread hook = new Thread(WEBDRIVER_SHUTDOWN_GROUP, createQuitWebDriverRunnable(driver));
    getRuntime().addShutdownHook(hook);
    WEB_DRIVER_INFORMATION_THREAD_LOCAL.set(createWebDriverInformation(driver, hook));
  }

}
