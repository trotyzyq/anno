package cn.dface.commons.anno.anno;

import java.lang.annotation.*;

/**
 * 处理bean的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandleBeanAnno {

    String value() default "" ;

    String methodName() default "" ;

    String desc() default "" ;
}
