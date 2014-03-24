package io.github.mmichaelis.selenium.client.annotation.impl;

import io.github.mmichaelis.selenium.client.annotation.ValueParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * The String Value Parser is meant as fallback if all other parsers failed. It is the only ValueParser which
 * is able to deal with the null values which will be returned as is.
 * </p>
 *
 * @since 2014-03-19.
 */
public final class StringValueParser implements ValueParser<String> {
  @Nullable
  @Override
  public String parse(@Nonnull final String value) {
    return value;
  }

  @Override
  public boolean canParse(@Nullable final String value) {
    return true;
  }
}
