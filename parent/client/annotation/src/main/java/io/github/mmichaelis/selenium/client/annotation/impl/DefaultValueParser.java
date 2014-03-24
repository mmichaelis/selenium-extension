package io.github.mmichaelis.selenium.client.annotation.impl;

import io.github.mmichaelis.selenium.client.annotation.ValueParser;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * The default value parser tries to identify and parse most common values which
 * are set for capabilities. Currently supported are Boolean and Strings.
 *
 * @since 2014-03-19.
 */
public class DefaultValueParser<T> implements ValueParser<Object> {
  private static final List<? extends ValueParser<?>> PRIORITIZED_VALUE_PARSERS =
          asList(new BooleanValueParser(), new PlatformValueParser(), new StringValueParser());

  @Override
  public Object parse(final String value) {
    for (final ValueParser<?> parser : PRIORITIZED_VALUE_PARSERS) {
      if (parser.canParse(value)) {
        return parser.parse(value);
      }
    }
    throw new IllegalArgumentException(String.format("Unparseable value %s.", value));
  }

  @Override
  public boolean canParse(@Nullable final String value) {
    return true;
  }
}
