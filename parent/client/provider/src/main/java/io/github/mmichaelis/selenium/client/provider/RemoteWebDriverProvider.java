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

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URL;

import static com.google.common.base.Objects.toStringHelper;
import static io.github.mmichaelis.selenium.common.internal.PreconditionMessage.MUST_NOT_BE_NULL;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * <p>
 * Provides remote web driver instances with given capabilities.
 * </p>
 *
 * @since 2014-03-19.
 */
public final class RemoteWebDriverProvider extends AbstractWebDriverProvider {
  private static final String P_DESIRED_CAPABILITIES = "desiredCapabilities";
  private static final String P_URL = "url";
  private final URL url;
  private final Capabilities desiredCapabilities;

  /**
   * <p>
   * Create a provider of remote WebDriver instances.
   * </p>
   *
   * @param url                 URL of the Selenium Server like {@code http://localhost:4444/wd/hub}; if {@code null}
   *                            it falls back to WebDrivers default handling to determine the server address
   * @param desiredCapabilities specifies the capabilities you want the started browser to have - like for example
   *                            which browser should be started.
   */
  public RemoteWebDriverProvider(@Nullable final URL url,
                                 @Nonnull final Capabilities desiredCapabilities) {
    requireNonNull(desiredCapabilities, MUST_NOT_BE_NULL.format(P_DESIRED_CAPABILITIES));
    this.url = url;
    this.desiredCapabilities = desiredCapabilities;
  }

  @CheckReturnValue
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

  @Override
  @CheckReturnValue
  public String toString() {
    return toStringHelper(this)
            .add(P_URL, url)
            .add(P_DESIRED_CAPABILITIES, desiredCapabilities)
            .toString();
  }
}
