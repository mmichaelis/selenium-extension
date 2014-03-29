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

package io.github.mmichaelis.selenium.client.provider;

import io.github.mmichaelis.selenium.common.internal.NetUtils;
import io.github.mmichaelis.selenium.server.junit.DefaultSeleniumServerRule;
import io.github.mmichaelis.selenium.server.junit.SeleniumServerRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.String.format;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.remote.DesiredCapabilities.htmlUnit;

/**
 * @since 2014-03-27.
 */
public class RemoteWebDriverProviderTest {
  @ClassRule
  public static final SeleniumServerRule SERVER_RULE = new DefaultSeleniumServerRule();
  @Rule
  public final ErrorCollector errorCollector = new ErrorCollector();
  @Rule
  public final ExpectedException expectedException = ExpectedException.none();

  @Test
  public void instantiate_remote_WebDriver() throws Exception {
    final DesiredCapabilities desiredCapabilities = htmlUnit();
    final WebDriverProvider provider =
            new RemoteWebDriverProvider(SERVER_RULE.getUrl(), desiredCapabilities);
    final WebDriver driver = getDriverInThread(provider);
    errorCollector.checkThat("A RemoteWebDriver instance should have been returned.",
            driver, instanceOf(RemoteWebDriver.class));
    final HasCapabilities remoteWebDriver = (HasCapabilities) driver;
    errorCollector.checkThat("Requested browser type should have been provided.",
            remoteWebDriver.getCapabilities().getBrowserName(),
            equalTo(desiredCapabilities.getBrowserName()));
  }

  @Test
  public void validate_to_string() throws Exception {
    final DesiredCapabilities desiredCapabilities = htmlUnit();
    final WebDriverProvider provider =
            new RemoteWebDriverProvider(SERVER_RULE.getUrl(), desiredCapabilities);
    assertThat(provider, hasToString(
            allOf(
                    containsString(RemoteWebDriverProvider.class.getSimpleName()),
                    containsString(SERVER_RULE.getUrl().toExternalForm()),
                    containsString(desiredCapabilities.toString())
            )
    ));
  }

  @Test
  public void fail_on_unavailable_server() throws Exception {
    final WebDriverProvider provider =
            new RemoteWebDriverProvider(URI.create(format("http://localhost:%d/", NetUtils.getFreePort())).toURL(), htmlUnit());
    expectedException.expectCause(isA(WebDriverInstantiationException.class));
    getDriverInThread(provider);
  }

  private WebDriver getDriverInThread(final WebDriverProvider provider) throws InterruptedException, ExecutionException {
    final Future<WebDriver> driverFuture = newCachedThreadPool().submit(new WebDriverCallable(provider));
    return driverFuture.get();
  }

  private static class WebDriverCallable implements Callable<WebDriver> {
    private final WebDriverProvider provider;

    public WebDriverCallable(final WebDriverProvider provider) {
      this.provider = provider;
    }

    @Override
    public WebDriver call() {
      return provider.get();
    }
  }

}
