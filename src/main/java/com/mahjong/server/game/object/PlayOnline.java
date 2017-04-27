package com.mahjong.server.game.object;

import java.util.Set;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.context.GameContext.PlayerView;

public class PlayOnline implements Player {

	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action chooseAction(PlayerView contextView, Set<ActionType> actionTypes, Action illegalAction)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actionDone(PlayerView contextView, PlayerLocation actionLocation, Action action) {
		// TODO Auto-generated method stub
		
	}

	

}
