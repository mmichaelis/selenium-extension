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

import io.github.mmichaelis.selenium.common.internal.PreconditionMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;

import javax.annotation.Nonnull;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * @since 2014-03-22.
 */
public final class CloseOtherWindowsAction implements Action {
  private final WebDriver driver;

  public CloseOtherWindowsAction(@Nonnull final WebDriver driver) {
    requireNonNull(driver, PreconditionMessage.MUST_NOT_BE_NULL.format("driver"));
    this.driver = driver;
  }

  @Override
  public void perform() {
    final String currentWindowHandle = driver.getWindowHandle();
    final Set<String> windowHandles = driver.getWindowHandles();
    for (final String windowHandle : windowHandles) {
      if (!currentWindowHandle.equals(windowHandle)) {
        driver.switchTo().window(windowHandle);
        driver.close();
      }
    }
    driver.switchTo().window(currentWindowHandle);
  }
}
