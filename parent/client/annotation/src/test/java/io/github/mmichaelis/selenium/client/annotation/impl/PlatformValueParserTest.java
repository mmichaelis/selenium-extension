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
 * Tests {@link PlatformValueParser}.
 *
 * @since 2014-03-23.
 */
@SuppressWarnings("MethodParameterOfConcreteClass")
@RunWith(Parameterized.class)
public final class PlatformValueParserTest extends AbstractValueParserTestCase {

  public PlatformValueParserTest(final ValueParserTestData data) {
    super(data);
  }

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> parameters() {
    final Collection<Object[]> objects = new ArrayList<>();
    objects.add(new Object[]{data("Fail for null.", null, NullPointerException.class, Boolean.FALSE)});
    objects.add(new Object[]{data("Invalid Value.", random(10), IllegalArgumentException.class, Boolean.FALSE)});
    final Platform[] platforms = Platform.values();
    for (final Platform platform : platforms) {
      objects.add(new Object[]{data("Value from Platform", platform.toString(), platform, Boolean.TRUE)});
      objects.add(new Object[]{data("Value from Platform (ignore case)", platform.toString().toLowerCase(Locale.ROOT), platform, Boolean.TRUE)});
    }
    return objects;
  }

  @Override
  protected ValueParser<?> getParser() {
    return new PlatformValueParser();
  }

}
