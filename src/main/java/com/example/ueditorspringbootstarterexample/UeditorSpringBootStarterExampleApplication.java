package com.example.ueditorspringbootstarterexample;

import com.baidu.ueditor.spring.support.qiniu.EnableQiniuUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
@EnableQiniuUploader
public class UeditorSpringBootStarterExampleApplication {

    private static final Logger logger = LoggerFactory.getLogger(UeditorSpringBootStarterExampleApplication.class);
    static int port;

    @Value("${server.port}")
    public void setPort(int port) {
        UeditorSpringBootStarterExampleApplication.port = port;
    }

    public static void main(String[] args) {
        SpringApplication.run(UeditorSpringBootStarterExampleApplication.class, args);
        logger.info("http://localhost:" + port);
    }

    @GetMapping("/")
    public String index() {
        return "ue";
    }
}
