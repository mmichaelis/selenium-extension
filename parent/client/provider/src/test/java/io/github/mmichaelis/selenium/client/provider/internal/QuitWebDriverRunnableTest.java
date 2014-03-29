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

package io.github.mmichaelis.selenium.client.provider.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.concurrent.Executors;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests {@link io.github.mmichaelis.selenium.client.provider.internal.QuitWebDriverRunnable}.
 *
 * @since 2014-03-21.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuitWebDriverRunnableTest {
  @Rule
  public final ExpectedException expectedException = ExpectedException.none();

  @Mock
  private WebDriver driver;
  @Mock
  private UnreachableBrowserException unreachableBrowserException;
  @Mock
  private WebDriverException webDriverException;

  @Test
  public void quit_webdriver_when_running() throws Exception {
    final Runnable runnable = new QuitWebDriverRunnable(driver);
    final Thread thread = Executors.defaultThreadFactory().newThread(runnable);
    thread.start();
    thread.join();
    verify(driver, atLeastOnce()).quit();
  }

  @Test
  public void ignore_failure_when_server_is_unreachable_meanwhile() throws Exception {
    final Runnable runnable = new QuitWebDriverRunnable(driver);
    final Thread thread = Executors.defaultThreadFactory().newThread(runnable);
    Mockito.doThrow(unreachableBrowserException).when(driver).quit();
    thread.start();
    thread.join();
    verify(driver, atLeastOnce()).quit();
  }

  @Test
  public void ignore_failure_on_any_other_failure_when_quitting() throws Exception {
    final Runnable runnable = new QuitWebDriverRunnable(driver);
    final Thread thread = Executors.defaultThreadFactory().newThread(runnable);
    Mockito.doThrow(webDriverException).when(driver).quit();
    thread.start();
    thread.join();
    verify(driver, atLeastOnce()).quit();
  }

  @Test
  public void toString_provides_relevant_information() throws Exception {
    final Runnable runnable = new QuitWebDriverRunnable(driver);
    final String someString = random(10);
    when(driver.toString()).thenReturn(someString);
    assertThat(runnable, hasToString(allOf(containsString(someString), containsString(QuitWebDriverRunnable.class.getSimpleName()))));
  }
}
