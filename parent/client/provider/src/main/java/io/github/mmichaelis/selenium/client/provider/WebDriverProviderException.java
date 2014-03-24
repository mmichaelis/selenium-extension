package io.github.mmichaelis.selenium.client.provider;

import javax.annotation.Nullable;

/**
 * General exception for problems providing WebDriver instances.
 *
 * @since 2014-03-21.
 */
public class WebDriverProviderException extends RuntimeException {
  private static final long serialVersionUID = -3556649446315503014L;

  public WebDriverProviderException(@Nullable final String message) {
    super(message);
  }

  public WebDriverProviderException(@Nullable final String message, @Nullable final Throwable cause) {
    super(message, cause);
  }

}
