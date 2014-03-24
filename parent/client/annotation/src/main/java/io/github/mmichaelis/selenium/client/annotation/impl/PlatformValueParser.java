package io.github.mmichaelis.selenium.client.annotation.impl;

import com.google.common.base.Function;
import io.github.mmichaelis.selenium.client.annotation.ValueParser;
import org.openqa.selenium.Platform;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Locale;

import static com.google.common.collect.Collections2.transform;
import static java.util.Arrays.asList;
import static java.util.Locale.ROOT;
import static java.util.Objects.requireNonNull;
import static org.openqa.selenium.Platform.valueOf;

/**
 * <p>
 * Parses a String to a Platform.
 * </p>
 *
 * @since 2014-03-19.
 */
public final class PlatformValueParser implements ValueParser<Platform> {
  private static final Collection<String> SUPPORTED_PLATFORMS =
          transform(asList(Platform.values()), new PlatformToStringFunction());

  /**
   * <p>
   * Parses a given String to a Platform. The Platform String will be converted to upper case and
   * must match the list of supported platforms in {@link Platform}.
   * </p>
   *
   * @param value value to parse; will fail on {@code null}
   * @return parsed platform
   * @throws NullPointerException     if value is null
   * @throws IllegalArgumentException if platform is unknown
   */
  @Nullable
  @CheckReturnValue
  @Override
  public Platform parse(@Nonnull final String value) {
    requireNonNull(value, "Platform value must not be null.");
    canParse(value);
    return valueOf(value.toUpperCase(ROOT));
  }

  /**
   * <p>
   * Will validate if this parser can parse the given String value. It especially checks that
   * the provided value matches a predefined platform in {@link Platform}.
   * </p>
   *
   * @param value the value to parse
   * @return {@code true} if value can be parsed; {@code false} otherwise
   */
  @CheckReturnValue
  @Override
  public boolean canParse(@Nullable final String value) {
    return value != null && SUPPORTED_PLATFORMS.contains(unifyPlatformCase(value));
  }

  /**
   * Converts platforms to (lower cased) String representations.
   */
  private static class PlatformToStringFunction implements Function<Platform, String> {
    @Nullable
    @CheckReturnValue
    @Override
    public String apply(@Nullable final Platform input) {
      return input == null ? null : unifyPlatformCase(input.toString());
    }
  }

  /**
   * Ensures that platform string values are stored and compared in the same case.
   *
   * @param value Platform string value to unify
   * @return unified (regarding case) of platform name
   */
  @Nonnull
  @CheckReturnValue
  private static String unifyPlatformCase(@Nonnull final String value) {
    return value.toLowerCase(Locale.ROOT);
  }

}
