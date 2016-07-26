package com.moonshy.wechatframework.api.user;

/**
 * 头像尺寸，目前官方支持如下几个尺寸：0（640 * 640）,46,64,96,132
 * Created by moonshy on 2016/7/15.
 */
public enum HeadImgSize {
    ZERO(0), FOUTY_SIX(46), SIXTY_FOUR(64), NINETY_SIX(96), ONE_H_THIRTY_TWO(132);

    private int size;

    public String toString() {
        return String.valueOf(this.size);
    }

    HeadImgSize(int size) {
        this.size = size;
    }
}
