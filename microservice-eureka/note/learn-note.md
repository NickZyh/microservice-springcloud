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

Eureka与Zookeeper的区别
从设计思路上看，Eureka是AP(保证可用性)型设计，ZOOKEEPER是CP(保证一致性)型设计：
--Eureka：Eureka Server可以运行多个实例来构建集群解决单点问题，但不同于ZooKeeper的选举leader过程，
Eureka Server采用的是Peer to Peer对等通信。这是一种去中心化的架构，无master/slave之分，每一个Peer 
都是对等的。在这种架构风格中，节点通过彼此互相注册来提高可用性，每个节点需要添加一个或多个有效的serviceUrl 
指向其他节点(这里的节点指的是Eureka Server集群)。每个节点都可被视为其他节点的副本。在集群环境中如果某个节
点宕机，则Eureka Client的请求会自动切换到新的节点上。当宕机节点重新恢复后，该节点会再次加入服务器集群中(因为
节点使用的自动注册,即某节点上线会主动通知其他的所有节点)。当一个新的节点启动后，会首先尝试从邻近节点获取所有
注册列表信息，并完成初始化。Eureka Server通过getEurekaServiceUrls()方法获取所有的节点，并且会通过心跳契
约的方式定期更新。默认情况下，如果Eureka Server在一定时间内没有接收到某个服务实例(包括客户端和服务端)的心跳
（默认周期为30秒），Eureka Server将会注销该实例（默认为90秒，通过eureka.instance.lease-expiration-duration-in-seconds
进行自定义配置）。当Eureka Server节点在短时间内丢失过多的心跳时，那么这个节点就会进入自我保护模式;
--Zookeeper
与Eureka有所不同，Apache Zookeeper在设计时就紧遵CP原则，即任何时候对Zookeeper的访问请求能得到最新的数据结果，同时
系统对网络分割具备容错性，但是Zookeeper不能保证每次服务请求都是可达的。从Zookeeper的实际应用情况来看，在使用
Zookeeper获取服务列表时，如果此时的Zookeeper集群中的Leader宕机了，该集群就要进行Leader的选举;一旦Zookeeper集
群中半数以上服务器节点不可用，那么将无法处理该请求。所以说，Zookeeper不能保证服务可用性。当然，在大多数分布式环境中，
尤其是涉及到数据存储的场景，数据一致性应该是首先被保证的，这也是Zookeeper设计紧遵CP原则的另一个原因。但是对于服务发
现来说，情况就不太一样了，针对同一个服务，即使注册中心的不同节点保存的服务提供者信息不尽相同，也并不会造成灾难性的后
果。因为对于服务消费者来说，能消费才是最重要的，消费者虽然拿到可能不正确的服务实例信息后尝试消费一下，也要胜过因为无法
获取实例信息而不去消费，导致系统异常要好（淘宝的双十一由于服务宕机,所以不提供服务是CP的最好参照）。
总结
1 Eureka不持久化，服务存入缓存;Zookeeper持久化;
    - 对于注册中心中的服务来说没必要持久化，因为我们只关心当前瞬时的所有服务的状态
2 Eureka通过增量更新注册信息，Zookeeper通过Watch事件监控变化;
    - 对于服务注册变化的过程，我们不关心，只关心瞬时状态
3 Eureka提供客户端缓存，Zookeeper无客户端缓存;在网络隔离注册中心访问不了的情况
下，Eureka宁可使用旧数据也需要提供服务，也不能因为暂时的网络故障而找不到可用的服务器;而Zookeeper则直接无法使用；
综上所述，Eureka适合作为服务注册发现中心，Zookeeper适合更广泛的分布式协调服务

