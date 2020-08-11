package cn.dface.commons.anno.implHandler;



import cn.dface.commons.anno.anno.TrimBlankAnno;
import cn.dface.commons.anno.service.AbstractHandleAnno;
import cn.dface.commons.anno.service.HandleBeanParentService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author zyq
 * @Description 清空空字符串
 * @Date 2020/7/31 16:51
 */
@Slf4j
public class TrimBlankAnnoServiceImpl extends AbstractHandleAnno implements HandleBeanParentService {


    @Override
    public void setAnnotation() {
        this.annotation = TrimBlankAnno.class;
    }


    @Override
    public Object doParam(Object param) {
        String str = ((String)param).replaceAll("1","333");
        return str;
    }
}
