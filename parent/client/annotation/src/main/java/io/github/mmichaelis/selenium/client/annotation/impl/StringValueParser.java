package io.github.mmichaelis.selenium.client.annotation.impl;

import io.github.mmichaelis.selenium.client.annotation.ValueParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @since 2014-03-19.
 */
public class StringValueParser implements ValueParser<String> {
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
