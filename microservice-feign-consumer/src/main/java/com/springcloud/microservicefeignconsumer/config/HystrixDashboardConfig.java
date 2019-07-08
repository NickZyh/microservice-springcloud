package com.springcloud.microservicefeignconsumer.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Zyh
 * @Date 2019/6/30 17:36
 * @Description
 * @Note
 */
@Configuration
public class HystrixDashboardConfig {

    /**
     * spring boot 2.*的版本需要实现一个servlet来访问监控仪表盘
     * springboot中创建servlet需要使用ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        // 创建一个仪表盘监控的servlet
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        // 使用ServletRegistrationBean进行注册
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);

        // 以下为配置声明的servlet

        // 加载顺序
        registrationBean.setLoadOnStartup(1);

        // 配置该servlet的访问路径
        registrationBean.addUrlMappings("/hystrix.stream");

        // servlet的名字
        registrationBean.setName("HystrixMetricsStreamServlet");

        return registrationBean;
    }



}
