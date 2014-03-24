package io.github.mmichaelis.selenium.client.provider;

import javax.annotation.Nullable;

/**
 * Exception when creating a new WebDriver instance.
 *
 * @since 2014-03-21.
 */
public class WebDriverInstantiationException extends WebDriverProviderException {
  private static final long serialVersionUID = 196602134628367192L;

  public WebDriverInstantiationException(@Nullable final String message) {
    super(message);
  }

  public WebDriverInstantiationException(@Nullable final String message, @Nullable final Throwable cause) {
    super(message, cause);
  }

}
