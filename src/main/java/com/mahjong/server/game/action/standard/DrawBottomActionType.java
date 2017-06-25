package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.ANGANG;
import static com.mahjong.server.game.action.standard.StandardActionType.DRAW_BOTTOM;

import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
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
	protected boolean isLegalActionWithPreconition(GameContext context, PlayerLocation location, Tile tiles) {
		// 牌墙中必须有超过14张牌才能摸，否则就算黄庄
		return context.getTable().getTileWallSize() > 14;
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location, Tile tile) {
		Tile drawBottomTile = context.getTable().drawBottom(1);
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(location);
		if(playerInfo!=null){
		    context.getTable().getRemainderTileNum().addAndGet(-1);
			playerInfo.setDiscardAuth(true);
			playerInfo._getSortAliveTiles().addTile(drawBottomTile);
			playerInfo.setLastDrawedTile(drawBottomTile);
			context.getTable().resetPlayersLastDrawTile(location);
			context.getLocalDoneActions().add(new ActionAndLocation(new Action(DRAW_BOTTOM, drawBottomTile), location));
			context.getTable().printAllPlayTiles();
		}

	}

}
