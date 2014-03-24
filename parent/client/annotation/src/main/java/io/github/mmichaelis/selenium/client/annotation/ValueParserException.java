package io.github.mmichaelis.selenium.client.annotation;

import javax.annotation.Nullable;

/**
 * Denotes an error parsing capability values.
 *
 * @since 2014-03-23.
 */
public class ValueParserException extends RuntimeException {
  private static final long serialVersionUID = -4818050684277268049L;

  /**
   * Constructor specifying a message.
   *
   * @param message exception message
   */
  public ValueParserException(@Nullable final String message) {
    super(message);
  }
}
