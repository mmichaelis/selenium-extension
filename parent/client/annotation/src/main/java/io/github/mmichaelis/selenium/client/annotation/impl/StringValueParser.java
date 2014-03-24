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

import javax.annotation.CheckReturnValue;
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
  /**
   * <p>
   * Parses a given String value by directly returning it.
   * </p>
   *
   * @param value value to parse; {@code null} will be returned as is (as well as anything else parsed as argument)
   * @return value (as is)
   */
  @Nullable
  @CheckReturnValue
  @Override
  public String parse(@Nonnull final String value) {
    return value;
  }

  /**
   * As fallback parser accepts anything.
   *
   * @param value the value to parse
   * @return always true
   */
  @CheckReturnValue
  @Override
  public boolean canParse(@Nullable final String value) {
    return true;
  }
}
