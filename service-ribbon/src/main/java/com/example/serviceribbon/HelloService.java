package com.example.serviceribbon;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")//添加此处注
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
//        return "hi "+name+",i am from port: 8764";
    }

    public String hiError(String name) {
//        try {
//            Thread.sleep(10000);
//        } catch (Exception e) {
//            e.getStackTrace();
//        }

        return "hi,"+name+",HystrixTest,sorry,error!";
    }
}
