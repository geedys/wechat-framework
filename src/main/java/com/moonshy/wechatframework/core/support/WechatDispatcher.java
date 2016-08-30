package com.moonshy.wechatframework.core.support;

import com.moonshy.wechatframework.core.common.EventType;
import com.moonshy.wechatframework.core.common.MsgType;
import com.moonshy.wechatframework.core.common.WechatCommonConst;
import com.moonshy.wechatframework.core.msg.*;
import com.moonshy.wechatframework.core.requestMsg.WechatRequest;
import com.moonshy.wechatframework.core.responseMsg.WechatResponse;
import com.moonshy.wechatframework.core.util.XmlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 微信服务器与用户消息转发控制中心
 *
 * @author
 */
abstract class WechatDispatcher implements IDispatcher {
    static Logger logger = LoggerFactory.getLogger(WechatDispatcher.class);

    /**
     * 微信服务器消息入口
     *
     * @param wechatRequest
     * @return 返回处理后的消息
     * @throws Exception
     */
    public String service(WechatRequest wechatRequest) {
        try {
            excludeRequest(wechatRequest);
            String xml = null;
            // 消息类型
            switch (MsgType.valueOf(wechatRequest.getMsgType())) {
                case event:
                    xml = dispatchEvent(wechatRequest);
                    break;
                default:
                    xml = dispatchMessage(wechatRequest);
                    break;
            }
            return xml;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getCause().getMessage());
        }
        return WechatCommonConst.SERVICE_ERROR.getValue();
    }



    /**
     * 普通消息分发中心
     *
     * @param wechatRequest
     * @return
     * @throws Exception
     */
    private String dispatchMessage(WechatRequest wechatRequest) throws Exception {
        // 消息类型
        MsgType msgType = MsgType.valueOf(wechatRequest.getMsgType());
        String msgResponse = null;
        switch (msgType) {
            case text:
                msgResponse = onText(wechatRequest);
                break;
            case image:
                msgResponse = onImage(wechatRequest);
                break;
            case voice:
                msgResponse = onVoice(wechatRequest);
                break;
            case video:
                msgResponse = onVideo(wechatRequest);
                break;
            case shortvideo:
                msgResponse = onShortVideo(wechatRequest);
                break;
            case location:
                msgResponse = onLocation(wechatRequest);
                break;
            case link:
                msgResponse = onLink(wechatRequest);
                break;
            default:
                msgResponse = onUnknown(wechatRequest);
                break;
        }
        return msgResponse;
    }

    /**
     * 事件消息分发中心
     *
     * @param wechatRequest
     * @return
     * @throws Exception
     */
    private String dispatchEvent(WechatRequest wechatRequest) throws Exception {
        // 事件推送
        // 事件类型
        EventType eventType = EventType.valueOf(wechatRequest.getEvent());
        String msgResponse = null;
        switch (eventType) {
            case CLICK:
                msgResponse = click(wechatRequest);
                break;
            case subscribe:
                msgResponse = subscribe(wechatRequest);
                break;
            case unsubscribe:
                msgResponse = unSubscribe(wechatRequest);
                break;
            case SCAN:
                msgResponse = scan(wechatRequest);
                break;
            case LOCATION:
                msgResponse = location(wechatRequest);
                break;
            case VIEW:
                msgResponse = view(wechatRequest);
                break;
            case TEMPLATESENDJOBFINISH:
                msgResponse = templateMsgCallback(wechatRequest);
                break;
            case scancode_push:
                msgResponse = scanCodePush(wechatRequest);
                break;
            case scancode_waitmsg:
                msgResponse = scanCodeWaitMsg(wechatRequest);
                break;
            case pic_sysphoto:
                msgResponse = picSysPhoto(wechatRequest);
                break;
            case pic_photo_or_album:
                msgResponse = picPhotoOrAlbum(wechatRequest);
                break;
            case pic_weixin:
                msgResponse = picWeixin(wechatRequest);
                break;
            case location_select:
                msgResponse = locationSelect(wechatRequest);
                break;
            case kf_create_session:
                msgResponse = kfCreateSession(wechatRequest);
                break;
            case kf_close_session:
                msgResponse = kfCloseSession(wechatRequest);
                break;
            case kf_switch_session:
                msgResponse = kfSwitchSession(wechatRequest);
                break;
            default:
                msgResponse = onUnknown(wechatRequest);
                break;
        }
        return msgResponse;
    }

    /**
     * 将返回消息对象格式化为微信服务器所需要的XML格式字符串
     *
     * @param wechatRequest
     * @param wechatResponse
     * @return
     */
    private String getWechatResponse(WechatRequest wechatRequest, WechatResponse wechatResponse) {
        return XmlHelper.toXML(setWechatResponseBase(wechatRequest, wechatResponse));
    }

    /**
     * 设置返回消息对象的基础数据
     *
     * @param wechatRequest
     * @param response
     * @return
     */
    private WechatResponse setWechatResponseBase(WechatRequest wechatRequest, WechatResponse response) {
        // 发送方帐号, 公告号将要向该号发送回复消息
        // 开发者微信帐号
        response.setToUserName(wechatRequest.getFromUserName());
        response.setFromUserName(wechatRequest.getToUserName());
        response.setCreateTime(System.currentTimeMillis());
        return response;
    }

    protected final String textResponse(WechatRequest wechatRequest, String content) {
        WechatResponse response = new WechatResponse();
        response.setContent(content);
        response.setMsgType(MsgType.text.name());
        return getWechatResponse(wechatRequest, response);
    }

    protected final String newsResponse(WechatRequest wechatRequest, Article... responses) {
        List<Article> list = new ArrayList<>();
        Arrays.stream(responses).forEach(list::add);
        return newsResponse(wechatRequest, list);
    }

    protected final String newsResponse(WechatRequest wechatRequest, List<Article> responses) {
        WechatResponse response = new WechatResponse();
        response.setMsgType(MsgType.news.name());
        response.setArticles(responses);
        response.setArticleCount(response.getArticles().size());
        return getWechatResponse(wechatRequest, response);
    }

    protected final String imageResponse(WechatRequest wechatRequest, String mediaId) {
        WechatResponse wechatResponse = new WechatResponse();
        wechatResponse.setMsgType(MsgType.image.name());
        wechatResponse.setImage(new Image(mediaId));
        return getWechatResponse(wechatRequest, wechatResponse);
    }

    protected final String voiceResponse(WechatRequest wechatRequest, String mediaId) {
        WechatResponse wechatResponse = new WechatResponse();
        wechatResponse.setMsgType(MsgType.voice.name());
        wechatResponse.setVoice(new Voice(mediaId));
        return getWechatResponse(wechatRequest, wechatResponse);
    }

    protected final String videoResponse(WechatRequest wechatRequest, String mediaId, String thumbMediaId, String title, String des) {
        WechatResponse wechatResponse = new WechatResponse();
        wechatResponse.setMsgType(MsgType.video.name());
        Video video = new Video();
        video.setMediaId(mediaId);
        video.setThumbMediaId(thumbMediaId);
        video.setTitle(title);
        video.setDescription(des);
        wechatResponse.setVideo(video);
        return getWechatResponse(wechatRequest, wechatResponse);
    }

    protected final String musicResponse(WechatRequest wechatRequest, String title, String description,
                                         String musicURL, String hQMusicUrl, String thumbMediaId) {
        Music music = new Music();
        music.setTitle(title);
        music.setDescription(description);
        music.setMusicUrl(musicURL);
        music.setHQMusicUrl(hQMusicUrl);
        music.setThumbMediaId(thumbMediaId);
        return musicResponse(wechatRequest, music);
    }

    /**
     * @param wechatRequest
     * @param music
     * @return
     */
    protected final String musicResponse(WechatRequest wechatRequest, Music music) {
        WechatResponse wechatResponse = new WechatResponse();
        wechatResponse.setMsgType(MsgType.music.name());
        wechatResponse.setMusic(music);
        return getWechatResponse(wechatRequest, wechatResponse);
    }

    /**
     * 消息转发到指定客服
     *
     * @param kfAccount 客服
     */
    protected final String customerServiceResponse(WechatRequest wechatRequest, String kfAccount) {
        WechatResponse wechatResponse = new WechatResponse();
        wechatResponse.setMsgType(MsgType.transfer_customer_service.name());
        wechatResponse.setTransInfo(new TransInfo(kfAccount));
        return getWechatResponse(wechatRequest, wechatResponse);
    }


    /**
     * 文本消息处理Msgtype=text
     *
     * @throws Exception
     */
    protected abstract String onText(WechatRequest wechatRequest) throws Exception;

    /**
     * 图像消息Msgtype=image
     */
    protected abstract String onImage(WechatRequest wechatRequest) throws Exception;

    /**
     * 语音消息 Msgtype=voice
     */
    protected abstract String onVoice(WechatRequest wechatRequest) throws Exception;

    /**
     * 视频 消息Msgtype=video
     */
    protected abstract String onVideo(WechatRequest wechatRequest) throws Exception;

    /**
     * 小视频 消息Msgtype=shortvideo
     */
    protected abstract String onShortVideo(WechatRequest wechatRequest) throws Exception;

    /**
     * 地理位置消息Msgtype=location
     */
    protected abstract String onLocation(WechatRequest wechatRequest) throws Exception;

    /**
     * 链接消息Msgtype=link
     */
    protected abstract String onLink(WechatRequest wechatRequest) throws Exception;

    /**
     * 未知消息类型的错误处理逻辑，不需要处理则空方法即可
     */
    protected abstract String onUnknown(WechatRequest wechatRequest) throws Exception;

    /**
     * click点击事件处理event=location
     */
    protected abstract String click(WechatRequest wechatRequest) throws Exception;

    /**
     * subscribe关注事件处理
     */
    protected abstract String subscribe(WechatRequest wechatRequest) throws Exception;

    /**
     * unSubscribe取消关注事件处理
     */
    protected abstract String unSubscribe(WechatRequest wechatRequest) throws Exception;

    /**
     * scan事件处理
     */
    protected abstract String scan(WechatRequest wechatRequest) throws Exception;

    /**
     * location事件处理event=location
     */
    protected abstract String location(WechatRequest wechatRequest) throws Exception;

    /**
     * view 事件处理event=location
     */
    protected abstract String view(WechatRequest wechatRequest) throws Exception;

    /**
     * 模板消息发送回调
     */
    protected abstract String templateMsgCallback(WechatRequest wechatRequest) throws Exception;

    /**
     * 扫码推事件
     */
    protected abstract String scanCodePush(WechatRequest wechatRequest) throws Exception;

    /**
     * 扫码推事件且弹出“消息接收中”提示框的事件
     */
    protected abstract String scanCodeWaitMsg(WechatRequest wechatRequest) throws Exception;

    /**
     * 弹出系统拍照发图的事件
     */
    protected abstract String picSysPhoto(WechatRequest wechatRequest) throws Exception;

    /**
     * 弹出拍照或者相册发图的事件
     */
    protected abstract String picPhotoOrAlbum(WechatRequest wechatRequest) throws Exception;

    /**
     * 扫码推事件且弹出“消息接收中”提示框的事件
     */
    protected abstract String picWeixin(WechatRequest wechatRequest) throws Exception;

    /**
     * 弹出地理位置选择器的事件
     */
    protected abstract String locationSelect(WechatRequest wechatRequest) throws Exception;

    /**
     * 客服人员有接入会话
     */
    protected abstract String kfCreateSession(WechatRequest wechatRequest) throws Exception;

    /**
     * 客服人员有关闭会话
     */
    protected abstract String kfCloseSession(WechatRequest wechatRequest) throws Exception;

    /**
     * 客服人员有转接会话
     */
    protected abstract String kfSwitchSession(WechatRequest wechatRequest) throws Exception;
}
