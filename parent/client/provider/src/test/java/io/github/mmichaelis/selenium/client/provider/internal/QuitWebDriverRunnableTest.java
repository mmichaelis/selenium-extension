package io.github.mmichaelis.selenium.client.provider.internal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.Executors;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests {@link io.github.mmichaelis.selenium.client.provider.internal.QuitWebDriverRunnable}.
 *
 * @since 2014-03-21.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuitWebDriverRunnableTest {
  @Mock
  private WebDriver driver;

  @Test
  public void quit_webdriver_when_running() throws Exception {
    final Runnable runnable = new QuitWebDriverRunnable(driver);
    final Thread thread = Executors.defaultThreadFactory().newThread(runnable);
    thread.start();
    thread.join();
    verify(driver, atLeastOnce()).quit();
  }

  @Test
  public void toString_provides_relevant_information() throws Exception {
    final Runnable runnable = new QuitWebDriverRunnable(driver);
    final String someString = random(10);
    when(driver.toString()).thenReturn(someString);
    assertThat(runnable.toString(),
            allOf(containsString(someString), containsString(QuitWebDriverRunnable.class.getSimpleName())));
  }
}
