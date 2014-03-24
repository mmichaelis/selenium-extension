package io.github.mmichaelis.selenium.server.inmemory;

/**
 * Denotes a program error which should be filed as bug-report.
 *
 * @since 2014-03-23.
 */
@SuppressWarnings("CheckedExceptionClass")
public class InMemorySeleniumServerError extends Error {
  private static final long serialVersionUID = -1703860049527989066L;

  /**
   * Constructor with message and cause. Please provide any available information to file a
   * good bug report.
   *
   * @param message message of the exception
   * @param cause   cause of the exception
   */
  public InMemorySeleniumServerError(final String message, final Throwable cause) {
    super(message, cause);
  }
}
