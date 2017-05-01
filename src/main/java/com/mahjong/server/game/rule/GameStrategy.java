package com.mahjong.server.game.rule;

import java.util.Map;
import java.util.Set;

import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.MahjongTable;
import com.mahjong.server.game.object.Tile;

/**
 * 游戏策略。即一种游戏规则的定义。
 * @author warter
 */
public class GameStrategy {
	
	private RuleInfo ruleInfo;

	public RuleInfo getRuleInfo() {
		return ruleInfo;
	}

	public void setRuleInfo(RuleInfo ruleInfo) {
		this.ruleInfo = ruleInfo;
	}

	/**
	 * 检查一个麻将桌是否符合条件开始进行一局。
	 * 
	 * @param table
	 *            麻将桌
	 * @return 如果可以开始，返回true，否则返回false。
	 */
	public boolean checkReady(MahjongTable table) {
		return false;
	}

	/**
	 * 获取全部麻将牌的列表。
	 */
	public Tile getAllTiles() {
		return null;
	}

	/**
	 * 在一局开始之前对上下文进行必要操作。
	 */
	public void readyContext(GameContext context) {
	}

	/**
	 * 根据当前状态获取开局的发牌动作，用于发牌。
	 */
	public Action getDealAction(GameContext context) {
		return null;
	}

	/**
	 * 返回游戏进行中（发牌后，直到结束）所有动作类型列表。
	 */
	public Set<? extends ActionType> getAllActionTypesInGame() {
		return null;
	}

	/**
	 * 根据当前状态返回默认动作。默认动作是所有玩家都没有可选动作或均选择不做动作之后自动执行的动作。
	 * 
	 * @return 默认动作
	 */
	public ActionAndLocation getDefaultAction(GameContext context,
			Map<PlayerLocation, Set<ActionType>> choises) {
		return null;
	}


	/**
	 * 判断指定条件下是否可和牌。<br>
	 * 默认实现为使用此策略支持的所有和牌类型进行判断，至少有一种和牌类型判断可以和牌则可以和牌。
	 */
	public boolean canWin() {
		return false;
	}

	/**
	 * 根据当前状态判断游戏是否结束。
	 */
	public boolean tryEndGame(GameContext context) {
		return false;
	}
}
