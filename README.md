# cs-test
add test case for your demo to spring-web

#如何使用
1. 下载或者克隆项目
2. mvn install打包项目到本地库
3. 添加以下依赖\
    \<dependency>\
        \<groupId>me.chen\</groupId>\
        \<artifactId>cs-test-core\</artifactId>\
        \<version>1.0\</version>\
    \</dependency>\
以及spring-boot web和thymeleaf相关依赖
4.使用@ComponentScan修改basePackages为me.chen包以及你的包
5.在你需要测试的类上添加@TestCase注解
6.启动项目并访问/test/index\
7.点击按钮即可对对应的方法进行测试

你也可以直接运行SampleApplication进行HelloWorld的测试