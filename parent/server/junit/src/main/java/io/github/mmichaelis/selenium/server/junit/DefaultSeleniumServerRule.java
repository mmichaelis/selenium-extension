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

package io.github.mmichaelis.selenium.server.junit;

import io.github.mmichaelis.selenium.server.provider.DefaultSeleniumServerProvider;
import io.github.mmichaelis.selenium.server.provider.SeleniumServerProvider;
import org.junit.rules.ExternalResource;

import javax.annotation.Nonnull;
import java.net.URL;

/**
 * Default rule for Selenium Server which starts the server before the test(s) and shuts it down afterwards.
 *
 * @since 2014-03-23.
 */
public final class DefaultSeleniumServerRule extends ExternalResource implements SeleniumServerRule {
  private final SeleniumServerProvider serverProvider = new DefaultSeleniumServerProvider();

  @Override
  protected void before() {
    serverProvider.start();
  }

  @Override
  protected void after() {
    // DevNote: Not stopping for now. Could stop here, if test class for example indicates that it requires
    // a server restart.
    // serverProvider.stop();
  }

  @Override
  @Nonnull
  public URL getUrl() {
    return serverProvider.getUrl();
  }
}
