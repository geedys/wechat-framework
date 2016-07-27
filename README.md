微信公众平台SDK开发框架，封装了微信公众平台的接口，方便开发者快速构建微信公众号应用，专注于业务逻辑开发，提高开发效率<br>
现在本项目全面支持Spring框架。

1. TokenProxy获取AccessToken方式;<br>
   (1) DefaultTokenProxy,使用该方式，支持本机ConcurrentHashMap来缓存accessToken.<br>
   Spring 配置：<br>
   &lt;bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.DefaultTokenProxy"<br>
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/&gt;<br>
   (2) RedisTokenProxy,使用该方式，支持Redis分布式环境。<br>
   Spring 配置：<br>
   &lt;bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.RedisTokenProxy"<br>
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/&gt;<br>
   参考配置文件：src/main/resources/spring-redis-example.xml<br>
   Maven 添加依赖：<br>
   <pre>
   &lt;dependency&gt;
       &lt;groupId&gt;redis.clients&lt;/groupId&gt;
       &lt;artifactId&gt;jedis&lt;/artifactId&gt;
       &lt;version&gt;2.9.0&lt;/version&gt;
   &lt;/dependency&gt;
   &lt;dependency&gt;
       &lt;groupId&gt;org.springframework.data&lt;/groupId&gt;
       &lt;artifactId&gt;spring-data-redis&lt;/artifactId&gt;
       &lt;version&gt;1.7.2.RELEASE&lt;/version&gt;
   &lt;/dependency&gt;
   </pre>
   (3) MemcachedTokenProxy,使用该方式，支持Memcached缓存服务器环境。<br>
      Spring 配置：<br>
      &lt;bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.MemcachedTokenProxy"<br>
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/&gt;<br>
      参考配置文件：src/main/resources/spring-memcached-example.xml<br>
      Maven 添加依赖：
   <pre>
   &lt;dependency&gt;
       &lt;groupId&gt;com.whalin&lt;/groupId&gt;
       &lt;artifactId&gt;Memcached-Java-Client&lt;/artifactId&gt;
       &lt;version&gt;3.0.2&lt;/version&gt;
   &lt;/dependency&gt;
   </pre>
   (4) MongoDBTokenProxy,使用该方式，支持MongoDB数据库存储。
       Spring 配置：<br/>
       &lt;bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.MongoDBTokenProxy"
               p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}" p:collectionName="xxx"/&gt;<br>
       参考配置文件：src/main/resources/spring-mongodb-example.xml<br>
       Notice:<font color="#00aa00">collectionName</font>属性用于配置MongoDB数据库中的集合名。<br>
       Maven 添加依赖：
   <pre>
   &lt;dependency&gt;
       &lt;groupId&gt;org.mongodb&lt;/groupId&gt;
       &lt;artifactId&gt;mongodb-driver&lt;/artifactId&gt;
       &lt;version&gt;3.3.0&lt;/version&gt;
   &lt;/dependency&gt;
   &lt;dependency&gt;
       &lt;groupId&gt;org.springframework.data&lt;/groupId&gt;
       &lt;artifactId&gt;spring-data-mongodb&lt;/artifactId&gt;
       &lt;version&gt;1.9.2.RELEASE&lt;/version&gt;
       &lt;exclusions&gt;
           &lt;exclusion&gt;
               &lt;groupId&gt;org.mongodb&lt;/groupId&gt;
               &lt;artifactId&gt;mongo-java-driver&lt;/artifactId&gt;
           &lt;/exclusion&gt;
       &lt;/exclusions&gt;
   &lt;/dependency&gt;
   </pre>