package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.ANGANG;

import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.object.PlayerLocation;
import com.mahjong.server.game.object.Tile;

/**
 * 动作类型“摸底牌”。与摸牌动作的区别是，前提条件为自己补花或杠之后，并且是从牌墙底部摸。
 * 
 * @author warter
 */
public class DrawBottomActionType extends DrawActionType {

	@Override
	protected boolean checkLastActionCondition(ActionAndLocation al, PlayerLocation playerLocation) {
		// 必须是自己杠之后
		if (playerLocation == al.getLocation()
				&& (ANGANG.matchBy(al.getActionType()))) {
			return true;
		}
		return false;
  	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location, Tile tile) {
		Tile drawBottomTile = context.getTable().drawBottom(1);
		context.getPlayerInfoByLocation(location).getAliveTiles().addTile(drawBottomTile);
		context.getPlayerInfoByLocation(location).setLastDrawedTile(drawBottomTile);
	}

}
