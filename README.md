微信公众平台SDK开发框架，封装了微信公众平台的接口，方便开发者快速构建微信公众号应用，专注于业务逻辑开发，提高开发效率
现在本项目全面支持Spring框架。

1. TokenProxy获取AccessToken方式<br>
   (1) DefaultTokenProxy,使用该方式，支持本机ConcurrentHashMap来缓存accessToken.<br>
   Spring 配置：<br>
   <bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.DefaultTokenProxy"<br>
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/><br>
   (2) RedisTokenProxy,使用该方式，支持Redis分布式环境。<br>
   Spring 配置：<br>
   <bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.RedisTokenProxy"<br>
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/><br>
   Maven 添加依赖：<br>
   <dependency><br>
       <groupId>redis.clients</groupId><br>
       <artifactId>jedis</artifactId><br>
       <version>2.9.0</version><br>
   </dependency><br>
   <dependency><br>
       <groupId>org.springframework.data</groupId><br>
       <artifactId>spring-data-redis</artifactId><br>
       <version>1.7.2.RELEASE</version><br>
   </dependency><br>