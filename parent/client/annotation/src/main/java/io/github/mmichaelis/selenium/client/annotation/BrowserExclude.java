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

import java.lang.annotation.*;

/**
 * Marks tests (and test classes) which must not run on the specified browsers. Tests will be marked as
 * ignored if a browser is excluded.
 *
 * @since 2014-03-19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface BrowserExclude {
  /**
   * Browsers to exclude. Any match will cause the test to be ignored for the current browser.
   *
   * @return browsers to exclude
   */
  Browser[] value();
}
