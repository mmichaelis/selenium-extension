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

package io.github.mmichaelis.selenium.client.annotation;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.openqa.selenium.Platform;

import static org.hamcrest.Matchers.array;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.Platform.ANY;
import static org.openqa.selenium.Platform.UNIX;
import static org.openqa.selenium.remote.BrowserType.FIREFOX;
import static org.openqa.selenium.remote.BrowserType.IE;

/**
 * Some proof of concept for the {@link Browser}-annotation. It especially serves as example how the annotations
 * for inclusion and exclusion are used.
 *
 * @since 2014-03-25.
 */
public class BrowserTest {
  private static final String UNIX_NOT_SUPPORTED = "Unix not supported.";

  @Test
  public void validate_class_annotation() throws Exception {
    final BrowserExclude exclude =
            ExampleFirefoxOrInternetExplorerNotOnSolaris.class.getAnnotation(BrowserExclude.class);
    final BrowserInclude include =
            ExampleFirefoxOrInternetExplorerNotOnSolaris.class.getAnnotation(BrowserInclude.class);
    final Browser[] excludes = exclude.value();
    final Browser[] includes = include.value();
    assertThat(excludes, array(new BrowserEquals("", UNIX, UNIX_NOT_SUPPORTED)));
    assertThat(includes, arrayContainingInAnyOrder(
            new BrowserEquals(FIREFOX, ANY, ""),
            new BrowserEquals(IE, ANY, "")));
  }

  @BrowserExclude(@Browser(value = "", platform = UNIX, message = UNIX_NOT_SUPPORTED))
  @BrowserInclude({
          @Browser(FIREFOX),
          @Browser(IE)
  })
  private static final class ExampleFirefoxOrInternetExplorerNotOnSolaris {

    @BrowserExclude({
            @Browser(value = FIREFOX, version = "<24"),
            @Browser(value = IE, version = "<10")
    })
    public void excludeOldBrowsers() {
    }

    @BrowserExclude(@Browser(FIREFOX))
    public void excludeForFirefox() {
    }
  }

  private static class BrowserEquals extends CustomTypeSafeMatcher<Browser> {
    private final String expectedValue;
    private final Platform expectedPlatform;
    private final String expectedMessage;

    public BrowserEquals(final String expectedValue, final Platform expectedPlatform, final String expectedMessage) {
      super(String.format("Browser tag matches value: %s, platform: %s, message: %s", expectedValue, expectedPlatform, expectedMessage));
      this.expectedValue = expectedValue;
      this.expectedPlatform = expectedPlatform;
      this.expectedMessage = expectedMessage;
    }

    @Override
    protected boolean matchesSafely(final Browser item) {
      return Matchers.equalTo(expectedValue).matches(item.value())
              && Matchers.equalTo(expectedPlatform).matches(item.platform())
              && Matchers.equalTo(expectedMessage).matches(item.message());
    }
  }
}
