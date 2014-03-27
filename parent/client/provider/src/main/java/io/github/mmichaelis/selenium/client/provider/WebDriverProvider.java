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

import org.openqa.selenium.WebDriver;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * Strategy to retrieve a WebDriver instance.
 *
 * @since 2014-03-19.
 */
public interface WebDriverProvider {
  /**
   * <p>
   * Retrieve a WebDriver instance. Implementations should restrict the WebDriver instance to one
   * per thread.
   * </p>
   *
   * @return WebDriver instance
   * @throws WebDriverProviderException on problems instantiating the
   *                                    WebDriver instance
   */
  @Nonnull
  WebDriver get();

  /**
   * <p>
   * Check if there is already an browser opened for this thread.
   * </p>
   *
   * @return {@code true} if browser is started, {@code false} if not
   */
  @CheckReturnValue
  boolean isStarted();

  /**
   * <p>
   * Best effort reset the Browser state to its initial state. Might be used a alternative to
   * {@link #quit()} to run multiple tests without restarting the browser always.
   * </p>
   * <p>
   * Ignored when there is no open browser session.
   * </p>
   */
  void reset();

  /**
   * <p>
   * Explicitly quit the given WebDriver session. When {@link #get()} is performed the next time a new
   * session will be started.
   * </p>
   * <p>
   * Ignored when there is no open browser session.
   * </p>
   */
  void quit();
}
