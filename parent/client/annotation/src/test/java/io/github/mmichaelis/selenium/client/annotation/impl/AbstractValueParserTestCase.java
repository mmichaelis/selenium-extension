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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @since 2014-03-24.
 */
@SuppressWarnings({"InstanceVariableOfConcreteClass", "MethodParameterOfConcreteClass"})
public abstract class AbstractValueParserTestCase {
  @Rule
  public final ExpectedException expectedException = ExpectedException.none();
  private final ValueParserTestData data;
  private ValueParser<?> parser;

  protected AbstractValueParserTestCase(final ValueParserTestData data) {
    this.data = data;
  }

  @Before
  public final void setUp() throws Exception {
    parser = getParser();
  }

  protected abstract ValueParser<?> getParser();

  @SuppressWarnings("unchecked")
  @Test
  public final void validateParse() throws Exception {
    final Object parseResult = data.getParseResult();
    if (parseResult instanceof Class && Throwable.class.isAssignableFrom((Class<?>) parseResult)) {
      expectedException.expect((Class<? extends Throwable>) parseResult);
    }
    final Object result = parser.parse(data.getToParse());
    assertThat(result, equalTo(parseResult));
  }

  @Test
  public final void validateCanParse() throws Exception {
    final boolean canParse = parser.canParse(data.getToParse());
    assertThat(canParse, equalTo(data.isCanParseResult()));
  }
}
