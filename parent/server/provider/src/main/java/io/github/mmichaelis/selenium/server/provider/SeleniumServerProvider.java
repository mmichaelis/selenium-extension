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

package io.github.mmichaelis.selenium.server.provider;

import org.openqa.selenium.server.SeleniumServer;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.net.URL;

/**
 * Provides one Selenium Server per Thread. If started the Selenium Server will be automatically registered
 * for shutdown via shutdown hook.
 *
 * @since 2014-03-28.
 */
public interface SeleniumServerProvider {
  /**
   * Access to the Selenium Server.
   *
   * @return Selenium Server instance; will be started if not yet done
   * @throws SeleniumServerProviderException if starting server fails
   */
  @Nonnull
  @CheckReturnValue
  SeleniumServer get();

  /**
   * <p>
   * If not yet started, starts the Selenium Server.
   * </p>
   *
   * @throws SeleniumServerProviderException if starting server fails
   */
  void start();

  /**
   * <p>
   * Stops the Selenium Server. If already stopped nothing will happen.
   * As server is registered for shutdown per shutdown hook it does not need to be explicitly called.
   * </p>
   */
  void stop();

  /**
   * Indicate if the Selenium Server is started.
   *
   * @return {@code true} if it is started, {@code false} if not
   */
  @CheckReturnValue
  boolean isStarted();

  /**
   * <p>
   * Retrieve the Selenium Server URL to contact for RemoteWebDriver.
   * </p>
   *
   * @return URL of Selenium Server
   * @throws SeleniumServerProviderException if server is not started
   */
  @Nonnull
  @CheckReturnValue
  URL getUrl();
}
