package io.github.mmichaelis.selenium.common.internal;

import static com.google.common.base.Objects.toStringHelper;

/**
 * <p>
 * Messages for Precondition checks.
 * </p>
 *
 * @since 2014-03-21.
 */
public enum PreconditionMessage {
  /**
   * Message that an object must not be null. Required one additional format-argument describing the variable
   * which holds the object.
   */
  MUST_NOT_BE_NULL("%s must not be null.");

  private final String message;

  PreconditionMessage(final String message) {
    this.message = message;
  }

  public final String format(final String... arguments) {
    return String.format(message, arguments);
  }

  @Override
  public String toString() {
    return toStringHelper(this)
            .add("message", message)
            .toString();
  }
}
