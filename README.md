微信公众平台SDK开发框架，封装了微信公众平台的接口，方便开发者快速构建微信公众号应用，专注于业务逻辑开发，提高开发效率<br>
现在本项目全面支持Spring框架。

1. TokenProxy获取AccessToken方式;<br>
   (1) DefaultTokenProxy,使用该方式，支持本机ConcurrentHashMap来缓存accessToken.
   Spring 配置：<br>
   &lt;bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.DefaultTokenProxy"<br>
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/&gt;<br>
   (2) RedisTokenProxy,使用该方式，支持Redis分布式环境。<br>
   Spring 配置：<br>
   &lt;bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.RedisTokenProxy"<br>
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/&gt;<br>
   Maven 添加依赖：<br>
   <pre>
   &lt;dependency&gt;<br>
       &lt;groupId&gt;redis.clients&lt;/groupId&gt;<br>
       &lt;artifactId&gt;jedis&lt;/artifactId&gt;<br>
       &lt;version&gt;2.9.0&lt;/version&gt;<br>
   &lt;/dependency&gt;<br>
   &lt;dependency&gt;<br>
       &lt;groupId&gt;org.springframework.data&lt;/groupId&gt;<br>
       &lt;artifactId&gt;spring-data-redis&lt;/artifactId&gt;<br>
       &lt;version&gt;1.7.2.RELEASE&lt;/version&gt;<br>
   &lt;/dependency&gt;<br>
   </pre>
  (3) MemcachedTokenProxy,使用该方式，支持Memcached缓存服务器环境。
      Spring 配置：
      &lt;bean id="tokenProxy" class="com.richong.wechatframework.api.token.impl.MemcachedTokenProxy"<br>
             p:appid="${wechat.appid}" p:appSecret="${wechat.appsecret}"/&gt;<br>
Maven 添加依赖：<br>
   <pre>
   &lt;dependency&gt;<br>
       &lt;groupId&gt;com.whalin&lt;/groupId&gt;<br>
       &lt;artifactId&gt;Memcached-Java-Client&lt;/artifactId&gt;<br>
       &lt;version&gt;3.0.2&lt;/version&gt;<br>
   &lt;/dependency&gt;<br>
   </pre>
