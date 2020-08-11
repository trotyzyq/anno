package cn.dface.commons.anno.implHandler;////package cn.dface.commons.anno.implHandler;


import cn.dface.commons.anno.anno.AddSpaceAnno;
import cn.dface.commons.anno.service.AbstractHandleAnno;
import cn.dface.commons.anno.service.HandleBeanParentService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author zyq
 * @Description 清空空字符串
 * @Date 2020/7/31 16:51
 */
@Slf4j
public class AddSpaceServiceImpl extends AbstractHandleAnno implements HandleBeanParentService {

    @Override
    public void setAnnotation() {
        this.annotation = AddSpaceAnno.class;
    }


    @Override
    public Object doParam(Object param) {
        return (String)param + "111";
    }
}
