package cn.dface.commons.anno.anno;


import java.lang.annotation.*;

/**
 * 去除参数trim注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TrimBlankAnno {

    String value() default "" ;

    String methodName() default "" ;

    int level() default 1;

}
