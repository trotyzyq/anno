package cn.dface.commons.anno.service;

/**
 * @Author zyq
 * @Description 抽象注解 与实现类关联
 * @Date 2020/8/7 15:42
 */

public abstract class AbstractHandleAnno {
    protected Class annotation;

    public abstract void setAnnotation();

    public Class getAnnotation() {
        return annotation;
    }
}
