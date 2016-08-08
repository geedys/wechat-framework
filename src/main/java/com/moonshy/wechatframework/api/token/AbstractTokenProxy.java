package com.moonshy.wechatframework.api.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * token管理抽象类，
 *
 * @author moonshy
 */
public abstract class AbstractTokenProxy implements TokenProxy {

    private static Logger logger = LoggerFactory.getLogger(AbstractTokenProxy.class);

    /**
     * 初始化时，开启调度器。
     */
    public void init() {
        logger.debug("----------启动TokenProxy调度器----------");
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this::refresh, 0, EXPIRE_TIME, TimeUnit.MILLISECONDS);
        logger.debug("----------启动TokenProxy调度器成功----------");
    }

    private String appid;
    private String appSecret;

    protected boolean isInit;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    protected static final String ACCESS_TOKEN_PREFIX = "access_token";
    protected static final String JS_TICKET_PREFIX = "js_tiket";

    @Override
    public final String accessToken(String appid, String secret) {
        String key = tokenKey();
        String accessToken = getResultValue(key);
        if (!StringUtils.isEmpty(accessToken)) {
            return accessToken;
        }
        logger.info("accessToken 不存在");
        synchronized (this) {
            if (StringUtils.isEmpty(accessToken)) {
                while (!isInit) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                        return null;
                    }
                }
                while (StringUtils.isEmpty(accessToken = getResultValue(key))) {
                    this.saveAccessToken(getAppid(), getAppSecret());
                }

            }
            logger.info("返回accessToken:" + accessToken);
            return accessToken;
        }
    }

    @Override
    public final String jsTicket(String appid, String secret) {
        String key = jsTicketKey();
        String jsTicket = getResultValue(key);
        if (!StringUtils.isEmpty(jsTicket)) {
            return jsTicket;
        }
        logger.info("jsTicket 不存在");
        synchronized (this) {
            if (StringUtils.isEmpty(jsTicket)) {
                while (!isInit) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                        return null;
                    }
                }
                while (StringUtils.isEmpty(jsTicket = getResultValue(key))) {
                    this.saveJsTicket(accessToken(appid, secret));
                }
            }
            logger.info("返回jsTiket:" + jsTicket);
            return jsTicket;
        }
    }

    @Override
    public String accessToken() {
        return accessToken(appid, appSecret);
    }

    @Override
    public String jsTicket() {
        return jsTicket(appid, appSecret);
    }

    /**
     * 获取accessToken 保存后的键
     *
     * @return
     */
    protected String tokenKey() {
        return ACCESS_TOKEN_PREFIX + this.getAppid();
    }

    /**
     * 获取jsTiket 保存后的键
     *
     * @return
     */
    protected String jsTicketKey() {
        return JS_TICKET_PREFIX + this.getAppid();
    }


    /**
     * 持久化AccessToken,从微信官网获取到access_token后保存到数据库的逻辑操作
     *
     * @param appid
     * @param appsecret
     * @return 保存后的AccessToken
     */
    protected abstract String saveAccessToken(String appid, String appsecret);

    /**
     * 持久化JSTICKET,从微信官网获取到js_ticket后保存到数据库的逻辑操作
     *
     * @param accessToken
     * @return 保存后的JsTicket
     */
    protected abstract String saveJsTicket(String accessToken);

    /**
     * 返回查询后的未超时的值。(access_token或者js_ticket)
     *
     * @param key
     * @return 根据指定的key获取值
     */
    protected abstract String getResultValue(String key);

    /**
     * 子类实现刷新Token的方法。重新获取最新的Token并保存。具体如何保存由子类提供
     */
    private synchronized void refresh() {
        logger.info("刷新最新的token");
        String accessToken = saveAccessToken(getAppid(), getAppSecret());
        saveJsTicket(accessToken);
        if (!isInit) {
            this.isInit = true;
            this.notifyAll();
        }
    }
}
