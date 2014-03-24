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

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.URL;

import static java.lang.String.format;

/**
 * @since 2014-03-19.
 */
public final class RemoteWebDriverProvider extends AbstractWebDriverProvider {

  private final URL url;
  private final Capabilities desiredCapabilities;

  public RemoteWebDriverProvider(@Nonnull final URL url,
                                 @Nonnull final Capabilities desiredCapabilities) {
    this.url = url;
    this.desiredCapabilities = desiredCapabilities;
  }

  @Override
  @Nonnull
  protected WebDriver instantiateDriver() {
    try {
      return new RemoteWebDriver(url, desiredCapabilities);
    } catch (final WebDriverException e) {
      throw new WebDriverInstantiationException(
              format("Failed to instantiate RemoteWebDriver with server URL %s and desired capabilities %s.",
                      url, desiredCapabilities),
              e
      );
    }
  }

}
