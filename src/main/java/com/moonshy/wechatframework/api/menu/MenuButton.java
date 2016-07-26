package com.moonshy.wechatframework.api.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.moonshy.wechatframework.api.oauth.WxOAuth2API;
import com.moonshy.wechatframework.core.common.BaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单按钮
 *
 * @author moonshy
 * @version 2015-7-4
 */
public class MenuButton extends BaseData {
    /**
     *
     */
    private static final long serialVersionUID = -4295478170036989007L;
    private MenuButtonType type;// 菜单的响应动作类型
    private String name;// 菜单标题，不超过16个字节，子菜单不超过40个字节
    private String key;// click等点击类型必须 菜单KEY值，用于消息接口推送，不超过128字节
    private String url;// view类型必须 网页链接，用户点击菜单可打开链接，不超过256字节
    private String mediaId;// media_id类型和view_limited类型必须 调用新增永久素材接口返回的合法media_id
    private List<MenuButton> subButton = new ArrayList<>();// 子菜单,每个一级菜单最多包含5个二级菜单

    public MenuButtonType getType() {
        return type;
    }

    public void setType(MenuButtonType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JSONField(name = "media_id")
    public String getMediaId() {
        return mediaId;
    }

    @JSONField(name = "media_id")
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @JSONField(name = "sub_button")
    public List<MenuButton> getSubButton() {
        return subButton;
    }

    @JSONField(name = "sub_button")
    public void setSubButton(List<MenuButton> subButton) {
        this.subButton = subButton;
    }

    public void addButton(int index, MenuButton button) {
        this.subButton.add(index, button);
    }

    public void removeButton(int index) {
        this.subButton.remove(index);
    }

    public void removeButton(MenuButton button) {
        this.subButton.remove(button);
    }

    public void addButton(MenuButton button) {
        this.subButton.add(button);
    }

    public void addButton(List<MenuButton> buttons) {
        this.subButton.addAll(buttons);
    }

    @Override
    public String toString() {
        return "MenuButton{" +
                "type=" + type.name() +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", url='" + url + '\'' +
                ", mediaId='" + mediaId + '\'' +
                '}';
    }

    /**
     * 转化当前URL为oauth2授权认证地址，当type为view的时候
     */
    public void toOAuthUrl(String appid, String webPath) {
        if (this.getType() == MenuButtonType.view && this.getUrl() != null) {
            if (!this.getUrl().matches(WxOAuth2API.OAUTH_DIRECTURL_REGEX)) {
                if (this.url.startsWith(webPath)) {
                    this.url = WxOAuth2API.generateRedirectURI(this.url, appid);
                } else if (this.url.startsWith("/")) {
                    this.url = WxOAuth2API.generateRedirectURI(webPath + this.url, appid);
                } else {
                    this.url = WxOAuth2API.generateRedirectURI(webPath + "/" + this.url, appid);
                }
            }
        }
    }
}
