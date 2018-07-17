package net.intelink.zmframework.annotation;

import java.lang.annotation.*;

/**
 * 获取缓存数据注解
 *
 * @author suzhongqiang
 * @date 2017.06.03
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GetCache {

    String key();

    String value() default "";

    int expire() default -1;
}
