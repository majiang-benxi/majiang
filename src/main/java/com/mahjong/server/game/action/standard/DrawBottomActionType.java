package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.ANGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.BUGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.BUHUA;
import static com.mahjong.server.game.action.standard.StandardActionType.ZHIGANG;

import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.Tile;

/**
 * 动作类型“摸底牌”。与摸牌动作的区别是，前提条件为自己补花或杠之后，并且是从牌墙底部摸。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class DrawBottomActionType extends DrawActionType {

	@Override
	protected boolean checkLastActionCondition(ActionAndLocation al, PlayerLocation playerLocation) {
		// 必须是自己补花或杠之后
		if (playerLocation == al.getLocation()
				&& (BUHUA.matchBy(al.getActionType()) || ANGANG.matchBy(al.getActionType())
						|| ZHIGANG.matchBy(al.getActionType()) || BUGANG.matchBy(al.getActionType()))) {
			return true;
		}
		return false;
  	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location, Tile tile) {
		Tile drawBottomTile = context.getTable().drawBottom(1);
		context.getTable().getPlayerByLocation(location).getAliveTiles().addTile(drawBottomTile);
		context.getTable().getPlayerByLocation(location).setLastDrawedTile(drawBottomTile);
	}

}
