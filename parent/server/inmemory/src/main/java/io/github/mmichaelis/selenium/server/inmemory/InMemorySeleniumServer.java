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

package io.github.mmichaelis.selenium.server.inmemory;

import org.openqa.selenium.server.SeleniumServer;

import javax.annotation.CheckReturnValue;
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
   * Access to the in memory Selenium Server.
   *
   * @return Selenium Server or {@code null} if not yet started
   */
  @Nullable
  @CheckReturnValue
  SeleniumServer getSeleniumServer();

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
