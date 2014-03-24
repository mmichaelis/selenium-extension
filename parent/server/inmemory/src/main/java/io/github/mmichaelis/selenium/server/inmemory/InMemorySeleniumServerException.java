package io.github.mmichaelis.selenium.server.inmemory;

/**
 * Exceptions when controlling the In-Memory-Selenium-Server.
 *
 * @since 2014-03-23.
 */
public class InMemorySeleniumServerException extends RuntimeException {
  private static final long serialVersionUID = -3365322010168491405L;

  /**
   * Constructor with message and cause.
   *
   * @param message message providing debugging hints
   * @param cause   cause for this exception
   */
  public InMemorySeleniumServerException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
