package cn.dface.commons.anno.channelHandler;


import cn.dface.commons.anno.AnnoCommonUtil;
import cn.dface.commons.anno.anno.HandleBeanAnno;
import cn.dface.commons.anno.service.AbstractHandleAnno;
import cn.dface.commons.anno.service.HandleBeanParentService;
import cn.dface.commons.anno.service.HandleParamParentService;
import cn.dface.commons.anno.service.HandleService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @Author zyq
 * @Description 处理bean参数注解的handler
 * @Date 2019/8/22 16:36
 */

@Aspect
@Slf4j
public class HandleBeanAnnoHandler {
    private String RESULT_OBJECT_NAME = "object";


    /**
     * point 扩展点
     * around 通知
     */
    @Around(value = "@annotation(around)")
    public Object initBean(ProceedingJoinPoint point, HandleBeanAnno around) throws Throwable {

        // 获取方法入参的参数
        Object[] submitBeans = point.getArgs();

        // 每个service执行参数 如某个bean,返回整个bean,并执行数组赋值,
        // 用于防止字符串传递引用
        for(int i = 0; i < submitBeans.length; i++){
            Object submitBean = submitBeans[i];
            submitBeans[i] = handleParam(submitBean);
        }

        // 执行被代理类方法
        Object object = point.proceed(point.getArgs());

        //获取返回的ResultVO 的 Field(即object) 的参数
        Field field = object.getClass().getDeclaredField(RESULT_OBJECT_NAME);
        field.setAccessible(true);
        Object param = field.get(object);

        // 处理返回的参数
        handleParam(param);

        log.info("Handle End");
        return object;
    }


    /**
     * 检查是否是bean
     * @param param
     * @return
     */
    private boolean checkNotBean(Object param){
        if(param instanceof Short){
            return true;
        }
        if(param instanceof Integer){
            return true;
        }
        if(param instanceof Long){
            return true;
        }
        if(param instanceof Boolean){
            return true;
        }
        if(param instanceof Float){
            return true;
        }
        if(param instanceof Double){
            return true;
        }
        if(param instanceof String){
            return true;
        }
        return false;
    }

    /**
     * 很多情况下是bean,少数情况下是某个RequestParam
     * @param param
     * @return
     */
    private Object handleParam(Object param) throws Exception{
        List<HandleBeanParentService> beanServiceList = HandleBeanAnnoProperty.beanServiceList;
        List<HandleParamParentService> paramServiceList = HandleBeanAnnoProperty.paramServiceList;
        Map<String, HandleService> handleBeanServiceMap = HandleBeanAnnoProperty.annoConnectMap;

        if(null != param){
            if(checkNotBean(param)){
                for(HandleParamParentService service : paramServiceList){
                    param = service.doParam(param);
                }
            }else{
                Field[] fields = param.getClass().getDeclaredFields();
                for(Field field : fields){
                    // 获取field上的多个注解
                    Annotation[] annotations = field.getDeclaredAnnotations();
                    // 判断是否注解,没有注解直接返回
                    if(null == annotations || annotations.length == 0){
                        continue;
                    }
                    field.setAccessible(true);

                    //注解排序
                    AnnoCommonUtil.sort(annotations);

                    for(Annotation annotation : annotations){
                        // bean 参数上的注解名称
                        String annotationName = annotation.annotationType().getName();

                        // 判断注解是否有实现类,存在去执行
                        HandleService handleService = handleBeanServiceMap.get(annotationName);
                        Object beExecuteObject = null;
                        try {
                            beExecuteObject = field.get(param);
                        } catch (IllegalAccessException e) {
                            log.error("不能获取改field的值, field{}",field.getName());
                            continue;
                        }
                        // 启动时定义完成的service bean 在初始化时就存入map中
                        if(null != handleService){
                            Object resultParam = handleService.doParam(beExecuteObject);
                            // 判断返回值是否相同,不相同再赋值,减少反射消耗
                            if(!beExecuteObject.equals(resultParam)) {
                                field.set(param, resultParam);
                            }
                        }else{
                            // 没有实现类 就去所有的实现类中试一次(防止通过代码手动生成bean的情况)
                            for(HandleBeanParentService service : beanServiceList){
                                AbstractHandleAnno abstractHandleAnno = (AbstractHandleAnno)service;
                                // 每个service的注解名称
                                String serviceAnnotationName = abstractHandleAnno.getAnnotation().getName();
                                if(annotationName.equals(serviceAnnotationName)){
                                    handleBeanServiceMap.put(annotationName, service);
                                    Object resultParam =  service.doParam(beExecuteObject);
                                    if(!beExecuteObject.equals(resultParam)) {
                                        field.set(param, resultParam);
                                    }
                                }

                            }
                        }
                    }


                }
            }
        }
        return param;
    }
}
