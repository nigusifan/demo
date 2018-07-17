package net.intelink.zmframework.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Log {

//    @AliasFor("module")
//    String value();

//    @AliasFor("value")
    String module();

    String content() default "";

}
