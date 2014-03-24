package io.github.mmichaelis.selenium.client.annotation;

import io.github.mmichaelis.selenium.client.annotation.impl.DefaultValueParser;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @since 2014-03-19.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Capability {
  String key();

  String value();

  Class<? extends ValueParser> valueParser() default DefaultValueParser.class;
}
