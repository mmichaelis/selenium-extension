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

import javax.annotation.Nonnull;

/**
 * Holds information on a started WebDriver instance. Information are required for example to remove
 * shutdown hooks or to return to the browsers start URL.
 *
 * @since 2014-03-21.
 */
public interface WebDriverInformation {
  /**
   * The initial URL the browser started with. Most likely something like {@code about:blank}.
   *
   * @return initial URL
   */
  String getInitialUrl();

  /**
   * Retrieve the WebDriver instance.
   *
   * @return WebDriver instance
   */
  @Nonnull
  WebDriver getDriver();

  /**
   * Retrieve the thread which is defined as shutdown hook to quit the WebDriver instance (and thus
   * will close the browser.
   *
   * @return Shutdown-Hook Thread
   */
  @Nonnull
  Thread getShutdownHook();
}
