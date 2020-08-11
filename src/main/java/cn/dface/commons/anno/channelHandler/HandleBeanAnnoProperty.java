package cn.dface.commons.anno.channelHandler;



import cn.dface.commons.anno.SpringContext;
import cn.dface.commons.anno.service.AbstractHandleAnno;
import cn.dface.commons.anno.service.HandleBeanParentService;
import cn.dface.commons.anno.service.HandleParamParentService;
import cn.dface.commons.anno.service.HandleService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zyq
 * @Description 处理bean注解的参数
 * @Date 2020/8/4 14:18
 */
public class HandleBeanAnnoProperty implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 已经注册的所有的类名
     */
    public static List<String> registerClassNames = new ArrayList<>();

    /**
     * bean service的所有实现类的名称
     */
    public static List<HandleBeanParentService> beanServiceList = new ArrayList<>();

    /**
     * param service的所有实现类的名称
     */
    public static List<HandleParamParentService> paramServiceList = new ArrayList<>();


    public static Map<String, HandleService> annoConnectMap = new HashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        //获取所有resolvers
//        HandlerMethodArgumentResolverComposite resolverComposite = (HandlerMethodArgumentResolverComposite) SpringContext.getBean("handlerMethodArgumentResolverComposite");
//        resolvers =  resolverComposite.getResolvers();

        //所有的bean 放入list中
        Object bean = null;
        for(String beanName: registerClassNames){
            try {
                bean = SpringContext.getBean(beanName);
            }catch (Exception e){
            }

            // bean 为空 不放入list中
            if(null == bean){
                continue;
            }
            if(bean instanceof HandleBeanParentService){
                HandleBeanParentService beanParentService = (HandleBeanParentService) bean;
                //设置关联的注解
                AbstractHandleAnno abstractHandleAnno = (AbstractHandleAnno) bean;
                abstractHandleAnno.setAnnotation();

                annoConnectMap.put(abstractHandleAnno.getAnnotation().getName(),beanParentService);
                beanServiceList.add((HandleBeanParentService) bean);
            }

            // 如果是param的参数，放入resolvers中
            if(bean instanceof HandleParamParentService){
                paramServiceList.add((HandleParamParentService) bean);
            }

        }

    }
}
