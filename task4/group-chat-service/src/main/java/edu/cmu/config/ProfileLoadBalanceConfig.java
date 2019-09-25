package edu.cmu.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Config load balancer, which is using Spring Cloud Ribbon.
 * To achieve sending requests to different profile service endpoints
 * <p>
 * For more information about Ribbon, refer to: https://spring.io/guides/gs/client-side-load-balancing/
 *
 * @author lucas
 */
public class ProfileLoadBalanceConfig {

    /**
     * Store client configuration for a client or load balancer.
     */
    @Autowired
    IClientConfig ribbonClientConfig;

    /**
     * Define how periodic pings of a server are performed.
     *
     * @param config information
     * @return IPing
     */
    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    /**
     * Describe a load balancing strategy, to have fault tolerance.
     *
     * @param config information
     * @return load balance rule
     */
    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }

}
