package com.moonshy.wechatframework.api.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moonshy.wechatframework.api.user.model.*;
import com.moonshy.wechatframework.core.exception.WeChatException;
import com.moonshy.wechatframework.core.util.WeChatUtil;
import com.moonshy.wechatframework.core.util.http.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 用户管理
 *
 * @author moonshy
 * @version 2015-7-5
 */
public class WxUserAPI {

    static Logger logger = LoggerFactory.getLogger(WxUserAPI.class);
    private static final int FOLLWERS_USER_MAX_COUNT = 10000;
    // 获取用户列表
    private static final String USRE_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";
    // 设置用户备注名
    private static final String USER_UPDATE_REMARK_POST_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=";
    // 获取用户基本信息
    private static final String USER_INFO_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
    // 创建分组
    private static final String GROUP_CREATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=";
    // 查询所有分组
    private static final String GROUP_GET_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=";
    // 查询用户所在分组
    private static final String GROUP_GETID_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=";
    // 修改分组名
    private static final String GROUP_UPDATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=";
    // 移动用户分组
    private static final String GROUP_MEMBERS_UPDATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=";
    // 批量移动用户分组
    private static final String GROUP_MEMBERS_DATCHUPDATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=";
    // 删除分组
    private static final String GROUP_DELETE_POST_URL = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=";
    //批量获取用户基本信息，一次最多拉取100条
    private static final String USER_INFO_BATCH_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=";

    /**
     * 获取所有的关注者列表
     *
     * @return
     */
    public static List<String> allSubscriber(String access_token) {
        Follwers follwers = subscriberList(access_token);
        int count = follwers.getCount();
        int total = follwers.getTotal();
        String nextOpenId = follwers.getNextOpenid();
        while (count == FOLLWERS_USER_MAX_COUNT && count < total) {
            Follwers f = subscriberList(nextOpenId, access_token);
            if (f != null) {
                nextOpenId = f.getNextOpenid();
                count = f.getCount();
                total = f.getTotal();
                if (f.getData() != null) {
                    follwers.getData().getOpenid().addAll(f.getData().getOpenid());
                }
            }
        }
        return follwers.getData().getOpenid();
    }

    /**
     * 获取帐号的关注者列表前10000人
     *
     * @return
     */
    public static Follwers subscriberList(String access_token) {
        return subscriberList(null, access_token);
    }

    /**
     * 获取帐号的关注者列表
     *
     * @param nextOpenId
     * @return
     */
    public static Follwers subscriberList(String nextOpenId, String access_token) {
        String url = USRE_GET_URL + access_token;
        if (StringUtils.isNotBlank(nextOpenId)) {
            url += "&next_openid=" + nextOpenId;
        }
        String resultStr = HttpUtils.get(url);
        logger.info("return data " + resultStr);
        try {
            WeChatUtil.isSuccess(resultStr);
        } catch (WeChatException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return JSONObject.parseObject(resultStr, Follwers.class);
    }

    /**
     * 设置用户备注名
     *
     * @param openId 用户openid
     * @param remark 新的备注名，长度必须小于30字符
     * @return
     * @throws WeChatException
     */
    public static void updateRemark(String openId, String remark, String access_token) throws WeChatException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid", openId);
        jsonObject.put("remark", remark);
        String requestData = jsonObject.toString();
        logger.info("request data " + requestData);
        String resultStr = HttpUtils.post(USER_UPDATE_REMARK_POST_URL + access_token, requestData);
        logger.info("return data " + resultStr);
        WeChatUtil.isSuccess(resultStr);
    }

    /**
     * 获取用户基本信息
     *
     * @param openId 普通用户的标识，对当前公众号唯一
     * @return
     */
    public static UserInfo getUserInfo(String openId, String access_token) {
        return getUserInfo(openId, null, access_token);
    }

    /**
     * 获取用户基本信息
     *
     * @param openId 普通用户的标识，对当前公众号唯一
     * @param lang   返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return
     */
    public static UserInfo getUserInfo(String openId, LanguageType lang, String access_token) {
        String url = USER_INFO_GET_URL + access_token + "&openid=" + openId;
        if (lang != null) {
            url += "&lang=" + lang.name();
        }
        String resultStr = HttpUtils.get(url);
        logger.info("return data " + resultStr);
        try {
            WeChatUtil.isSuccess(resultStr);
        } catch (WeChatException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
        UserInfo user = JSONObject.parseObject(resultStr, UserInfo.class);
        return user;
    }

    /**
     * 创建分组
     *
     * @param name 分组名字（30个字符以内）
     * @return
     * @throws WeChatException
     */
    public static Group createGroup(String name, String access_token) throws WeChatException {
        JSONObject nameJson = new JSONObject();
        JSONObject groupJson = new JSONObject();
        nameJson.put("name", name);
        groupJson.put("group", nameJson);
        String requestData = groupJson.toString();
        logger.info("request data " + requestData);
        String resultStr = HttpUtils.post(GROUP_CREATE_POST_URL + access_token, requestData);
        logger.info("return data " + resultStr);
        WeChatUtil.isSuccess(resultStr);
        return JSONObject.parseObject(resultStr).getObject("group", Group.class);
    }

    /**
     * 查询所有分组
     *
     * @return
     */
    public static List<Group> getGroup(String access_token) {
        String resultStr = HttpUtils.post(GROUP_GET_POST_URL + access_token);
        logger.info("return data " + resultStr);
        try {
            WeChatUtil.isSuccess(resultStr);
        } catch (WeChatException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        List<Group> groups = JSON.parseArray(jsonObject.getString("groups"), Group.class);
        return groups;
    }

    /**
     * 查询用户所在分组
     *
     * @param openId 用户的OpenID
     * @return 用户所属的groupid
     */
    public static Integer getIdGroup(String openId, String access_token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid", openId);

        String requestData = jsonObject.toString();
        logger.info("request data " + requestData);
        String resultStr = HttpUtils.post(GROUP_GETID_POST_URL + access_token, requestData);
        logger.info("return data " + resultStr);
        try {
            WeChatUtil.isSuccess(resultStr);
        } catch (WeChatException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        int groupId = resultJson.getIntValue("groupid");
        return groupId;
    }

    /**
     * 修改分组名
     *
     * @param groupId 分组id
     * @param name    分组名称
     * @throws WeChatException
     */
    public static void updateGroup(int groupId, String name, String access_token) throws WeChatException {
        JSONObject nameJson = new JSONObject();
        JSONObject groupJson = new JSONObject();
        nameJson.put("id", groupId);
        nameJson.put("name", name);
        groupJson.put("group", nameJson);
        String requestData = groupJson.toString();
        logger.info("request data " + requestData);
        String resultStr = HttpUtils.post(GROUP_UPDATE_POST_URL + access_token, requestData);
        logger.info("return data " + resultStr);
        WeChatUtil.isSuccess(resultStr);
    }

    /**
     * 移动用户分组
     *
     * @param groupId      用户的OpenID
     * @param groupId      分组id
     * @param access_token
     * @throws WeChatException
     */
    public static void membersUpdateGroup(String openId, int groupId, String access_token) throws WeChatException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid", openId);
        jsonObject.put("to_groupid", groupId);
        String requestData = jsonObject.toString();
        logger.info("request data " + requestData);
        String resultStr = HttpUtils.post(GROUP_MEMBERS_UPDATE_POST_URL + access_token, requestData);
        logger.info("return data " + resultStr);
        WeChatUtil.isSuccess(resultStr);
    }

    /**
     * 批量移动用户分组
     *
     * @param openIds 用户唯一标识符openid的列表（size不能超过50）
     * @param groupId 分组id
     * @return 是否修改成功
     * @throws WeChatException
     */
    public static void membersDatchUpdateGroup(String[] openIds, int groupId, String access_token) throws WeChatException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid_list", openIds);
        jsonObject.put("to_groupid", groupId);
        String requestData = jsonObject.toString();
        logger.info("request data " + requestData);
        String resultStr = HttpUtils.post(GROUP_MEMBERS_DATCHUPDATE_POST_URL + access_token, requestData);
        logger.info("return data " + resultStr);
        WeChatUtil.isSuccess(resultStr);
    }

    /**
     * 删除分组
     *
     * @param groupId
     * @throws WeChatException
     */
    public static void deleteGroup(int groupId, String access_token) throws WeChatException {
        JSONObject idJson = new JSONObject();
        idJson.put("id", groupId);
        JSONObject groupJson = new JSONObject();
        groupJson.put("group", idJson);
        String requestData = groupJson.toJSONString();
        logger.info("request data " + requestData);
        String resultStr = HttpUtils.post(GROUP_DELETE_POST_URL + access_token, requestData);
        logger.info("return data " + resultStr);
        WeChatUtil.isSuccess(resultStr);
    }


    /**
     * 批量获取用户信息。一次最多拉取一百条
     *
     * @param opendids OpenId列表
     * @param accesstoken 访问令牌
     * @throws WeChatException 如果超过100个openid,抛出异常.
     * @return
     */
    public static UserData batchGetUserInfo(List<String> opendids, String accesstoken) throws WeChatException {
        if(opendids == null || opendids.isEmpty()){
            return null;
        }
        if(opendids.size()>100){
            logger.info("openid 列表长度超过100");
            throw new WeChatException("openid 列表长度超过100");
        }
        JSONObject postData = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject userdata;
        for (String opendid : opendids) {
            userdata = new JSONObject(true);
            userdata.put("openid", opendid);
            userdata.put("lang", "zh-CN");
            jsonArray.add(userdata);
        }
        postData.put("user_list", jsonArray);
        String requestData = postData.toJSONString();
        logger.info("request data" + requestData);
        String result = HttpUtils.post(USER_INFO_BATCH_GET_URL + accesstoken, requestData);

        try {
            WeChatUtil.isSuccess(result);
        } catch (WeChatException e) {
            logger.error(e.getMessage());
            return null;
        }
        return JSON.parseObject(result, UserData.class);
    }
}
