package demo.luckbridge.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import demo.luckbridge.consumer.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/consumer")
public class Knock {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SpringClientFactory factory;

    @Autowired
    private FeignService feignService;

    private final String KnockURL = "http://producer/provide";

    /**
     * 仅使用 RestTemplate实现服务调用,没有使用Ribbon实现负载均衡
     * @return String
     */
    @GetMapping("/restTemplate")
    public String knockByTemplate() {
        final String SERVICE_NAME = "producer";
        final String PATH = "/provide";
        RestTemplate rt = new RestTemplate();
        ILoadBalancer lb = factory.getLoadBalancer(SERVICE_NAME); // 获取负载均衡器
        List<Server> list = lb.getAllServers(); // lb.getReachableServers() 可用的
        int index = new Random().nextInt(list.size()); // 随机算法 [)
        Server curServer = list.get(index); // 获取的实例
        final String FINAL_PATH = "http://" + curServer.getHost() + ":" + curServer.getPort() + PATH; // 拼接路径
        return rt.getForObject(FINAL_PATH, String.class);
    }

    /**
     * 使用 RestTemplate+Ribbon 实现跨服务调用
     * @return String
     */
    @GetMapping("/ribbon")
    @HystrixCommand(fallbackMethod = "defaultByPlaceOrder")
    public String knockByRibbon() {
        return restTemplate.getForObject(KnockURL, String.class);
    }

    /**
     * 使用 feign 调用服务
     * @return String
     */
    @GetMapping("/feign")
    public String knockByFeign() {
        String a = feignService.getPort();
        System.out.println(a);
        return a;
    }

    public String defaultByPlaceOrder() {
        return "服务熔断";
    }
}
