/**
 * <p>
 * Selenium Client Annotation implementations.
 * </p>
 * <p><strong>Contents:</strong></p>
 * <dl>
 *   <dt><strong>ValueParsers:</strong></dt>
 *   <dd>
 *     <p>
 *       Implementations of {@link io.github.mmichaelis.selenium.client.annotation.ValueParser} which parse
 *       capabilities given as Strings to their appropriate values. In addition the
 *       {@link io.github.mmichaelis.selenium.client.annotation.impl.DefaultValueParser} is a best effort
 *       approach to parse any given String to the appropriate type.
 *     </p>
 *     <p>
 *       ValueParsers are only allowed to parse values they really understand. This is because the last fallback
 *       is the {@link io.github.mmichaelis.selenium.client.annotation.impl.StringValueParser} which is also the
 *       only one to accept {@code null} values. All other ValueParsers should deny parsing {@code null} values and
 *       fail if trying to do so.
 *     </p>
 *   </dd>
 * </dl>
 * @since 2014-03-23.
 */
package io.github.mmichaelis.selenium.client.annotation.impl;
