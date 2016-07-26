package com.moonshy.wechatframework.api.menu;

import com.moonshy.wechatframework.core.common.BaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信菜单
 * 
 * @author moonshy
 * @version 2015-7-4
 */
public class Menu extends BaseData {
	private List<MenuButton> button = new ArrayList<>();

	public List<MenuButton> getButton() {
		return button;
	}

	public void addButton(int index, MenuButton button) {
		this.button.add(index, button);
	}

	public void addButton(MenuButton button) {
		this.button.add(button);
	}

	public void addButton(List<MenuButton> buttons) {
		this.button.addAll(buttons);
	}

	public void removeButton(int index) {
		this.button.remove(index);
	}

	public void removeButton(MenuButton button){
		this.button.remove(button);
	}

}
