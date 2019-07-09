### ***maven设置parent的好处*** 
使用maven是为了更好的帮项目管理包依赖,maven的核心就是pom.xml.当我们需要引入一个jar包时,在pom文件中加上dependency标签就可以从maven仓库中依赖到相应的jar包然后导入到本地
项目中;
####maven找寻仓库的顺序
本地仓库 -> 私有仓库(若存在,需配置) -> 远程仓库
#####场景一
有两个web项目A、B,一个java项目C,它们都需要用到同一个jar包：common.jar.如果分别在三个项目的pom.xml中定义各自对common.jar的依赖,那么当common.jar的版本发生变化时,三个项目的pom文件
都要改,项目越多要改的地方就越多,很麻烦.这时候就可以用到parent标签,我们创建一个parent项目,打包类型设置为pom,parent项目中不存放任何代码,只是管理多个项目之间公共的依赖.在parent项目的
pom.xml中定义对common.jar的依赖,ABC三个子项目中只需要定义&lt;parent&gt;&lt;/parent&gt;,parent标签中写上parent项目的pom坐标就可以引用到parent项目中的common.jar.
#####场景二
有一个springmvc.jar,只有AB两个web项目需要,C项目是java项目不需要,那么又要怎么去依赖.如果AB中分别定义对springmvc.jar的依赖,当springmvc.jar版本变化时修改起来又会很麻烦.解决办法是在
parent项目的pom文件中使用&lt;dependencyManagement&gt;&lt;/dependencyManagement&gt;将springmvc.jar管理起来,如果有哪个子项目要用,那么子项目在自己的pom文件中使用
````
<dependency>
    <groupId></groupId>
    <artifactId></artifactId>
</dependency>
````
标签中写上springmvc.jar的坐标,不需要写版本号,可以依赖到这个jar包了.这样springmvc.jar的版本发生变化时只需要修改parent中的版本就可以了.

####Maven生命周期
Maven的生命周期就是为了对项目构建过程进行抽象和统一,包括项目的清理、初始化、编译、测试、打包、集成测试、验证、部署和站点生成等几乎所有构建
步骤。也就是说,几乎所有项目的构建,都能映射到这样一个生命周期上。每个构建步骤都可以绑定一个或者多个插件行为,而且Maven为大多数构建步骤编
写并绑定了默认插件。例如,针对编译的插件有maven-compiler-plugin,针对测试的插件有maven-surefire-plugin等。虽然在大多数时间里,用户
几乎都不会觉察到插件的存在,但实际上编译是由maven-compiler-plugin完成的,而测试是由maven-surefire-plugin完成的(Maven可以用于执行
单元测试)。
Maven拥有三套相互独立的生命周期,它们分别为clean、default和site。clean生命周期的目的是清理项目,default生命周期的目的是构建项目,而
site生命周期的目的是建立项目站点。
单独的一套生命周期之内,每一个阶段都是顺序执行的,也就是说如果执行了后面的阶段,前面的阶段会被执行;但是三套生命周期之间是分开的,执行任一一个
周期中的阶段都不会出发另一套生命周期的阶段,即互不干涉;并且,可以使用mvn命令同时调用多个生命周期,如mvn clean package 清理并打包
1. clean生命周期
clean生命周期的目的是正式构建项目之前的清理原有项目的工作,它包含三个阶段：
````
pre-clean 执行一些清理前需要完成的工作。
clean 清理上一次构建生成的文件。
post-clean 执行一些清理后需要完成的工作。
````
2. default生命周期
````
default 生命周期定义了项目构建时真正所需要执行的所有步骤,它是所有生命周期中最核心的部分,如下只列出关键步骤:
validate 验证
compile 编译项目的主源码。一般来说,是编译src/main/java目录下的Java文件至项目输出的主classpath目录中。
test-compile 编译项目的测试代码。一般来说,是编译src/test/java目录下的Java文件至项目输出的测试classpath目录中。
test 通过插件利用单元测试框架运行测试,
package 接受编译好的代码,打包成可发布的格式,如JAR,测试代码不会被打包或部署。
install 将包传到Maven本地仓库,供本地其他Maven项目使用。
deploy 将最终的包复制到远程仓库,供其他开发人员和Maven项目使用。
````
3. site生命周期(简单了解)
前言:站点可以理解为描述当前项目的一个web网站,该网站的所有信息是基于POM生成的,例如<name>、<url>等等标签都属于站点信息
site生命周期的目的是建立和发布项目站点,Maven能够基于POM所包含的信息,自动生成一个友好的站点,方便团队交流和发布项目信息。该生命周期包含如下阶段：
````
pre-site执行一些在生成项目站点之前需要完成的工作。
site生成项目站点文档。
post-site执行一些在生成项目站点之后需要完成的工作。
site-deploy将生成的项目站点发布到服务器上。
````

####Maven常用命令
mvn clean:清理上次项目编译的结果,调用clean生命周期的clean阶段。
mvn package:完成项目编译、单元测试、打包功能;但没有把打好的可执行jar包(war包或其它形式的包)布署到本地maven仓库和远程maven私服仓库<br>
mvn install:完成项目编译、单元测试、打包功能,同时把打好的可执行jar包(war包或其它形式的包)布署到本地maven仓库;但没有布署到远程maven私服仓库<br>
mvn deploy:完成项目编译、单元测试、打包功能,同时把打好的可执行jar包(war包或其它形式的包)布署到本地maven仓库和远程maven私服仓库<br>

版本号
SNAPSHOT -- 快照版本
ALPHA -- 内侧版本
BETA -- 公测版本
RELEASE -- 稳定版本
GA -- 正式发布

<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.0</version>
      <type>jar</type>
      <scope>test</scope>
      <optional>true</optional>
    </dependency>
    ...
</dependencies>
type:依赖类型,默认类型是jar,通常表示依赖的文件的扩展名;
optional:可选的意思;首先,Maven中的依赖是具有传递关系的;假如A依赖了B,那么此时C依赖了A,则C会同时引入A和B两个依赖;
optional表示当前依赖不加入依赖继承关系;比如A依赖了B,但是B的optional为true,表示依赖可选;那么此时C依赖了A,则C不会自动依赖B;如果C想依赖B,
那么此时C只能手动加入B的依赖,即依赖传递被打断;
scope:表示当前依赖与当前项目的构建之间的关系,如下:
  - compile(编译范围):默认值,表示当前依赖需要参与项目的编译,当然后续的测试,运行周期也参与其中,是一个比较强的依赖.打包的时候通常也包含进去.      
  - provided(已提供范围)：provided意味着打包的时候可以不用包进去,别的设施(Web Container)会提供。事实上该依赖理论上可以参与编译,测试,运行等周期
    相当于compile,但是在打包阶段做了exclude的动作。      
  - runtime: runtime依赖在运行和测试系统的时候需要,但在编译的时候不需要。比如,你可能在编译的时候只需要JDBC API JAR,而只有在运行的
    时候才需要JDBC驱动实现      
  - test:在一般的编译和运行时都不需要,它们只有在测试编译和测试运行阶段可用。   
  - improt:仅用于导入pom,解决无法多继承的问题
  
  scope的依赖传递
  A–>B–>C。当前项目为A,A依赖于B,B依赖于C。知道B在A项目中的scope,那么怎么知道C在A中的scope呢？答案是： 
  当C是test或者provided时,C直接被丢弃,A不依赖C； 
  否则A依赖C,C的scope继承于B的scope。  
exclusions:如果项目A引用了一个Jar包,而该Jar包又引用了其他Jar包,那么在默认情况下项目编译时,Maven 会把直接引用和间接引用的Jar包都下载到
本地;exclusions的作用就是将不想引入的Jar包排除,如下:
<exclusions>
   <exclusion>
     <groupId>ch.qos.logback</groupId>
     <artifactId>logback-classic</artifactId>
   </exclusion>
</exclusions>

<properties>
    <maven.compiler.source>1.7<maven.compiler.source>
    <maven.compiler.target>1.7<maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
</properties>
通过 properties元素用户可以定义一个或多个 maven 属性,然后在 maven 的其他地方使用 ${属性名称} 的方式引用该属性,这种做法的意义在于消
除重复和统一管理.比如,需要在多个地方重复声明同样的 SpringFramework 版本,现在只需要在一个地方声明就可以。Maven 共有6种属性(根据引用
的来源不同)：内置属性、POM 属性、自定义属性、Settings 属性、环境变量属性等,引用方式是类似的,下面介绍其中的集中

理解scope
scope和Maven生命周期相关,不同的scope值只作用于指定的生命周期;通俗的理解scope就是说每次Maven执行生命周期时都会针对scope值去处理当前依
赖;举个scope=provided的例子。比如说,假定我们自己的项目A中有一个类叫B,B中会import这个portal-impl的artifact中的C,那么在编译阶段,我
们肯定需要这个C,否则B通不过编译,因为我们的scope设置为provided,所以编译阶段该包会被添加进项目中,所以B正确的通过了编译。测试阶段类似,故忽略。
最后当要把A部署到Liferay服务器上了,这时候,我们到$liferay-tomcat-home\webapps\ROOT\WEB-INF\lib下发现,里面已经有了一个portal-impl.jar
了,换句话说,容器已经提供了这个artifact对应的jar,所以,我们在运行阶段,这个B类直接可以用容器提供的portal-impl.jar中的C类,而不会出任何问题。
当我们用maven install生成最终的构件包ProjectABC.war后,在其下的WEB-INF/lib中,会包含我们被标注为scope=compile的构件的jar包,而不会包含
我们被标注为scope=provided的构件的jar包。这也避免了此类构件当部署到目标容器后产生包依赖冲突。
可以理解为,每个生命周期都会生成一套单独的依赖,这些依赖会根据源码中的依赖的scope值来筛选;