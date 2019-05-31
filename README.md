### 本Demo项目基于Spring Boot构建，实现后台接口的开发。
## 用到的技术：
1. 请注意，本demo为简化代码编译时加入了lombok支持，如果不了解lombok的话，请先学习下相关知识，比如可以阅读[此文章](https://mp.weixin.qq.com/s/cUc-bUcprycADfNepnSwZQ)；
1. jdk 1.8
1. 配置：复制 `/src/main/resources/application.yml.template` 或修改其扩展名生成 `application.yml` 文件，根据自己需要填写相关配置（需要注意的是：yml文件内的属性冒号后面的文字之前需要加空格，可参考已有配置，否则属性会设置不成功）；


## 特别说明：	
1. 里面已经配置了数据库（sql server2008）的连接
```      
          <!--SQL Server数据库驱动依赖, mvn本地下载安装 
          这个包需要自己网上下载 然后安装到本地仓库 最后引用到这里-->
          <dependency>
              <groupId>com.microsoft.sqlserver</groupId>
              <artifactId>sqljdbc42</artifactId>
              <version>4.2</version>
          </dependency>
```
