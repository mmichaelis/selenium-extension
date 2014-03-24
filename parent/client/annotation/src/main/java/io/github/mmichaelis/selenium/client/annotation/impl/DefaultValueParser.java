package io.github.mmichaelis.selenium.client.annotation.impl;

import io.github.mmichaelis.selenium.client.annotation.ValueParser;
import io.github.mmichaelis.selenium.client.annotation.ValueParserException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * <p>
 * The default value parser tries to identify and parse most common values which
 * are set for capabilities. Currently supported are Boolean, Platform and String.
 * The latter one as fallback to return parsed Strings (including {@code null}) as is.
 * </p>
 *
 * @since 2014-03-19.
 */
public final class DefaultValueParser implements ValueParser<Object> {
  private static final List<? extends ValueParser<?>> PRIORITIZED_VALUE_PARSERS =
          asList(new BooleanValueParser(),
                  new PlatformValueParser(),
                  new StringValueParser());

  /**
   * <p>
   * Tries to parse the given String value with a predefined set of ValueParsers.
   * </p>
   *
   * @param value value to parse
   * @return the parsed result
   * @throws ValueParserException if there is no parser which can deal with the provided value; this most of the time
   *                              means a defect you should file an issue for as the last fallback value parser should
   *                              always be able to deal with any String value.
   */
  @Nullable
  @CheckReturnValue
  @Override
  public Object parse(@Nullable final String value) {
    for (final ValueParser<?> parser : PRIORITIZED_VALUE_PARSERS) {
      if (parser.canParse(value)) {
        return parser.parse(value);
      }
    }
    throw new ValueParserException(format("No registered ValueParser available for value: '%s'.", value));
  }

  /**
   * <p>
   * DefaultValueParser can parse anything.
   * </p>
   *
   * @param value the value to parse
   * @return always true
   */
  @CheckReturnValue
  @Override
  public boolean canParse(@Nullable final String value) {
    // DevNote: While the DefaultParser could query each of its contained parsers if they can parse the given value
    // it is the purpose of the default parser to deal with any value. Thus not being able to parse a String value
    // is a defect.
    return true;
  }
}
