package com.moonshy.wechatframework.api.token.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.moonshy.wechatframework.api.jsapi.WxJsSDKAPI;
import com.moonshy.wechatframework.api.token.AbstractTokenProxy;
import com.moonshy.wechatframework.api.token.WxAccessTokenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * 使用MongoDB存储AccessToken.
 *
 * @author moonshy Created on 2016-07-26 15:17
 */
public class MongodbTokenProxy extends AbstractTokenProxy {
    private static Logger logger = LoggerFactory.getLogger(MongodbTokenProxy.class);

    private String collectionName = "ACCESS_TOKEN_COLLECTION";
    private final static String COLUMN_KEY = "key";
    private final static String COLUMN_TOKEN = "token";
    private final static String COLUMN_EXPIRE = "expire";


    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public String saveAccessToken(String appid, String appsecret) {
        String key = tokenKey();
        DBCollection collection = mongoTemplate.getCollection(collectionName);
        String accessToken = this.getResultValue(key);
        if (accessToken == null) {
            try {
                collection.createIndex(COLUMN_KEY);
                logger.info("首次插入token到MongoDB,创建索引：key");
            } catch (Exception e) {
                logger.info("key 索引已存在");
            }
            accessToken = WxAccessTokenAPI.getAccess_token(appid, appsecret);
            logger.info("从微信服务器获取到新的AccessToken:" + accessToken);
            DBObject dbObject = new BasicDBObject(COLUMN_KEY, key).append(COLUMN_TOKEN, accessToken).append(COLUMN_EXPIRE, System.currentTimeMillis() + EXPIRE_TIME);
            logger.info("将最新的 accessToken 插入MongoDB");
            collection.insert(dbObject);
        } else {
            DBObject queryObject = createQueryObject(key);
            logger.info("accessToken 过期");
            accessToken = WxAccessTokenAPI.getAccess_token(appid, appsecret);
            logger.info("从微信服务器获取到新的AccessToken:" + accessToken);
            DBObject dbObject = new BasicDBObject();
            dbObject.put(COLUMN_KEY, key);
            dbObject.put(COLUMN_TOKEN, accessToken);
            dbObject.put(COLUMN_EXPIRE, System.currentTimeMillis() + EXPIRE_TIME);
            logger.info("将最新的 accessToken 更新MongoDB");
            collection.update(queryObject, dbObject);
        }
        return accessToken;
    }

    @Override
    public String getResultValue(String key) {
        DBCollection collection = mongoTemplate.getCollection(collectionName);
        //查询没有超时的记录。
        DBObject queryObject = createQueryObjectNoExpire(key);
        DBCursor dbCursor = collection.find(queryObject);
        if (dbCursor.hasNext()) {
            return dbCursor.next().get("COLUMN_TOKEN").toString();
        }
        return null;
    }

    @Override
    public String saveJsTicket(String accessToken) {
        String key = jsTicketKey();
        DBCollection collection = mongoTemplate.getCollection(collectionName);
        String jsTicket = this.getResultValue(key);
        if (jsTicket == null) {
            try {
                collection.createIndex(COLUMN_KEY);
                logger.info("首次插入JsTicket到MongoDB,创建索引：key");
            } catch (Exception e) {
                logger.info("key 索引已存在");
            }
            jsTicket = WxJsSDKAPI.getJsTicket(accessToken);
            logger.info("从微信服务器获取到新的JsTicket:" + jsTicket);
            DBObject dbObject = new BasicDBObject(COLUMN_KEY, key).append(COLUMN_TOKEN, jsTicket).append(COLUMN_EXPIRE, System.currentTimeMillis() + EXPIRE_TIME);
            logger.info("将最新的 JsTicket 插入MongoDB");
            collection.insert(dbObject);
        } else {
            DBObject queryObject = createQueryObject(key);
            logger.info("JsTicket 过期");
            jsTicket = WxJsSDKAPI.getJsTicket(accessToken);
            logger.info("从微信服务器获取到新的JsTicket:" + jsTicket);
            DBObject dbObject = new BasicDBObject();
            dbObject.put(COLUMN_KEY, key);
            dbObject.put(COLUMN_TOKEN, jsTicket);
            dbObject.put(COLUMN_EXPIRE, System.currentTimeMillis() + EXPIRE_TIME);
            logger.info("将最新的 JsTicket 更新MongoDB");
            collection.update(queryObject, dbObject);
        }
        return jsTicket;
    }

    private DBObject createQueryObject(String key) {
        return new Query(Criteria.where(COLUMN_KEY).is(key)).getQueryObject();
    }

    private DBObject createQueryObjectNoExpire(String key) {
        return new Query(Criteria.where(COLUMN_KEY).is(key).and(COLUMN_EXPIRE).gt(new Date())).getQueryObject();
    }
}
