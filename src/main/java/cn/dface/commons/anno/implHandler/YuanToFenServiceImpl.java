package cn.dface.commons.anno.implHandler;////package cn.dface.commons.anno.implHandler;


import cn.dface.commons.anno.anno.YuanToFenAnno;
import cn.dface.commons.anno.service.AbstractHandleAnno;
import cn.dface.commons.anno.service.HandleBeanParentService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author zyq
 * @Description 清空空字符串
 * @Date 2020/7/31 16:51
 */
@Slf4j
public class YuanToFenServiceImpl extends AbstractHandleAnno implements HandleBeanParentService {

    @Override
    public void setAnnotation() {
        this.annotation = YuanToFenAnno.class;
    }


    @Override
    public Object doParam(Object param) {
        Integer originParam = (Integer) param;
        return  originParam * 100;
    }
}
