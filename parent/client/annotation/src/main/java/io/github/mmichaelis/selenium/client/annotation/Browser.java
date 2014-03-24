package io.github.mmichaelis.selenium.client.annotation;

import java.lang.annotation.*;

/**
 * @see <a href="https://code.google.com/p/selenium/wiki/DesiredCapabilities">DesiredCapabilities Specification</a>
 * @since 2014-03-19
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Browser {
  Capability[] included() default {};

  Capability[] excluded() default {};
}
