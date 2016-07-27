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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public String accessToken(String appid, String secret) {
        String key = ACCESS_TOKEN_PREFIX + appid;
        DBCollection collection = mongoTemplate.getCollection(collectionName);
        DBObject queryObject = new Query(Criteria.where(COLUMN_KEY).is(key)).getQueryObject();
        synchronized (this) {
            DBCursor dbCursor = collection.find(queryObject);
            if (dbCursor.hasNext()) {
                DBObject next = dbCursor.next();
                if (Long.valueOf(next.get(COLUMN_EXPIRE).toString()) < System.currentTimeMillis()) {
                    logger.info("accessToken 过期");
                    String accessToken = WxAccessTokenAPI.getAccess_token(appid, secret);
                    logger.info("获取到新的AccessToken:" + accessToken);
                    BasicDBObject basicDBObject = new BasicDBObject(COLUMN_KEY, key).append(COLUMN_TOKEN, accessToken).append(COLUMN_EXPIRE, System.currentTimeMillis() + EXPIRE_TIME);
                    logger.info("将最新的 accessToken 更新MongoDB");
                    collection.update(queryObject, basicDBObject);
                    return accessToken;
                } else {
                    return next.get(COLUMN_TOKEN).toString();
                }
            } else {
                try {
                    collection.createIndex(COLUMN_KEY);
                    logger.info("首次插入token到MongoDB,创建索引：key");
                } catch (Exception e) {
                    logger.info("key 索引已存在");
                }
                String accessToken = WxAccessTokenAPI.getAccess_token(appid, secret);
                logger.info("获取到新的AccessToken:" + accessToken);
                BasicDBObject basicDBObject = new BasicDBObject(COLUMN_KEY, key).append(COLUMN_TOKEN, accessToken).append(COLUMN_EXPIRE, System.currentTimeMillis() + EXPIRE_TIME);
                logger.info("将最新的 accessToken 插入MongoDB");
                collection.insert(basicDBObject);
                return accessToken;
            }
        }
    }

    @Override
    @Transactional
    public String jsTiket(String appid, String secret) {
        String key = JS_TIKET_PREFIX + appid;
        DBCollection collection = mongoTemplate.getCollection(collectionName);
        DBObject queryObject = new Query(Criteria.where(COLUMN_KEY).is(key)).getQueryObject();
        synchronized (this) {
            DBCursor dbCursor = collection.find(queryObject);
            if (dbCursor.hasNext()) {
                DBObject next = dbCursor.next();
                if (Long.valueOf(next.get(COLUMN_EXPIRE).toString()) < System.currentTimeMillis()) {
                    logger.info("jstiket 过期");
                    String accessToken = WxJsSDKAPI.getJs_tiket(accessToken(appid, secret));
                    logger.info("获取到新的jstiket:" + accessToken);
                    BasicDBObject basicDBObject = new BasicDBObject(COLUMN_KEY, key).append(COLUMN_TOKEN, accessToken).append(COLUMN_EXPIRE, System.currentTimeMillis() + EXPIRE_TIME);
                    logger.info("将最新的jstiket 更新MongoDB");
                    collection.update(queryObject, basicDBObject);
                    return accessToken;
                } else {
                    return next.get(COLUMN_TOKEN).toString();
                }
            } else {
                try {
                    collection.createIndex(COLUMN_KEY);
                    logger.info("首次插入jstiket到MongoDB,创建索引：key");
                } catch (Exception e) {
                    logger.info("key 索引已存在");
                }
                String accessToken = WxJsSDKAPI.getJs_tiket(accessToken(appid, secret));
                logger.info("获取到新的jstiket:" + accessToken);
                BasicDBObject basicDBObject = new BasicDBObject(COLUMN_KEY, key).append(COLUMN_TOKEN, accessToken).append(COLUMN_EXPIRE, System.currentTimeMillis() + EXPIRE_TIME);
                logger.info("将最新的 accessToken 插入MongoDB");
                collection.insert(basicDBObject);
                return accessToken;
            }
        }
    }
}
