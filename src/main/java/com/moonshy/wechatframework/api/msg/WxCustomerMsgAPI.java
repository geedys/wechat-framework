/**
 * http请求方式: POST
 * https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
 */
package com.moonshy.wechatframework.api.msg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moonshy.wechatframework.core.common.MsgType;
import com.moonshy.wechatframework.core.exception.WeChatException;
import com.moonshy.wechatframework.core.msg.Article;
import com.moonshy.wechatframework.core.msg.Music;
import com.moonshy.wechatframework.core.msg.Video;
import com.moonshy.wechatframework.core.util.WeChatUtil;
import com.moonshy.wechatframework.core.util.http.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送客服消息
 * 
 * @author moonshy
 * @date 2014年12月11日
 */
public class WxCustomerMsgAPI {
	private static Logger logger = LoggerFactory.getLogger(WxCustomerMsgAPI.class);

	private static final String MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

	/**
	 * 发送客服消息
	 * 
	 * @param msgBody
	 */
	private static void send(String accsee_token, String toUserOpenId, String msgBody) {
		if (StringUtils.isBlank(toUserOpenId))
			return;
		// token不存在则重新刷新token
		if (StringUtils.isBlank(accsee_token)) {
			logger.error("发送失败，无法得到accessToken");
			return;
		}
		// 需要判断一下，防止上面刷新token失败
		if (StringUtils.isNotBlank(accsee_token)) {
			String url = MSG_URL + accsee_token;
			String post = HttpUtils.post(url, msgBody);
			try {
				WeChatUtil.isSuccess(post);
			} catch (WeChatException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * { "touser":"OPENID", "msgtype":"text", "text": { "content":"Hello World"
	 * } }
	 * 
	 * @param content
	 */
	public static void sendText(String content, String accsee_token, String toUserOpenId) {
		JSONObject jsonMsg = new JSONObject();
		jsonMsg.put("content", content);
		JSONObject json = new JSONObject();
		json.put("touser", toUserOpenId);
		json.put("msgtype", MsgType.text.name());
		json.put("text", jsonMsg);
		send(accsee_token, toUserOpenId, json.toJSONString());
	}

	/**
	 * 发送图片消息 { "touser":"OPENID", "msgtype":"image", "image": {
	 * "media_id":"MEDIA_ID" } }
	 * 
	 * @param mediaId
	 */
	public static void sendImage(String mediaId, String accsee_token, String toUserOpenId) {
		JSONObject jsonMsg = new JSONObject();
		jsonMsg.put("media_id", mediaId);
		JSONObject json = new JSONObject();
		json.put("touser", toUserOpenId);
		json.put("msgtype", MsgType.image.name());
		json.put("image", jsonMsg);
		send(accsee_token, toUserOpenId, json.toJSONString());
	}

	/**
	 * 发送语音消息
	 * 
	 * { "touser":"OPENID", "msgtype":"voice", "voice": { "media_id":"MEDIA_ID"
	 * } }
	 */
	public static void sendVoice(String mediaId, String accsee_token, String toUserOpenId) {
		JSONObject jsonMsg = new JSONObject();
		jsonMsg.put("media_id", mediaId);
		JSONObject json = new JSONObject();
		json.put("touser", toUserOpenId);
		json.put("msgtype", MsgType.voice.name());
		json.put("voice", jsonMsg);
		send(accsee_token, toUserOpenId, json.toJSONString());
	}

	/**
	 * 发送视频消息
	 * 
	 * 
	 * @param title
	 * @param description
	 * @param mediaId
	 * @param thumbMediaId
	 */
	public static void sendVideo(String title, String description, String mediaId, String thumbMediaId, String accsee_token, String toUserOpenId) {
		Video video = new Video(mediaId, title, description, thumbMediaId);
		sendVideo(video, accsee_token, toUserOpenId);
	}

	/**
	 * 发送视频消息 { "touser":"OPENID", "msgtype":"video", "video": {
	 * "media_id":"MEDIA_ID", "thumb_media_id":"MEDIA_ID", "title":"TITLE",
	 * "description":"DESCRIPTION" } }
	 * 
	 * @param video
	 */
	public static void sendVideo(Video video, String accsee_token, String toUserOpenId) {
		JSONObject jsonMsg = new JSONObject();
		jsonMsg.put("media_id", video.getMediaId());
		jsonMsg.put("thumb_media_id", video.getThumbMediaId());
		jsonMsg.put("title", video.getTitle());
		jsonMsg.put("description", video.getDescription());

		JSONObject json = new JSONObject();
		json.put("touser", toUserOpenId);
		json.put("msgtype", MsgType.video.name());
		json.put("video", jsonMsg);
		send(accsee_token, toUserOpenId, json.toJSONString());
	}

	/**
	 * 发送音乐消息
	 * 
	 * @param title
	 * @param description
	 * @param musicURL
	 * @param hQMusicUrl
	 * @param thumbMediaId
	 */
	public static void sendMusic(String title, String description, String musicURL, String hQMusicUrl, String thumbMediaId, String accsee_token, String toUserOpenId) {
		Music music = new Music(title, description, musicURL, hQMusicUrl, thumbMediaId);
		sendMusic(music, accsee_token, toUserOpenId);
	}

	/**
	 * 发送音乐消息 { "touser":"OPENID", "msgtype":"music", "music": {
	 * "title":"MUSIC_TITLE", "description":"MUSIC_DESCRIPTION",
	 * "musicurl":"MUSIC_URL", "hqmusicurl":"HQ_MUSIC_URL",
	 * "thumb_media_id":"THUMB_MEDIA_ID" } }
	 * 
	 * @param music
	 *            音乐消息
	 */
	public static void sendMusic(Music music, String accsee_token, String toUserOpenId) {
		JSONObject jsonMsg = new JSONObject();
		jsonMsg.put("title", music.getTitle());
		jsonMsg.put("description", music.getDescription());
		jsonMsg.put("musicurl", music.getMusicUrl());
		jsonMsg.put("hqmusicurl", music.getHQMusicUrl());
		jsonMsg.put("thumb_media_id", music.getThumbMediaId());

		JSONObject json = new JSONObject();
		json.put("touser", toUserOpenId);
		json.put("msgtype", MsgType.music.name());
		json.put("music", jsonMsg);
		send(accsee_token, toUserOpenId, json.toJSONString());
	}

	/**
	 * 发送图文消息，单条图文消息
	 * 
	 * @param title
	 *            图文消息标题
	 * @param description
	 *            图文消息描述
	 * @param picUrl
	 *            图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 * @param url
	 *            点击图文消息跳转链接
	 */
	public static void sendNew(String title, String description, String picUrl, String url, String accsee_token, String toUserOpenId) {
		Article item = new Article();
		item.setTitle(title);
		item.setDescription(description);
		item.setPicUrl(picUrl);
		item.setUrl(url);
		sendNews(item, accsee_token, toUserOpenId);
	}

	/**
	 * 发送图文消息，单条图文消息
	 * 
	 * @param item
	 */
	public static void sendNews(Article item, String accsee_token, String toUserOpenId) {
		List<Article> items = new ArrayList<Article>();
		items.add(item);
		sendNews(items, accsee_token, toUserOpenId);
	}

	/**
	 * 发送图文消息 { "touser":"OPENID", "msgtype":"news", "news":{ "articles": [ {
	 * "title":"Happy Day", "description":"Is Really A Happy Day", "url":"URL",
	 * "picurl":"PIC_URL" }, { "title":"Happy Day", "description":
	 * "Is Really A Happy Day", "url":"URL", "picurl":"PIC_URL" } ] } }
	 * 
	 * @param items
	 */
	public static void sendNews(List<Article> items, String accsee_token, String toUserOpenId) {
		JSONArray jsonArray = new JSONArray();
		for (Article item : items) {
			JSONObject jsonItem = new JSONObject();
			jsonItem.put("title", item.getTitle());
			jsonItem.put("description", item.getDescription());
			jsonItem.put("url", item.getUrl());
			jsonItem.put("picurl", item.getPicUrl());

			jsonArray.add(jsonItem);
		}

		JSONObject jsonMsg = new JSONObject();
		jsonMsg.put("articles", jsonArray);

		JSONObject json = new JSONObject();
		json.put("touser", toUserOpenId);
		json.put("msgtype", MsgType.news.name());
		json.put("news", jsonMsg);

		send(accsee_token, toUserOpenId, json.toJSONString());
	}
}
