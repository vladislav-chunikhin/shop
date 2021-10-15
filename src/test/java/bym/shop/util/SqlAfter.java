package bym.shop.util;

import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public @interface SqlAfter {

    @AliasFor(annotation = Sql.class)
    String[] value() default {};

    @AliasFor(annotation = Sql.class)
    String[] scripts() default {};

    @AliasFor(annotation = Sql.class)
    String[] statements() default {};

    @AliasFor(annotation = Sql.class)
    SqlConfig config() default @SqlConfig;

}
