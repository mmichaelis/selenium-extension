package io.github.mmichaelis.selenium.server.inmemory;

import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
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
    final int port = getFreePort();
    final RemoteControlConfiguration configuration = new RemoteControlConfiguration();
    configuration.setPort(port);
    return configuration;
  }

  private int getFreePort() {
    final int port;
    try (ServerSocket s = new ServerSocket(0)) {
      port = s.getLocalPort();
    } catch (final IOException e) {
      throw new InMemorySeleniumServerException("Unable to determine free port.", e);
    }
    return port;
  }

}
