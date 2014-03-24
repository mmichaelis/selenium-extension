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
import org.openqa.selenium.Platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static io.github.mmichaelis.selenium.client.annotation.impl.ValueParserTestData.data;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.junit.runners.Parameterized.Parameters;

/**
 * Tests {@link io.github.mmichaelis.selenium.client.annotation.impl.DefaultValueParser}.
 *
 * @since 2014-03-23.
 */
@SuppressWarnings("MethodParameterOfConcreteClass")
@RunWith(Parameterized.class)
public final class DefaultValueParserTest extends AbstractValueParserTestCase {

  public DefaultValueParserTest(final ValueParserTestData data) {
    super(data);
  }

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> parameters() {
    final Collection<Object[]> objects = new ArrayList<>();
    final Platform somePlatform = Platform.values()[0];
    // even a monkey might hit a platform value by chance; thus prefixing with underscore
    final String someString = '_' + random(10);

    objects.add(new Object[]{data(
            "Correctly parse platform.",
            somePlatform.toString().toLowerCase(Locale.ROOT),
            somePlatform,
            Boolean.TRUE)});
    objects.add(new Object[]{data(
            "Correctly parse boolean.",
            "tRue",
            Boolean.TRUE,
            Boolean.TRUE)});
    objects.add(new Object[]{data(
            "Correctly parse boolean.",
            "FaLsE",
            Boolean.FALSE,
            Boolean.TRUE)});
    objects.add(new Object[]{data(
            "Just pass some string.",
            someString,
            someString,
            Boolean.TRUE)});
    objects.add(new Object[]{data("Accept null value.", null, null, Boolean.TRUE)});
    return objects;
  }

  @Override
  protected ValueParser<?> getParser() {
    return new DefaultValueParser();
  }

}
