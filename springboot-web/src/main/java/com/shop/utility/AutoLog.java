package com.shop.utility;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
    String operate() default "";
    String identify() default "";
}
