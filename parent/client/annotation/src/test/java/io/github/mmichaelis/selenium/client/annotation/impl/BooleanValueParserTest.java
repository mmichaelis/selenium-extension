package io.github.mmichaelis.selenium.client.annotation.impl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @since 2014-03-23.
 */
@RunWith(Parameterized.class)
public class BooleanValueParserTest {
  @Rule
  public final ExpectedException expectedException = ExpectedException.none();

  private BooleanValueParser parser;
  private final String description;
  private final String toParse;
  private final Object parseResult;
  private final Boolean canParseResult;

  public BooleanValueParserTest(final String description,
                                final String toParse,
                                final Object parseResult,
                                final Boolean canParseResult) {
    this.description = description;
    this.toParse = toParse;
    this.parseResult = parseResult;
    this.canParseResult = canParseResult;
  }

  @Parameters(name = "{index}: {0} - analysing {1}; parse: {2}, canParse: {3}")
  public static Collection<Object[]> parameters() {
    return asList(new Object[][]{
            // Description, Value, Result parse, Result canParse
            {"Standard: True", "true", Boolean.TRUE, Boolean.TRUE},
            {"Standard: True, ignore case", "tRuE", Boolean.TRUE, Boolean.TRUE},
            {"Standard: False", "false", Boolean.FALSE, Boolean.TRUE},
            {"Standard: False, ignore case", "FaLsE", Boolean.FALSE, Boolean.TRUE},
            {"Unparseable: False", "yes", Boolean.FALSE, Boolean.FALSE},
            {"Unparseable: False", "no", Boolean.FALSE, Boolean.FALSE},
            {"Unparseable: False", "", Boolean.FALSE, Boolean.FALSE},
            {"Fail for null.", null, NullPointerException.class, Boolean.FALSE},
    });
  }

  @Before
  public void setUp() throws Exception {
    parser = new BooleanValueParser();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void validateParse() throws Exception {
    if (parseResult instanceof Class && Throwable.class.isAssignableFrom((Class<?>) parseResult)) {
      expectedException.expect((Class<? extends Throwable>) parseResult);
    }
    final Boolean result = parser.parse(toParse);
    assertThat(result, equalTo(parseResult));
  }

  @Test
  public void validateCanParse() throws Exception {
    final boolean canParse = parser.canParse(toParse);
    assertThat(canParse, equalTo(canParseResult));
  }
}
