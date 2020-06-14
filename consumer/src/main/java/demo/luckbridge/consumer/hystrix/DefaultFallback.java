package demo.luckbridge.consumer.hystrix;

import demo.luckbridge.consumer.service.FeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义服务降级类，
 * 可使用在FeignClient的fallbackFactory属性上
 * 需要开启feign hystrix
 */
@Component
public class DefaultFallback implements FallbackFactory<FeignService> {

    @Override
    public FeignService create(Throwable cause) {
        return new FeignService() {
            @Override
            public String getPort() {
                return "服务降级了!";
            }
        };
    }
}
