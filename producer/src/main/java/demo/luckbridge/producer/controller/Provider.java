package demo.luckbridge.producer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;

@RestController
@RequestMapping("/provide")
@RefreshScope
public class Provider {

    @Value("${server.port}")
    private Integer port;

    @Value("${env}")
    private String env;

    @GetMapping
    public String getPort() {
        System.out.println("current port: " + port);
        // int j = 1 / 0;
        return "provider port: " + port; // 打印实例的端口号
    }

    @GetMapping("/{str}")
    public String say(@PathVariable String str){
        System.out.println(env);
        return str;
    }
}
