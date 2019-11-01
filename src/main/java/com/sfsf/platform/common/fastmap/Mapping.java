package com.sfsf.platform.common.fastmap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(Mappings.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Mapping {

    String target();
    
    String source() default "";
    
    String expression() default "";
}
