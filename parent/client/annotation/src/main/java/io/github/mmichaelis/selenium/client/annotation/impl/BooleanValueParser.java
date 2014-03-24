package io.github.mmichaelis.selenium.client.annotation.impl;

import io.github.mmichaelis.selenium.client.annotation.ValueParser;
import io.github.mmichaelis.selenium.common.internal.PreconditionMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Boolean.parseBoolean;
import static java.util.Arrays.asList;
import static java.util.Locale.ROOT;

/**
 * @since 2014-03-19.
 */
public class BooleanValueParser implements ValueParser<Boolean> {
  private static final Collection<String> PARSEABLE = asList("true", "false");

  @Nonnull
  @Override
  public Boolean parse(@Nonnull final String value) {
    checkNotNull(value, PreconditionMessage.MUST_NOT_BE_NULL.format("value"));
    return parseBoolean(value);
  }

  @Override
  public boolean canParse(@Nullable final String value) {
    return value != null && PARSEABLE.contains(value.toLowerCase(ROOT));
  }

}
