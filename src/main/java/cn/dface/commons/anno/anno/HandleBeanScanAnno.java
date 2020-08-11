package cn.dface.commons.anno.anno;

import cn.dface.commons.anno.register.HandleBeanScanRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 处理bean的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(HandleBeanScanRegister.class)
public @interface HandleBeanScanAnno {
    String basePackages() default "com";
}
