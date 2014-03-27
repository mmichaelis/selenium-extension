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

import io.github.mmichaelis.selenium.common.internal.NetUtils;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Implementation of the InMemoryServer Starts the server at some random free port.
 *
 * @since 2014-03-23.
 */
public class DefaultInMemorySeleniumServer implements InMemorySeleniumServer {
  private SeleniumServer seleniumServer;

  @Override
  @Nullable
  @CheckReturnValue
  public SeleniumServer getSeleniumServer() {
    return seleniumServer;
  }

  @Override
  public void start() {
    if (seleniumServer == null) {
      seleniumServer = instantiateServer();
    }
    try {
      seleniumServer.start();
    } catch (final Exception e) {
      throw new InMemorySeleniumServerException("Unable to start SeleniumServer.", e);
    }
  }

  @Override
  public void stop() {
    if (seleniumServer != null) {
      seleniumServer.stop();
    }
  }

  @Override
  public boolean isStarted() {
    return seleniumServer != null && seleniumServer.getServer().isStarted();
  }

  @Nullable
  @Override
  public URL getUrl() {
    if (isStarted()) {
      final String urlString = String.format("http://localhost:%d/wd/hub", seleniumServer.getPort());
      try {
        return URI.create(urlString).toURL();
      } catch (final MalformedURLException e) {
        throw new InMemorySeleniumServerError(String.format("Please file a bug report: Invalid URL: %s", urlString), e);
      }
    }
    return null;
  }

  private SeleniumServer instantiateServer() {
    final SeleniumServer newServer;
    final RemoteControlConfiguration configuration = getConfiguration();
    try {
      newServer = new SeleniumServer(false, configuration);
    } catch (final Exception e) {
      throw new InMemorySeleniumServerException("Unable to instantiate SeleniumServer.", e);
    }
    return newServer;
  }

  private RemoteControlConfiguration getConfiguration() {
    final int port;
    try {
      port = NetUtils.getFreePort();
    } catch (final IOException e) {
      throw new InMemorySeleniumServerException("Unable to determine free port.", e);
    }
    final RemoteControlConfiguration configuration = new RemoteControlConfiguration();
    configuration.setPort(port);
    return configuration;
  }

}
