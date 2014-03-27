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

package io.github.mmichaelis.selenium.client.provider.internal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

import static com.google.common.base.Objects.toStringHelper;
import static io.github.mmichaelis.selenium.common.internal.PreconditionMessage.MUST_NOT_BE_NULL;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Runnable which will end the given WebDriver instance. Typically to be used as shutdown hook
 * to ensure that the WebDriver instance eventually quits.
 *
 * @since 2014-03-21.
 */
public final class QuitWebDriverRunnable implements Runnable {
  private static final Logger LOG = LoggerFactory.getLogger(QuitWebDriverRunnable.class);
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
    try {
      driver.quit();
    } catch (final UnreachableBrowserException e) {
      LOG.debug(format("Caught exception while closing WebDriver instance: %s. Assuming that there is no need to close.", driver), e);
    } catch (final WebDriverException e) {
      LOG.warn(format("Ignoring caught exception while closing WebDriver instance: %s", driver), e);
    }
  }

  @Override
  public String toString() {
    return toStringHelper(this)
            .add(P_DRIVER, driver)
            .toString();
  }
}
