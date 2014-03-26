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

import org.openqa.selenium.Platform;

import java.lang.annotation.*;

/**
 * <p>
 * Defines a browser to include or exclude.
 * </p>
 * <dl>
 * <dt><strong>Example:</strong></dt>
 * <dd>
 * <pre>{@code
 * &#64;BrowserExclude(&#64;Browser(value = "", platform = UNIX, message = "not supported))
 * &#64;BrowserInclude({
 *         &#64;Browser(FIREFOX),
 *         &#64;Browser(IE) })
 * }</pre>
 * </dd>
 * </dl>
 *
 * @see BrowserExclude
 * @see BrowserInclude
 * @since 2014-03-19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Browser {
  /**
   * <p>
   * The browser name to include/exclude.
   * </p>
   *
   * @return browser name
   * @see org.openqa.selenium.remote.BrowserType
   */
  String value();

  /**
   * <p>
   * The browser version to include/exclude. The version will be prefixed by an operand to compare against the
   * available browser version. If no operand is specified it defaults to equals.
   * </p>
   * <p>
   * Only the major version will be used for comparison.
   * </p>
   * <dl>
   * <dt><strong>Operands:</strong></dt>
   * <dd>
   * <ul>
   * <li>&lt; - less than</li>
   * <li>&gt; - greater than</li>
   * <li>= - equals</li>
   * </ul>
   * </dd>
   * <dt><strong>Example:</strong></dt>
   * <dd>
   * <ul>
   * <li>{@code &lt;10} - less than version 10</li>
   * </ul>
   * </dd>
   * </dl>
   *
   * @return version to compare; defaults to <em>ignore version</em> (empty string)
   */
  String version() default "";

  /**
   * <p>
   * The platform to include or exclude.
   * </p>
   *
   * @return the platform to include or exclude
   * @see Platform
   */
  Platform platform() default Platform.ANY;

  /**
   * A message to print when a test is ignored because of the include/exclude statements.
   *
   * @return message, defaults to none (empty string)
   */
  String message() default "";
}
