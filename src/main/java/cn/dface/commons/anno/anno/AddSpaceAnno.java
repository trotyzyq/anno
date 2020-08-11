package cn.dface.commons.anno.anno;

import java.lang.annotation.*;

/**
 * 元转分
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AddSpaceAnno {

    String value() default "" ;

    String methodName() default "" ;

    int level() default 1;
}
