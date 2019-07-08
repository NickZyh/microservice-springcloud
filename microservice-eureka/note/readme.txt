服务注册与发现中心
    虽然@EableDiscoveryClient和@EnableEurekaClient没什么区别,但是在它们两表示的语义不同
        @EnableDiscoveryClient:表示服务消费者语义
        @EnableEurekaClient:表示服务提供者语义
        @EnableEurekaServer:表示eureka的服务器,即注册中心