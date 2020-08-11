package cn.dface.commons.anno.register;



import cn.dface.commons.anno.anno.HandleBeanScanAnno;
import cn.dface.commons.anno.channelHandler.HandleBeanAnnoProperty;
import cn.dface.commons.anno.scan.HandleBeanParentScanner;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author zyq
 * @Description bean 扫描
 * @Date 2020/7/31 17:18
 */
public class HandleBeanScanRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private Environment environment;

    private String BASE_PACKAGE = "cn.dface.commons.anno.channelHandler";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        // 通过注解map获取注解
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(HandleBeanScanAnno.class.getName()));
        // 设置register用于set beanDefinition
        HandleBeanParentScanner scanner = new HandleBeanParentScanner(registry,false);

        //设置资源加载器
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }

        //获取注解中的包
        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annoAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        // 添加额外需要被切面管理的类
        basePackages.add(BASE_PACKAGE);

        //不过滤
        scanner.acceptAll();

        //获取创建bean后的BeanDefinitionHolder SET,并设置到map,用于后期通过名称获取bean
        Set<BeanDefinitionHolder> beanDefinitionHolderSet = scanner.doScan(StringUtils.toStringArray(basePackages));
        beanDefinitionHolderSet.forEach(beanDefinitionHolder -> {
            HandleBeanAnnoProperty.registerClassNames.add(beanDefinitionHolder.getBeanName());
        });

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
