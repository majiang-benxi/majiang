package com.mahjong.server.game.context;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.alibaba.fastjson.annotation.JSONField;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.DiscardContext;
import com.mahjong.server.game.object.GameResult;
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
	private GameResult gameResult;
	private DiscardContext discardContext;
	@JSONField(serialize=false)
	private final int huangZhuangtTileNum=14;
	
	private boolean isHuangzhuang = false;
	
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
		if (gameResult != null) {
			zhuangLocation = gameResult.getWinnerLocation();
		}
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
	
	public PlayerInfo joinRoom( UserInfo userInfo) {
		
		PlayerInfo nowPlayer = new PlayerInfo();
		nowPlayer.setUserInfo(userInfo);
		nowPlayer.setCurScore(1000);

		if (this.table.getPlayerByLocation(PlayerLocation.NORTH).getUserInfo() == null) {
			getTable().setPlayer(PlayerLocation.NORTH, nowPlayer);
			nowPlayer.setUserLocation(PlayerLocation.NORTH.getCode());
		} else if (this.table.getPlayerByLocation(PlayerLocation.WEST).getUserInfo() == null) {
			getTable().setPlayer(PlayerLocation.WEST, nowPlayer);
			nowPlayer.setUserLocation(PlayerLocation.WEST.getCode());
		} else if (this.table.getPlayerByLocation(PlayerLocation.SOUTH).getUserInfo() == null) {
			getTable().setPlayer(PlayerLocation.SOUTH, nowPlayer);
			nowPlayer.setUserLocation(PlayerLocation.SOUTH.getCode());
		} else {
			return null;
		}
		
		return nowPlayer;
	}

	public List<ActionAndLocation> getLocalDoneActions() {
		return localDoneActions;
	}

	public void setLocalDoneActions(List<ActionAndLocation> localDoneActions) {
		this.localDoneActions = localDoneActions;
	}

	public GameResult getGameResult() {
		return gameResult;
	}

	public void setGameResult(GameResult gameResult) {
		this.gameResult = gameResult;
	}

	public void setTable(MahjongTable table) {
		this.table = table;
	}

	public void setGameStrategy(GameStrategy gameStrategy) {
		this.gameStrategy = gameStrategy;
	}

	public DiscardContext getDiscardContext() {
		return discardContext;
	}

	public int getHuangZhuangtTileNum() {
		return huangZhuangtTileNum;
	}
	public void setDiscardContext(DiscardContext discardContext) {
		this.discardContext = discardContext;
	}
	public boolean isHuangzhuang() {
		return isHuangzhuang;
	}
	public void setHuangzhuang(boolean isHuangzhuang) {
		this.isHuangzhuang = isHuangzhuang;
	}

}
