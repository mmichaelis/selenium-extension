package io.github.mmichaelis.selenium.client.provider.internal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests {@link io.github.mmichaelis.selenium.client.provider.internal.WebDriverInformationImpl}.
 *
 * @since 2014-03-21.
 */
@RunWith(MockitoJUnitRunner.class)
public class WebDriverInformationImplTest {
  @Mock
  private WebDriver driver;
  private final Thread someThread = new Thread(random(10));

  @Test
  public void store_webdriver_instance_directly() throws Exception {
    final WebDriverInformation information = new WebDriverInformationImpl(driver, someThread);
    assertThat(information.getDriver(), sameInstance(driver));
  }

  @Test
  public void store_thread_instance_directly() throws Exception {
    final WebDriverInformation information = new WebDriverInformationImpl(driver, someThread);
    assertThat(information.getShutdownHook(), sameInstance(someThread));
  }

  @Test
  public void original_url_stored() throws Exception {
    final String someString = random(10);
    when(driver.getCurrentUrl()).thenReturn(someString);
    final WebDriverInformation information = new WebDriverInformationImpl(driver, someThread);
    assertThat(information.getInitialUrl(), equalTo(someString));
  }

  @Test
  public void toString_provides_relevant_information() throws Exception {
    final String someDriverDescription = random(10);
    final String someDriverUrl = random(10);
    when(driver.getCurrentUrl()).thenReturn(someDriverUrl);
    when(driver.toString()).thenReturn(someDriverDescription);

    final WebDriverInformation information = new WebDriverInformationImpl(driver, someThread);
    assertThat(information.toString(),
            allOf(containsString(someDriverDescription),
                    containsString(someDriverUrl),
                    containsString(WebDriverInformationImpl.class.getSimpleName()),
                    containsString(someThread.getName())
            ));
  }

}
