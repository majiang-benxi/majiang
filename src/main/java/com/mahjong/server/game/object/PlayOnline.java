
package com.mahjong.server.game.object;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.action.Action;

public class PlayOnline implements Player{
	
	private UserInfo userInfo ;
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public void chooseAction(Action userAction)
			throws InterruptedException {
		return ;
	}


}
