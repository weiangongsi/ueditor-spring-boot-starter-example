package com.example.ueditorspringbootstarterexample;

import com.baidu.ueditor.spring.support.qiniu.EnableQiniuUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
//@EnableQiniuUploader
public class UeditorSpringBootStarterExampleApplication {

    private static final Logger log = LoggerFactory.getLogger(UeditorSpringBootStarterExampleApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(UeditorSpringBootStarterExampleApplication.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");
        contextPath = contextPath == null ? "" : contextPath;
        String url = "http://localhost:" + port + contextPath;
        log.info(url);
    }

    @GetMapping("/")
    public String index() {
        return "ue";
    }
}
