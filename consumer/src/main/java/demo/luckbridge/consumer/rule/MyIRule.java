package demo.luckbridge.consumer.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;


public class MyIRule {
    // 配置负载均衡规则

    /**
     * RandomRule: 随机
     * RoundRobinRule: 轮询 (默认)
     * WeighedResponseTimeRule: 相应速度快的权重越大
     * RetryRule: 先按照RoundRobinRule获取,失败后会重试
     * BestAvailableRule: 过滤掉多次访问处于短路跳匝状态的服务,然后选择一个并发较小的
     * ZoneAvoidanceRule: 符合判断server所在域的性能和可用性
     * @return
     */
    @Bean
    public IRule rule() {
        return new RoundRobinRule();
    }
}
