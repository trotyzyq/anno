package cn.dface.commons.anno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

public class LussWebBackApplication {

    protected static Logger log = LoggerFactory.getLogger(LussWebBackApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(LussWebBackApplication.class, args);
    }
}
