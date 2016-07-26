package com.moonshy.wechatframework.api.user.model;

import com.moonshy.wechatframework.api.user.HeadImgSize;
import com.moonshy.wechatframework.core.common.BaseData;

import java.util.List;

/**
 * @Description 微信用户信息
 * @Author moonshy
 * @CreationDate 2016年5月12日 上午9:43:28
 */
public class UserInfo extends BaseData {

    private static final long serialVersionUID = 1897367234511577769L;
    private String nickname;// 用户昵称
    private String sex;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String province;// 用户个人资料填写的省份
    private String city;// 普通用户个人资料填写的城市
    private String country;// 国家，如中国为CN
    private String headimgurl;// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    private List<String> privilege;// 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    private String unionid;// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private int subscribe;// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
    private String language = "zh_CN";// 用户的语言，简体中文为zh_CN,zh_TW 繁体，en 英语
    private String lang = "zh_CN";// 用户的语言，简体中文为zh_CN,zh_TW 繁体，en 英语
    private long subscribe_time;// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    private String remark;// 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
    private int groupid;// 用户所在的分组ID（兼容旧的用户分组接口）
    private List<Integer> tagid_list;// 用户被打上的标签ID列表

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(long subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public List<Integer> getTagid_list() {
        return tagid_list;
    }

    public void setTagid_list(List<Integer> tagid_list) {
        this.tagid_list = tagid_list;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserInfo{");
        sb.append("nickname='").append(nickname).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", headimgurl='").append(headimgurl).append('\'');
        sb.append(", privilege=").append(privilege);
        sb.append(", unionid='").append(unionid).append('\'');
        sb.append(", subscribe=").append(subscribe);
        sb.append(", language='").append(language).append('\'');
        sb.append(", lang='").append(lang).append('\'');
        sb.append(", subscribe_time=").append(subscribe_time);
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", groupid=").append(groupid);
        sb.append(", tagid_list=").append(tagid_list);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 根据参数获取不同尺寸的头像
     * @param headImgSize
     * @return
     */
    public String getMultiSizeHeadImg(HeadImgSize headImgSize) {
        if (this.headimgurl == null || this.headimgurl.equals("")) {
            return null;
        }
        if (headImgSize == null || headImgSize == HeadImgSize.ZERO) {
            return this.headimgurl;
        }
        return this.headimgurl.substring(0, this.headimgurl.lastIndexOf("/") + 1) + headImgSize;
    }

}
