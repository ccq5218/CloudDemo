package cn.itcast.web;

import cn.itcast.client.UserClient;
import cn.itcast.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("consumer")
public class ConsumerController {
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private UserClient userClient;


//    @Autowired
//    private DiscoveryClient discoveryClient;
//    @Autowired
//    private RibbonLoadBalancerClient balancerClient;


//    @GetMapping("/{id}")
//    public User queryById(@PathVariable("id") Long id) {
////        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
////        ServiceInstance instance =instances.get(0);
//
////        ServiceInstance instance = balancerClient.choose("user-service");
////        String url = "http://"+instance.getHost()+":"+instance.getPort()+ "/user/"+id;
//
//        String url = "http://user-service/user/"+id;
//        User user = restTemplate.getForObject(url,User.class);
//        return user;
//    }

    @GetMapping("/{id}")
    @HystrixCommand(fallbackMethod = "queryByIdFallback",commandProperties = {
            @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds" ,value ="2000")
    })
    public String queryById(@PathVariable("id") Long id) {
//        String url = "http://user-service/user/"+id;
//        String user = restTemplate.getForObject(url,String.class);
        String user = userClient.queryById(id);
        return user;
    }

    public String queryByIdFallback(Long id) {
        return "不好意思，服务器拥挤。。。";
    }


}
