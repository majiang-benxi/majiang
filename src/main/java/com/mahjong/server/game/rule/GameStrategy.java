package com.mahjong.server.game.rule;

import java.util.Map;
import java.util.Set;

import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.PlayerLocation;
import com.mahjong.server.game.object.Tile;

/**
 * 游戏策略。即一种游戏规则的定义。
 * 
 */
public abstract class GameStrategy {

	/**
	 * 获取全部麻将牌的列表。
	 */
	public byte[] getAllTiles() {
		return Tile.getOneBoxMahjong();
	}
	/**
	 * 获取全部麻将牌的列表。
	 */
	public abstract byte[] checkUserIsWin();

	/**
	 * 返回游戏进行中（发牌后，直到结束）所有动作类型列表。
	 */
	public abstract Set<? extends ActionType> getAllActionTypesInGame();

	/**
	 * 根据当前状态返回下一步动作
	 * 
	 * @return 默认动作
	 */
	public abstract ActionAndLocation getActionUserCanChoises( Map<PlayerLocation, PlayerInfo> playersInfos);
	
	/**
	 * 在一局开始之前，根据context返回此局的庄家位置。
	 */
	protected abstract PlayerLocation nextZhuangLocation(GameContext context);

}
