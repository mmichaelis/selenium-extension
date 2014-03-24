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
