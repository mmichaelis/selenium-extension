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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;

/**
 * @since 2014-03-28.
 */
public class PocTest {
  private static final Logger LOG = LoggerFactory.getLogger(PocTest.class);

  @Test
  public void serialize() throws Exception {
    final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(ALLOW_UNQUOTED_FIELD_NAMES, true);
    final String s = mapper.writeValueAsString(capabilities);
    LOG.info("json: {}", s);
  }

  @Test
  public void deserialize() throws Exception {
    // {"version":"","platform":"ANY","javascriptEnabled":true,"browserName":"firefox"}
    // {"browserName":"firefox"}
    final String toDeserialize = "{browserName:\"firefox\"}";
    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(ALLOW_UNQUOTED_FIELD_NAMES, true);
    final DesiredCapabilities obj = mapper.readValue(toDeserialize, DesiredCapabilities.class);
    LOG.info("obj: {}", obj);
  }
}
