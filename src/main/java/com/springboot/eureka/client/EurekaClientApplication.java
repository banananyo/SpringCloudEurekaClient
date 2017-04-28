package com.springboot.eureka.client;

import com.netflix.discovery.converters.Auto;
import com.springboot.eureka.client.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.AvailabilityFilteringRule;

import java.util.List;

//hystrix
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@RibbonClient(name = "customer-service", configuration = CustomerServiceConfiguration.class)
//hystrix
@EnableCircuitBreaker
public class EurekaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}
}

//@RestController
//class ServiceInstanceRestController {
//
//	@Autowired
//	private DiscoveryClient discoveryClient;
//
//	@RequestMapping("/service-instances/{applicationName}")
//	public List<ServiceInstance> serviceInstancesByApplicationName(
//			@PathVariable String applicationName) {
//		return this.discoveryClient.getInstances(applicationName);
//	}
//}

class CustomerServiceConfiguration {

  @Autowired
  IClientConfig ribbonClientConfig;

  @Bean
  public IPing ribbonPing(IClientConfig config) {
    return new PingUrl();
  }

  @Bean
  public IRule ribbonRule(IClientConfig config) {
    return new AvailabilityFilteringRule();
  }

}

@RestController
class CustomerService{

	// @Bean
	// @LoadBalanced
	// public RestTemplate restTemplate(){
	// 	return new RestTemplate();
	// }

	// @Autowired
	// private RestTemplate restTemplate;

	// @RequestMapping("/")
	// String getGreeting(){
	// 	return "Hello this is Eureka-Client";
	// }

	// @RequestMapping("/getCustomerList")
	// Object getCustomerList(){
	// 	return this.restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/list", Object.class);
	// }

	// @RequestMapping("/getCustomerListMySQL")
	// Object getCustomerListMySQL(){
	// 	return this.restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/listmysql", Object.class);
	// }

	// @RequestMapping("/getPort")
	// String getPortOfInstance(){
	// 	return this.restTemplate.getForObject("http://CUSTOMER-SERVICE/getPort", String.class);
	// }

	//hystrix
	@Autowired
  	private CustomerServiceHystrix customerServiceHystrix;

	@Bean
	@LoadBalanced
	public RestTemplate rest(RestTemplateBuilder builder) {
		return builder.build();
	}

	@RequestMapping("/getCustomerList")
	Object getCustomerList(){
		return customerServiceHystrix.getCustomerList();
	}

	@RequestMapping("/getCustomerListMySQL")
	Object getCustomerListMySQL(){
		return customerServiceHystrix.getCustomerListMySQL();
	}

	@RequestMapping("/getPort")
	String getPortOfInstance(){
		return customerServiceHystrix.getPortOfInstance();
	}
}

@RefreshScope
@RestController
class MessageRestController {

	@Value("${message:eureka client default}")
	private String message;

	@RequestMapping("/message")
	String getMessage() {
		return this.message;
	}
}