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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static io.github.mmichaelis.selenium.client.annotation.impl.ValueParserTestData.data;
import static java.util.Arrays.asList;
import static org.junit.runners.Parameterized.Parameters;

/**
 * Tests {@link BooleanValueParser}.
 *
 * @since 2014-03-23.
 */
@SuppressWarnings("MethodParameterOfConcreteClass")
@RunWith(Parameterized.class)
public final class BooleanValueParserTest extends AbstractValueParserTestCase {

  public BooleanValueParserTest(final ValueParserTestData data) {
    super(data);
  }

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> parameters() {
    return asList(new Object[][]{
            {data("Standard: True", "true", Boolean.TRUE, Boolean.TRUE)},
            {data("Standard: True, ignore case", "tRuE", Boolean.TRUE, Boolean.TRUE)},
            {data("Standard: False", "false", Boolean.FALSE, Boolean.TRUE)},
            {data("Standard: False, ignore case", "FaLsE", Boolean.FALSE, Boolean.TRUE)},
            {data("Unparsable: False", "yes", Boolean.FALSE, Boolean.FALSE)},
            {data("Unparsable: False", "no", Boolean.FALSE, Boolean.FALSE)},
            {data("Unparsable: False", "", Boolean.FALSE, Boolean.FALSE)},
            {data("Fail for null.", null, NullPointerException.class, Boolean.FALSE)},
    });
  }

  @Override
  protected ValueParser<?> getParser() {
    return new BooleanValueParser();
  }

}
