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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests {@link io.github.mmichaelis.selenium.client.provider.internal.WebDriverInformationImpl}.
 *
 * @since 2014-03-21.
 */
@RunWith(MockitoJUnitRunner.class)
public class WebDriverInformationImplTest {
  private final Thread someThread = new Thread(random(10));
  @Mock
  private WebDriver driver;

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
