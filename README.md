# 业务驱动需求
&emsp;&emsp;日常的开发中，后端需要对前端上传数据进行处理，例如元转分，去空，脱敏等。  
&emsp;&emsp;&emsp;目前市面上的开源项目只支持项目中自带的注解，并没有支持像`DubboSpi`的扩展点开发。基于这个问题，车辙在`Spring`的基础上写了个小框架，用户可以自定义注解和实现类满足需求。
# 如何使用
# 1. 项目打包，并依赖（地址在最下方）
```
    <dependency>
        <groupId>cn.dface</groupId>
        <artifactId>anno</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
```


# 2.启动类添加扫描注解实现类包路径
## SpringBoot 
`@HandleBeanScanAnno(basePackages = "cn.dface.annocustom.web.back.anno.implHandler")`
## SpringMVC
`<context:component-scan base-package="com.xxx.**.cotroller"/>`  我也没试过😄
# 3.定义注解
```
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface TrimBlankAnno {

        String value() default "" ;

        String methodName() default "" ;

        int level() default 1;

    }
```

# 4.写实现类在(2的包路径下)    
```
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
```    
# 5.在Controller上添加包自带注解
```
@PostMapping("/testTrimAnno")
@HandleBeanAnno
public Object testTrimAnno( @RequestBody TestBean setBankInfoBean) {
    System.out.println(JSON.toJSONString(setBankInfoBean));
    return ResultVO.newSuccess(setBankInfoBean);
}
```
# 6.在入参Bean和出参Bean上添加注解
```
@TrimBlankAnno(level = 2)
@AddSpaceAnno
private String bankCardNo;

@YuanToFenAnno
private Integer amount;
```


# 7. 其他功能
1. 参数业务检查,不符合业务要求直接抛出异常
2. 不用再参数上写多个注解，一个注解满足多个条件，如先判断非空，在判断格式


# 8. 效果
## 实现类
```
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
```
## 入参
```
{
    "bankCardNo": " 123 4 5 6 ", // 去空格处理  然后加上空格
    "amount": 10         // 返回值元转分
}
```


## 出参
```
{
    "status": 0,
    "errorCode": null,
    "errorMsg": null,
    "object": {
        "bankCardNo": "123456      ",
        "amount": 1000
    }
}
```
# 结语
&emsp;&emsp;项目目前还不完善，希望大家能尝试下给点意见，欢迎下方留言。例如目前只支持`RequestBody`的bean支持，而RequestParam尚未支持，这些问题应该会在之后的版本中支持。
&emsp;&emsp;另外源码分析会在下一篇~~~
# 项目地址
https://github.com/trotyzyq/anno
