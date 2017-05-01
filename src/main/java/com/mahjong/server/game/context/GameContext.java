package com.mahjong.server.game.context;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.MahjongTable;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.rule.GameStrategy;
/**
 * 一局游戏进行中的上下文信息。
 * @author warter
 */
public class GameContext {
	
	private static final Logger logger = Logger.getLogger(GameContext.class.getSimpleName());

	private MahjongTable table;
	
	private GameStrategy gameStrategy;
	
	private PlayerLocation zhuangLocation;
	
	/*记录用户每一步出牌信息*/
	private List<ActionAndLocation> localDoneActions = new ArrayList<ActionAndLocation>();
	
	public GameContext(MahjongTable table, GameStrategy gameStrategy) {
		this.table = table;
		this.gameStrategy = gameStrategy;
	}
	public GameContext(GameContext gameContext) {
		this.table = gameContext.getTable();
		this.gameStrategy = gameContext.getGameStrategy();
	}

	public MahjongTable getTable() {
		return table;
	}

	public GameStrategy getGameStrategy() {
		return gameStrategy;
	}



	public PlayerLocation getZhuangLocation() {
		return zhuangLocation;
	}

	public void setZhuangLocation(PlayerLocation zhuangLocation) {
		this.zhuangLocation = zhuangLocation;
	}

	public void actionDone(Action action, PlayerLocation location) {
		localDoneActions.add(new ActionAndLocation(action, location));
	}

	/**
	 * 返回到目前为止做出的最后一个动作和玩家位置。
	 */
	public ActionAndLocation getLastActionAndLocation() {
		return localDoneActions.isEmpty() ? null
				: localDoneActions.get(localDoneActions.size() - 1);
	}

	/**
	 * 返回到目前为止做出的最后一个动作。
	 */
	public Action getLastAction() {
		ActionAndLocation lastAction = getLastActionAndLocation();
		return lastAction == null ? null : lastAction.getAction();
	}

	/**
	 * 返回到目前为止做出的最后一个动作的玩家位置。
	 */
	public PlayerLocation getLastActionLocation() {
		ActionAndLocation lastAction = getLastActionAndLocation();
		return lastAction == null ? null : lastAction.getLocation();
	}

	public List<ActionAndLocation> getDoneActions() {
		return localDoneActions;
	}

	protected void setDoneActions(List<ActionAndLocation> doneActions) {
		this.localDoneActions = doneActions;
	}
	
	public boolean joinRoom( UserInfo userInfo) {
		
		PlayerInfo nowPlayer = new PlayerInfo();
		nowPlayer.setUserInfo(userInfo);
		nowPlayer.setCurScore(1000);

		if (this.table.getPlayerByLocation(PlayerLocation.NORTH) == null) {
			getTable().setPlayer(PlayerLocation.NORTH, nowPlayer);
			nowPlayer.setUserLocation(PlayerLocation.NORTH.getCode());
		} else if (this.table.getPlayerByLocation(PlayerLocation.WEST) == null) {
			getTable().setPlayer(PlayerLocation.WEST, nowPlayer);
			nowPlayer.setUserLocation(PlayerLocation.WEST.getCode());
		} else if (this.table.getPlayerByLocation(PlayerLocation.SOUTH) == null) {
			getTable().setPlayer(PlayerLocation.SOUTH, nowPlayer);
			nowPlayer.setUserLocation(PlayerLocation.SOUTH.getCode());
		} else {
			return false;
		}
		
		if(this.table.getPlayerInfos().size()==4){
			//TODO init 
		}
		return true;
	}


}
