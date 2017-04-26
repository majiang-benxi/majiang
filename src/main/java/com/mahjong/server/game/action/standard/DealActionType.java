package com.mahjong.server.game.action.standard;

import java.util.Collection;

import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.object.MahjongTable;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.PlayerLocation;
import com.mahjong.server.game.object.PlayerLocation.Relation;
import com.mahjong.server.game.object.Tile;

/**
 * 动作类型“发牌”。<br>
 * 发牌动作不由玩家执行，只实现doAction方法。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class DealActionType implements ActionType {

	@Override
	public boolean canDo(GameContext context, PlayerLocation location) {
		// 不作为常规动作
		return false;
	}

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Tile> getLegalActionTiles(
			GameContext.PlayerView playerView) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isLegalAction(GameContext context, PlayerLocation location, Action action) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doAction(GameContext context, PlayerLocation location, Action action) throws IllegalActionException {
		MahjongTable table = context.getTable();
		PlayerLocation zhuang = context.getZhuangLocation();
		for (int i = 0; i < 4; i++) {
			int drawCount = i < 3 ? 4 : 1;
			for (Relation relation : Relation.values()) {
				PlayerLocation playerLocation = zhuang.getLocationOf(relation);
				PlayerInfo playerInfo = context.getPlayerInfoByLocation(playerLocation);
				playerInfo.getAliveTiles().addTile(table.draw(drawCount));
			}
		}
		context.getPlayerInfoByLocation(zhuang).getAliveTiles()
				.addTile(table.draw(1));
	}

	@Override
	public String name() {
		return this.getClass().getSimpleName();
	}

	/**
	 * 返回真正的动作类型类。<br>
	 * 默认实现为返回此对象的类。如果不是这样（例如{@link StandardActionType}）则需要重写此方法。
	 */
	@Override
	public Class<? extends ActionType> getRealTypeClass() {
		return this.getClass();
	}

	/**
	 * 返回真正的动作类型对象。<br>
	 * 默认实现为返回此对象。如果不是这样（例如{@link StandardActionType}）则需要重写此方法。
	 */
	public ActionType getRealTypeObject() {
		return this;
	}

	@Override
	public boolean matchBy(ActionType testType) {
		return getRealTypeClass().isAssignableFrom(testType.getRealTypeClass());
	}

}
