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
 * Denotes a program error which should be filed as bug-report.
 *
 * @since 2014-03-23.
 */
@SuppressWarnings("CheckedExceptionClass")
public class InMemorySeleniumServerError extends Error {
  private static final long serialVersionUID = -1703860049527989066L;

  /**
   * Constructor with message and cause. Please provide any available information to file a
   * good bug report.
   *
   * @param message message of the exception
   * @param cause   cause of the exception
   */
  public InMemorySeleniumServerError(final String message, final Throwable cause) {
    super(message, cause);
  }
}
