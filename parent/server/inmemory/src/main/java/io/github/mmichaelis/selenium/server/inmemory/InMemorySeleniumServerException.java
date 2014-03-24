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

package io.github.mmichaelis.selenium.server.inmemory;

/**
 * Exceptions when controlling the In-Memory-Selenium-Server.
 *
 * @since 2014-03-23.
 */
public class InMemorySeleniumServerException extends RuntimeException {
  private static final long serialVersionUID = -3365322010168491405L;

  /**
   * Constructor with message and cause.
   *
   * @param message message providing debugging hints
   * @param cause   cause for this exception
   */
  public InMemorySeleniumServerException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
