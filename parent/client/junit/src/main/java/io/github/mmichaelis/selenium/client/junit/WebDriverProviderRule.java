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

package io.github.mmichaelis.selenium.client.junit;

import io.github.mmichaelis.selenium.client.provider.RemoteWebDriverProvider;
import io.github.mmichaelis.selenium.client.provider.WebDriverProvider;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.annotation.Nonnull;

import static io.github.mmichaelis.selenium.common.internal.PreconditionMessage.MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * @since 2014-03-21.
 */
public class WebDriverProviderRule implements TestRule {
  private final WebDriverProvider webDriverProvider =
          new RemoteWebDriverProvider(null, DesiredCapabilities.firefox());

  public WebDriverProvider getWebDriverProvider() {
    return webDriverProvider;
  }

  @Override
  public Statement apply(@Nonnull final Statement base, @Nonnull final Description description) {
    requireNonNull(base, MUST_NOT_BE_NULL.format("base"));
    requireNonNull(description, MUST_NOT_BE_NULL.format("description"));
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          base.evaluate();
        } finally {
          if (description.isTest()) {
            // DevNote: We could add a switch here for the behavior to perform during tests.
            // If a test marks to have dirtied the browser we might also decide to restart.
            webDriverProvider.reset();
          } else {
            webDriverProvider.quit();
          }
        }
      }
    };
  }

}
