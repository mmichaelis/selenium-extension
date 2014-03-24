package io.github.mmichaelis.selenium.client.annotation.impl;

import io.github.mmichaelis.selenium.client.annotation.ValueParser;
import org.openqa.selenium.Platform;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Collections2.transform;
import static java.util.Arrays.asList;
import static java.util.Locale.ROOT;
import static org.openqa.selenium.Platform.valueOf;

/**
 * @since 2014-03-19.
 */
public class PlatformValueParser implements ValueParser<Platform> {
  private static final Collection<String> SUPPORTED_PLATFORMS =
          transform(asList(Platform.values()), new PlatformToStringFunction());

  @Nullable
  @Override
  public Platform parse(@Nonnull final String value) {
    checkNotNull(value, "Platform value must not be null.");
    return valueOf(value.toUpperCase(ROOT));
  }

  @Override
  public boolean canParse(@Nullable final String value) {
    return SUPPORTED_PLATFORMS.contains(value);
  }

  private static class PlatformToStringFunction implements com.google.common.base.Function<Platform, String> {
    @Nullable
    @Override
    public String apply(@Nullable final Platform input) {
      return input == null ? null : input.toString().toLowerCase(ROOT);
    }
  }
}
