package com.mahjong.server.game.object;

import java.util.List;

public class DiscardContext {
	private List<DisCardActionAndLocation> needPassOrDoAction;// 存储一次打牌操作之后，其他所有玩家针对这次打牌的一个能够响应的动作情况，每次询问返回后需要删除。一旦有玩家执行了非过的操作，就清空此对象。
	private int remainVoter = 0;// 剩余的投票者

	public DiscardContext(List<DisCardActionAndLocation> needPassOrDoAction, int remainVoter) {
		super();
		this.needPassOrDoAction = needPassOrDoAction;
		this.remainVoter = remainVoter;
	}

	public List<DisCardActionAndLocation> getNeedPassOrDoAction() {
		return needPassOrDoAction;
	}

	public void setNeedPassOrDoAction(List<DisCardActionAndLocation> actionAndLocations) {
		this.needPassOrDoAction = actionAndLocations;
	}

	public int getRemainVoter() {
		return remainVoter;
	}

	public void setRemainVoter(int remainVoter) {
		this.remainVoter = remainVoter;
	}

	public void releaseRemainVoter() {
		this.remainVoter--;
	}

	public void clear() {
		needPassOrDoAction = null;
		remainVoter = 0;

	}

}
