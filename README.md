#wechat-framework(v1.0.0)文档
###微信公众平台SDK开发框架，封装了微信公众平台的接口，方便开发者快速构建微信公众号应用，专注于业务逻辑开发，提高开发效率。  
####现在本项目全面支持Spring框架。
##TokenProxy获取AccessToken方式
   - DefaultTokenProxy方式  
   支持本机ConcurrentHashMap来缓存accessToken.  
***Spring 配置***：  
   \<bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.DefaultTokenProxy"  
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/>
   - RedisTokenProxy方式，支持Redis分布式环境。  
***Spring 配置***：  
   \<bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.RedisTokenProxy"  
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/>  
   参考配置文件：src/main/resources/spring-redis-example.xml  
   Maven 添加依赖：  
   ```xml
    <dependency>  
      <groupId>redis.clients</groupId>  
      <artifactId>jedis</artifactId>  
      <version>2.9.0</version>  
   </dependency>  
   
   <dependency>  
      <groupId>org.springframework.data</groupId>  
      <artifactId>spring-data-redis</artifactId>  
      <version>1.7.2.RELEASE</version>  
   </dependency>  
   ```
   - MemcachedTokenProxy,使用该方式，支持Memcached缓存服务器环境。  
***Spring 配置***：  
      \<bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.MemcachedTokenProxy"  
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/>  
   参考配置文件：src/main/resources/spring-memcached-example.xml  
      Maven 添加依赖:  

   ```xml
   <dependency>  
      <groupId>com.whalin</groupId>  
      <artifactId>Memcached-Java-Client</artifactId>  
      <version>3.0.2</version>  
   </dependency>
   ```
   - MongoDBTokenProxy,使用该方式，支持MongoDB数据库存储。  
***Spring 配置***：  
      \<bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.MongoDBTokenProxy"
               p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}" p:collectionName="xxx"/>  
   参考配置文件：src/main/resources/spring-mongodb-example.xml  
       Notice:***collectionName***属性用于配置MongoDB数据库中的集合名。  
       Maven 添加依赖：  
   ```xml
   <dependency>
      <groupId>org.mongodb</groupId>  
      <artifactId>mongodb-driver</artifactId>  
      <version>3.3.0</version>
   </dependency>  
   
   <dependency>  
      <groupId>org.springframework.data</groupId>  
      <artifactId>spring-data-mongodb</artifactId>  
      <version>1.9.2.RELEASE</version>  
      <exclusions>  
         <exclusion>  
            <groupId>org.mongodb</groupId>  
            <artifactId>mongo-java-driver</artifactId>  
         </exclusion>  
      </exclusions>  
   </dependency>
   ```
