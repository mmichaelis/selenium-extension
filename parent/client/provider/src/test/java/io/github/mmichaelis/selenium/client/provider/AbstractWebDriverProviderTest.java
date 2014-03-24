package io.github.mmichaelis.selenium.client.provider;

import com.google.common.io.Resources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests {@link io.github.mmichaelis.selenium.client.provider.AbstractWebDriverProvider}.
 *
 * @since 2014-03-22.
 */
@SuppressWarnings("AnonymousInnerClass")
@RunWith(MockitoJUnitRunner.class)
public class AbstractWebDriverProviderTest {
  @Rule
  public final ErrorCollector errorCollector = new ErrorCollector();

  @Mock
  private WebDriver defaultDriver;
  @Mock
  private WebDriver otherDriver;

  @Test
  public void provide_webdriver_instance() throws Exception {
    final WebDriverProvider driverProvider = new NoExceptionWebDriverProvider(defaultDriver);
    final WebDriver createdDriver = getDriverFromThread(driverProvider);
    assertThat(createdDriver, sameInstance(defaultDriver));
  }

  @Test
  public void different_threads_provide_different_webdriver_sessions() throws Exception {
    final WebDriverProvider driverProvider = new NoExceptionWebDriverProvider(defaultDriver, otherDriver);
    final WebDriver firstCreatedDriver = getDriverFromThread(driverProvider);
    final WebDriver secondCreatedDriver = getDriverFromThread(driverProvider);
    assertThat(firstCreatedDriver, sameInstance(defaultDriver));
    assertThat(secondCreatedDriver, sameInstance(otherDriver));
  }

  @Test
  public void quit_ends_browser() throws Exception {
    final WebDriverProvider driverProvider = new NoExceptionWebDriverProvider(defaultDriver);
    final Thread thread = Executors.defaultThreadFactory().newThread(new Runnable() {
      @Override
      public void run() {
        driverProvider.get();
        driverProvider.quit();
      }
    });
    thread.start();
    thread.join();
    verify(defaultDriver, atLeastOnce()).quit();
  }

  @Test
  public void quit_if_not_started_does_nothing() throws Exception {
    final WebDriverProvider driverProvider = new NoExceptionWebDriverProvider(defaultDriver);
    final Thread thread = Executors.defaultThreadFactory().newThread(new Runnable() {
      @Override
      public void run() {
        driverProvider.quit();
      }
    });
    thread.start();
    thread.join();
    verify(defaultDriver, never()).quit();
  }

  @Test
  public void retrieving_webdriver_twice_returns_same_instance_on_thread() throws Exception {
    final WebDriverProvider driverProvider = new NoExceptionWebDriverProvider(defaultDriver, otherDriver);
    final Future<WebDriver[]> webDriversFuture = Executors.newCachedThreadPool().submit(new Callable<WebDriver[]>() {
      @Override
      public WebDriver[] call() {
        return new WebDriver[]{driverProvider.get(), driverProvider.get()};
      }
    });
    final WebDriver[] webDrivers = webDriversFuture.get();
    assertThat(defaultDriver, sameInstance(webDrivers[0]));
    assertThat(defaultDriver, sameInstance(webDrivers[1]));
  }

  @Test
  public void reset_closes_all_but_one_window() throws Exception {
    final HtmlUnitDriver testedDriver = new HtmlUnitDriver(true);
    final URL homePageUrl = Resources.getResource(this.getClass(), "page1.html");
    testedDriver.get(homePageUrl.toExternalForm());
    testedDriver.manage().addCookie(new Cookie(random(10), random(10)));
    prepareResetTest(testedDriver);
    errorCollector.checkThat(testedDriver.getWindowHandles(), hasSize(1));
    errorCollector.checkThat(testedDriver.getCurrentUrl(), equalTo(homePageUrl.toExternalForm()));
    errorCollector.checkThat(testedDriver.manage().getCookies(), empty());
  }

  @Test
  public void reset_does_nothing_if_not_started() throws Exception {
    final WebDriverProvider driverProvider = new NoExceptionWebDriverProvider(defaultDriver);
    final Thread thread = Executors.defaultThreadFactory().newThread(new Runnable() {
      @Override
      public void run() {
        driverProvider.reset();
      }
    });
    thread.start();
    thread.join();
    errorCollector.checkSucceeds(new Callable<Object>() {
      @Nullable
      @Override
      public Object call() {
        verify(defaultDriver, never()).navigate();
        return null;
      }
    });
    errorCollector.checkSucceeds(new Callable<Object>() {
      @Nullable
      @Override
      public Object call() {
        verify(defaultDriver, never()).manage();
        return null;
      }
    });
    errorCollector.checkSucceeds(new Callable<Object>() {
      @Nullable
      @Override
      public Object call() {
        verify(defaultDriver, never()).getWindowHandle();
        return null;
      }
    });
    errorCollector.checkSucceeds(new Callable<Object>() {
      @Nullable
      @Override
      public Object call() {
        verify(defaultDriver, never()).getWindowHandles();
        return null;
      }
    });
  }

  private void prepareResetTest(final HtmlUnitDriver testedDriver)
          throws InterruptedException, ExecutionException {
    final URL otherPageUrl = Resources.getResource(this.getClass(), "page2.html");
    final URL newPageUrl = Resources.getResource(this.getClass(), "page3.html");
    final WebDriverProvider driverProvider = new NoExceptionWebDriverProvider(testedDriver);
    final ExecutorService executorService = Executors.newCachedThreadPool();
    final Future<Void> openWindowResultFuture = executorService.submit(new Callable<Void>() {
      @Nullable
      @Override
      public Void call() {
        final WebDriver driver = driverProvider.get();
        driver.get(otherPageUrl.toExternalForm());
        final JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.open(arguments[0])", newPageUrl.toExternalForm());
        final Set<String> windowHandles = driver.getWindowHandles();
        checkState(windowHandles.size() > 1, "Failed to open additional window for driver: %s", driver);
        driverProvider.reset();
        return null;
      }
    });
    openWindowResultFuture.get();
  }

  private WebDriver getDriverFromThread(final WebDriverProvider driverProvider)
          throws InterruptedException, ExecutionException {
    final Callable<WebDriver> driverCallable = new WebDriverCallable(driverProvider);
    final ExecutorService executor = newCachedThreadPool();
    final Future<WebDriver> result = executor.submit(driverCallable);
    return result.get();
  }

  private static class NoExceptionWebDriverProvider extends AbstractWebDriverProvider {
    private final Iterator<WebDriver> testedDriverIterator;

    public NoExceptionWebDriverProvider(final WebDriver... testedDriver) {
      this.testedDriverIterator = asList(testedDriver).iterator();
    }

    @Nonnull
    @Override
    protected WebDriver instantiateDriver() {
      return testedDriverIterator.next();
    }
  }

  private static class WebDriverCallable implements Callable<WebDriver> {
    private final WebDriverProvider driverProvider;

    public WebDriverCallable(final WebDriverProvider driverProvider) {
      this.driverProvider = driverProvider;
    }

    @Override
    public WebDriver call() {
      return driverProvider.get();
    }
  }
}
