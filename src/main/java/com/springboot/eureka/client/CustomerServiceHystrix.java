package com.springboot.eureka.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class CustomerServiceHystrix {

  private final RestTemplate restTemplate;

  public CustomerServiceHystrix(RestTemplate rest) {
    this.restTemplate = rest;
  }

  @HystrixCommand(fallbackMethod = "reliable")
  public Object getCustomerList() {
    URI uri = URI.create("http://CUSTOMER-SERVICE/customer/list");
    return this.restTemplate.getForObject(uri, Object.class);
  }

  @HystrixCommand(fallbackMethod = "reliable")
  public Object getCustomerListMySQL() {
    URI uri = URI.create("http://CUSTOMER-SERVICE/customer/listmysql");
    return this.restTemplate.getForObject(uri, Object.class);
  }

  @HystrixCommand(fallbackMethod = "reliable")
  public String getPortOfInstance() {
    URI uri = URI.create("http://CUSTOMER-SERVICE/getPort");
    return this.restTemplate.getForObject(uri, String.class);
  }

  public String reliable() {
    return "<h1 style=\"color:red\">Server Down</h>";
  }

}