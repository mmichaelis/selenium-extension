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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nullable;

/**
 * @since 2014-03-22.
 */
public class SilentAugmenter extends Augmenter {
  private static final String CGLIB_ENHANCED_REMOTE_WEB_DRIVER_CLASSNAME =
          "org.openqa.selenium.remote.RemoteWebDriver$$EnhancerByCGLIB";

  @Nullable
  @Override
  protected RemoteWebDriver extractRemoteWebDriver(final WebDriver driver) {
    if (isAcceptedByDefaultAugmenter(driver)) {
      return super.extractRemoteWebDriver(driver);
    }
    return null;
  }

  /**
   * <p>
   * This is (or should be) a copy of the default augmenter to judge if the class will be augmented or not.
   * It is used to suppress a logged warning in the default Augmenter when trying to augment a non-remote webdriver
   * instance.
   * </p>
   *
   * @param driver webdriver instance to check
   * @return true if the default augmenter will handle this webdriver instance
   */
  private boolean isAcceptedByDefaultAugmenter(final WebDriver driver) {
    return driver.getClass().equals(RemoteWebDriver.class)
            || driver.getClass().getName().startsWith(CGLIB_ENHANCED_REMOTE_WEB_DRIVER_CLASSNAME);
  }
}
