package demo.luckbridge.consumer.service;

import demo.luckbridge.consumer.hystrix.DefaultFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * feign使用声明式的接口调用服务
 */
@FeignClient(value = "producer", fallbackFactory = DefaultFallback.class)
public interface FeignService {

    @RequestMapping("/provide")
    String getPort();
}
