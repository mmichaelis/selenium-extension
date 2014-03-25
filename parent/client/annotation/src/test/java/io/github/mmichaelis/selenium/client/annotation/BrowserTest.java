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

import org.junit.Test;
import org.openqa.selenium.remote.BrowserType;

import static org.openqa.selenium.Platform.UNIX;

/**
 * Some proof of concept for the {@link Browser}-annotation.
 *
 * @since 2014-03-25.
 */
public class BrowserTest {
  @Test
  public void validate_class_annotation() throws Exception {


  }

  @BrowserExclude(@Browser(value = "", platform = UNIX))
  @BrowserInclude({
          @Browser(BrowserType.FIREFOX),
          @Browser(BrowserType.IE)
  })
  private static final class ExampleFirefoxOrInternetExplorerNotOnSolaris {
    @BrowserExclude({
            @Browser(value = BrowserType.FIREFOX, version = "<24"),
            @Browser(value = BrowserType.IE, version = "<10")
    })
    public void excludeOldBrowsers() {
    }

    @BrowserExclude(@Browser(BrowserType.FIREFOX))
    public void excludeForFirefox() {
    }
  }
}
