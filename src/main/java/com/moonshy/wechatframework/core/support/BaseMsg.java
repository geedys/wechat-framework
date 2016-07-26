package com.moonshy.wechatframework.core.support;

import java.time.LocalDateTime;

/**
 * Created by moonshy on 2016/7/5.
 */
public class BaseMsg {
    /**
     * 发送者标识
     */
    public String fromUser;

    /**
     * 消息表示。普通消息时，为msgid，事件消息时，为事件的创建时间
     */
    public String msgFlag;

    /**
     * 添加到队列的时间
     */
    public LocalDateTime createTime;

    public BaseMsg(LocalDateTime createTime, String fromUser, String msgFlag) {
        this.createTime = createTime;
        this.fromUser = fromUser;
        this.msgFlag = msgFlag;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        createTime = createTime;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        fromUser = fromUser;
    }

    public String getMsgFlag() {
        return msgFlag;
    }

    public void setMsgFlag(String msgFlag) {
        this.msgFlag = msgFlag;
    }
}
