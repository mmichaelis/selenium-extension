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

import com.google.common.base.Objects;

/**
 * @since 2014-03-24.
 */
@SuppressWarnings({"PublicMethodNotExposedInInterface", "MethodReturnOfConcreteClass"})
public final class ValueParserTestData {
  private final String description;
  private final String toParse;
  private final Object parseResult;
  private final boolean canParseResult;

  @SuppressWarnings("BooleanParameter")
  private ValueParserTestData(final String description, final String toParse, final Object parseResult, final boolean canParseResult) {
    this.description = description;
    this.toParse = toParse;
    this.parseResult = parseResult;
    this.canParseResult = canParseResult;
  }

  @SuppressWarnings("BooleanParameter")
  public static ValueParserTestData data(final String description, final String toParse, final Object parseResult, final boolean canParseResult) {
    return new ValueParserTestData(description, toParse, parseResult, canParseResult);
  }

  public String getToParse() {
    return toParse;
  }

  public Object getParseResult() {
    return parseResult;
  }

  public boolean isCanParseResult() {
    return canParseResult;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
            .add("description", description)
            .add("toParse", toParse)
            .add("parseResult", parseResult)
            .add("canParseResult", canParseResult)
            .toString();
  }
}
