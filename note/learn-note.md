####eureka产生问题 - 什么是CAP理论,与ACID理论的关系
#####分布式概念
一个业务分拆多个子业务，部署在不同的服务器上；一般来说,将一个子业务(服务)称为一个节点；
#####CAP介绍
C - 数据一致性(consistency) ：每次读取要么获得最近写入的数据，要么获得一个错误。
A - 可用性(availability)：每次请求都能获得一个（非错误）响应，但不保证返回的是最新写入的数据(保证系统时时可用);
P - 分区容错性(partition-tolerance):尽管任意数量的消息被节点间的网络丢失或延迟(网络分区)，系统仍继续运行；分区导致的
直接后果就是该两个节点之间的数据无法传递,即无法达到一致性;
#####CAP理解
在分布式系统中,分区的情况一定是客观存在,所以系统分区容错性是必须的,P必然被实现。剩下CA选择其一;CAP理论中,C和P是无法兼顾的,但是,CAP理论中的C表示的是强
一致性,所以，CAP理论定义的其实是在容忍网络分区的条件下，“强一致性”和“极致可用性”无法同时达到。
#####可用性和一致性的权衡
场景：一个分布式系统,假设此时出现了网络分区,刚好此时有一个注册用户的请求,但是由于此时节点一和节点三之间是无法通信的(数据无法同步),那么：
1 如果允许当前用户注册一个账户，此时注册的记录数据只会在节点一和节点二或者节点二和节点三同步，因为节点一和节点三的记录不能同步的，等到网络分区消除后再同
步数据，但是此时就会出现该账户再某一个节点上无法登录，导致无法使用服务。这种情况其实就是选择了系统高可用性(availability)，抛弃了数据强一致性(consistency)
2 如果不允许当前用户注册一个账户，只有节点一和节点三恢复通信才允许注册。节点一和节点三一旦恢复通信，我们就可以保证节点拥有的数据是最新版本。那么这样就导
致用户无法使用整个系统，导致用户体验下降；这种情况其实就是抛弃了可用性(availability)，选择了数据一致性(consistency)
总结：在分布式系统中，我们需要假设 P 是常态，所以一般的分布式系统在发生分区，总是在通过各种方式在 C 和 A 中做一个妥协：
CP without A：当发生分区的时候，节点之间的通信出现问题，各个节点的数据和状态无法收敛，这种系统会为了数据一致性，让整个系统陷入不可用，防止出现数据不一致，常
见的比如银行系统
AP without C：当发生分区的时候，保证系统可用性，而不追求数据的一致性，极端的情况就是每个节点都成了孤岛，各自使用本身能获取到的数据对外提供服务。常见的比如在
线即时游戏，出现各种问题的时候，并不需要考虑 30s 之前画面是什么，只需要你当前的请求能够得到响应即可
