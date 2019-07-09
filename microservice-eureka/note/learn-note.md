@EableDiscoveryClient和@EnableEurekaClient的区别
    首先,功能上两者没有区别;以下是两个注解的包位置:
    import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
    import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
    所以,@EnableDiscoveryClient基于spring-cloud-commons, @EnableEurekaClient基
    于spring-cloud-netflix。要表达的意思就是说当注册中心为Eureka时,可以使用
    @EnableEurekaClient也可以使用@EnableDiscoveryClient;但是当注册中心为不为Eureka
    时,比如consul、zookeeper等,只能使用@EnableDiscoveryClient;可见@EnableEurekaClient
    的使用场景有局限性;所以,为了防止之后服务注册中心实现的改变,建议在微服务上使用
    @EnableDiscoveryClient;
    
集群部署eureka注册中心    
    集群部署eureka的原理就是让服务注册中心之间相互引用;以下为以三个节点部署集群
1 配置application.yml,在同一配置文件中定义三份profile，当项目启动时使用 java -jar传入参数来使用不同的配置文件片段
---
spring:
  application:
    name: spring-cloud-eureka
  profiles: peer1
server:
  port: 8000
eureka:
  instance:
    hostname: peer1
  client:
    serviceUrl:
      defaultZone: http://peer2:8001/eureka/,http://peer3:8002/eureka/
---
spring:
  application:
    name: spring-cloud-eureka
  profiles: peer2
server:
  port: 8001
eureka:
  instance:
    hostname: peer2
  client:
    serviceUrl:
      defaultZone: http://peer1:8000/eureka/,http://peer3:8002/eureka/
---
spring:
  application:
    name: spring-cloud-eureka
  profiles: peer3
server:
  port: 8002
eureka:
  instance:
    hostname: peer3
  client:
    serviceUrl:
      defaultZone: http://peer1:8000/eureka/,http://peer2:8001/eureka/ 
2 由于此时是在本机运行多个项目,此时的eureka.instance.hostname设置为peer(任意指定,当然也可以直接用localhost),
所以此时需要再本地计算机的host文件中做出ip的名称映射,配置如下:
127.0.0.1 peer1  
127.0.0.1 peer2        
备注:实际上就是为本机ip映射出一个名称,localhost的原理也就是在hosts文件中映射了ip,如下
127.0.0.1 localhost     
3 直接本地打成Jar包
mvn clean package
4 将jar包分别以不同的profile的形式执行,从而执行出多个实例      
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer3
备注:spring-cloud-eureka-0.0.1-SNAPSHOT.jar是本地打包出来的Jar包名称,在pom中定义的    