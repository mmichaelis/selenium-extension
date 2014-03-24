package io.github.mmichaelis.selenium.client.annotation;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * <p>
 * Parses a String value to the specified type.
 * </p>
 *
 * @param <T> the type the String will be parsed to
 * @since 2014-03-19.
 */
public interface ValueParser<T> {
  /**
   * Parse the given String to a value of type T.
   *
   * @param value value to parse; some parsers might accept {@code null} values, other might fail with exceptions
   * @return the result of parsing the given string; might be {@code null} bust most of the time will be
   * {@code non-null}.
   * @throws java.lang.NullPointerException if parser cannot deal with provided
   *                                        {@code null} value
   * @throws ValueParserException           if parsing fails for any other reason
   */
  // DevNote: There is some discussion if interfaces should define JSR305 annotations (here: Nullable) for parameters
  // if implementations might decide not to support null values (thus annotate with Nonnull). Findbugs will accept this
  // and call it "best practice" - while Idea Inspections will report a problem.
  //
  // To skip the discussion here, "value" is not annotated at all - stating that (at this point) it is unknown if
  // implementations will accept null values or not.
  @CheckReturnValue
  T parse(String value);

  /**
   * <p>
   * Determine if this value parser is able to deal with the given value.
   * </p>
   *
   * @param value the value to parse
   * @return {@code true} if this parser could convert the given String, {@code false} otherwise
   */
  @CheckReturnValue
  boolean canParse(@Nullable String value);
}
