package com.mahjong.server.game.context;


import static com.mahjong.server.game.action.standard.StandardActionType.DRAW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.object.GameResult;
import com.mahjong.server.game.object.MahjongTable;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.PlayerLocation;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.rule.GameStrategy;
/**
 * 一局游戏进行中的上下文信息。
 * 
 * @author warter
 */
public class GameContext {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(GameContext.class.getSimpleName());

	private MahjongTable table;
	private GameStrategy gameStrategy;
	
	private PlayerLocation zhuangLocation;
	private List<ActionAndLocation> doneActions = new ArrayList<ActionAndLocation>();
	private GameResult gameResult;
	private final Map<PlayerLocation, PlayerView> playerViews = new HashMap<PlayerLocation, PlayerView>();

	public GameContext(MahjongTable table, GameStrategy gameStrategy) {
		this.table = table;
		this.gameStrategy = gameStrategy;
	}

	public MahjongTable getTable() {
		return table;
	}

	public GameStrategy getGameStrategy() {
		return gameStrategy;
	}


	public PlayerInfo getPlayerInfoByLocation(PlayerLocation location) {
		return table.getPlayerInfos().get(location);
	}

	public PlayerLocation getZhuangLocation() {
		return zhuangLocation;
	}

	public void setZhuangLocation(PlayerLocation zhuangLocation) {
		this.zhuangLocation = zhuangLocation;
	}

	public void actionDone(Action action, PlayerLocation location) {
		doneActions.add(new ActionAndLocation(action, location));
	}

	/**
	 * 返回到目前为止做出的最后一个动作和玩家位置。
	 */
	public ActionAndLocation getLastActionAndLocation() {
		return doneActions.isEmpty() ? null
				: doneActions.get(doneActions.size() - 1);
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
		return doneActions;
	}

	protected void setDoneActions(List<ActionAndLocation> doneActions) {
		this.doneActions = doneActions;
	}

	public GameResult getGameResult() {
		return gameResult;
	}

	public void setGameResult(GameResult gameResult) {
		this.gameResult = gameResult;
	}


	/**
	 * 获取指定位置的玩家视图。
	 */
	public PlayerView getPlayerView(PlayerLocation location) {
		PlayerView view = playerViews.get(location);
		if (view == null) { // 不需要加锁，因为多创建了也没事
			view = newPlayerView(location);
			playerViews.put(location, view);
		}
		return view;
	}

	protected PlayerView newPlayerView(PlayerLocation location) {
		return new PlayerView(location);
	}

	/**
	 * 一个位置的玩家的视图。需要限制一些权限。
	 * 
	 * @author warter
	 */
	public class PlayerView {

		private final PlayerLocation myLocation;

		public PlayerView(PlayerLocation myLocation) {
			this.myLocation = myLocation;
		}

		public MahjongTable.PlayerView getTableView() {
			return table.getPlayerView(myLocation);
		}

		public GameStrategy getGameStrategy() {
			return gameStrategy;
		}


		public PlayerLocation getMyLocation() {
			return myLocation;
		}

		public PlayerInfo getMyInfo() {
			return getPlayerInfoByLocation(myLocation);
		}

		public PlayerLocation getZhuangLocation() {
			return GameContext.this.getZhuangLocation();
		}

		/**
		 * 返回到目前为止做出的最后一个动作和玩家位置。
		 */
		public ActionAndLocation getLastActionAndLocation() {
			return GameContext.this.getLastActionAndLocation();
		}

		/**
		 * 返回到目前为止做出的最后一个动作。
		 */
		public Action getLastAction() {
			return GameContext.this.getLastAction();
		}

		/**
		 * 返回到目前为止做出的最后一个动作的玩家位置。
		 */
		public PlayerLocation getLastActionLocation() {
			return GameContext.this.getLastActionLocation();
		}

		/**
		 * 如果刚刚摸牌，则返回刚摸的牌，否则返回null。
		 */
		public Tile getJustDrawedTile() {
			ActionAndLocation laa = getLastActionAndLocation();
			if (laa.getLocation() != myLocation) {
				return null;
			}
			if (!DRAW.matchBy(laa.getAction().getType())) {
				return null;
			}
			return getMyInfo().getLastDrawedTile();
		}

		public List<ActionAndLocation> getDoneActions() {
			return GameContext.this.getDoneActions();
		}

		public GameResult getGameResult() {
			return GameContext.this.getGameResult();
		}

	}

}
