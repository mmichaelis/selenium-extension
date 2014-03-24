/*
 * Copyright 2014 Mark Michaelis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mmichaelis.selenium.client.annotation.impl;

import io.github.mmichaelis.selenium.client.annotation.ValueParser;
import io.github.mmichaelis.selenium.common.internal.PreconditionMessage;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

import static java.lang.Boolean.parseBoolean;
import static java.util.Arrays.asList;
import static java.util.Locale.ROOT;
import static java.util.Objects.requireNonNull;

/**
 * <p>
 * Parses Strings to Boolean values. Mind that parsing is rather restrictive compared to
 * {@link Boolean#parseBoolean(String)} thus only {@code true} and {@code false} are allowed (but ignoring case).
 * This is because ValueParsers are meant to run in a chain where a parser only takes that values it really
 * understands.
 * </p>
 *
 * @since 2014-03-19.
 */
public final class BooleanValueParser implements ValueParser<Boolean> {
  private static final Collection<String> PARSABLE = asList("true", "false");

  /**
   * Parses the given value to an instance of Boolean.
   *
   * @param value value to parse; must not be {@code null}; case is ignored.
   * @return either {@code true} or {@code false} depending on the given String value
   * @throws java.lang.NullPointerException if {@code null} is passed as value
   */
  @Nonnull
  @CheckReturnValue
  @Override
  public Boolean parse(@Nonnull final String value) {
    requireNonNull(value, PreconditionMessage.MUST_NOT_BE_NULL.format("value"));
    return parseBoolean(value);
  }

  @CheckReturnValue
  @Override
  public boolean canParse(@Nullable final String value) {
    return value != null && PARSABLE.contains(value.toLowerCase(ROOT));
  }

}
