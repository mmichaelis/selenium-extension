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

import io.github.mmichaelis.selenium.common.internal.NetUtils;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static java.lang.String.format;

/**
 * Default implementation for SeleniumServer-Provider starting it at a random free port.
 *
 * @since 2014-03-28.
 */
public final class DefaultSeleniumServerProvider implements SeleniumServerProvider {
  private static final ThreadLocal<SeleniumServer> SERVER_THREAD_LOCAL = new ThreadLocal<>();

  @Nonnull
  @Override
  public SeleniumServer get() {
    if (!isStarted()) {
      start();
    }
    return SERVER_THREAD_LOCAL.get();
  }

  @Override
  public void start() {
    if (!isStarted()) {
      final SeleniumServer seleniumServer = instantiateServer();
      SERVER_THREAD_LOCAL.set(seleniumServer);
      try {
        seleniumServer.start();
      } catch (final Exception e) {
        throw new SeleniumServerProviderException("Failed to start Selenium Server.", e);
      }
    }
  }

  @Override
  public void stop() {
    if (isStarted()) {
      try {
        SERVER_THREAD_LOCAL.get().stop();
      } finally {
        SERVER_THREAD_LOCAL.remove();
      }
    }
  }

  @Override
  public boolean isStarted() {
    return SERVER_THREAD_LOCAL.get() != null;
  }

  @Nonnull
  @Override
  public URL getUrl() {
    if (!isStarted()) {
      throw new SeleniumServerProviderException("Selenium Server not started yet.");
    }
    final SeleniumServer server = SERVER_THREAD_LOCAL.get();
    final int port = server.getConfiguration().getPortDriversShouldContact();
    final String urlString = format("http://localhost:%d/wd/hub", port);
    try {
      return URI.create(urlString).toURL();
    } catch (final MalformedURLException e) {
      throw new SeleniumServerProviderException(format("Please file a bug report: Invalid URL: %s", urlString), e);
    }
  }

  /**
   * <p>
   * Instantiates a SeleniumServer instance.
   * </p>
   *
   * @return instantiate SeleniumServer instance
   * @throws SeleniumServerProviderException in failure
   */
  @Nonnull
  @CheckReturnValue
  private SeleniumServer instantiateServer() {
    final SeleniumServer newServer;
    final RemoteControlConfiguration configuration = getConfiguration();
    try {
      newServer = new SeleniumServer(false, configuration);
    } catch (final Exception e) {
      throw new SeleniumServerProviderException("Unable to instantiate SeleniumServer.", e);
    }
    return newServer;

  }

  @Nonnull
  @CheckReturnValue
  private RemoteControlConfiguration getConfiguration() {
    final int port;
    try {
      port = NetUtils.getFreePort();
    } catch (final IOException e) {
      throw new SeleniumServerProviderException("Unable to determine free port.", e);
    }
    final RemoteControlConfiguration configuration = new RemoteControlConfiguration();
    configuration.setPort(port);
    return configuration;
  }
}
