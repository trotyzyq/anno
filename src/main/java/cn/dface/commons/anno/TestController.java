package cn.dface.commons.anno;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zyq
 * @Description 测试controller
 * @Date 2020/5/18 17:32
 */
@RequestMapping("/back")
@RestController
public class TestController {


    @GetMapping("/testAll")
    public void testAll(Integer type, String payOrderNo){

    }

    /** 测试个人入账 **/
    @GetMapping("/testPersonalEntry")
    public void testPersonalEntry(Integer year, Integer month, Integer day){
    }

}
