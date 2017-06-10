package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;

import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;

/**
 * 动作类型“打牌”。
 * 
 * @author warter
 */
public class DiscardActionType extends AbstractActionType {

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return false;
	}

	@Override
	protected boolean checkAliveTileSizeCondition(int size) {
		return size % 3 == 2;
	}

	@Override
	protected int getActionTilesSize() {
		return 1;
	}
	//
	// @Override
	// protected boolean isLegalActionWithPreconition(PlayerView context,
	// Tile tiles) {
	// if (!context.getMyInfo().isTing()) {
	// // 没听牌时，所有aliveTiles都可以打出
	// return true;
	// } else {
	// // 听牌后只允许打出最后摸的牌
	// Tile justDrawed = context.getJustDrawedTile();
	// return justDrawed != null
	// && justDrawed.equals(tiles.getPai()[0]);
	// }
	// }

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location, Tile tile) {
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(location);
		if(playerInfo!=null){
			playerInfo._getSortAliveTiles().removeAll(tile);		
			playerInfo.getDiscardedTiles().addTile(tile);
			context.getTable().resetPlayersLastTile(location);
			playerInfo.setLastDiscardTile(tile);
			context.getLocalDoneActions().add(new ActionAndLocation(new Action(DISCARD, tile), location));
		}

	}

	@Override
	protected boolean isLegalActionWithPreconition(GameContext context,PlayerLocation location, Tile tiles) {
		return true;
	}

}
