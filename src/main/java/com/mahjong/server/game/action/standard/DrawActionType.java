package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;
import static com.mahjong.server.game.action.standard.StandardActionType.DRAW;

import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroupType;

/**
 * 动作类型“摸牌”。
  * 
 * @author warter
  */
public class DrawActionType extends AbstractActionType {
	
	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return false;
	}

	@Override
	protected boolean checkLastActionCondition(ActionAndLocation al, PlayerLocation playerLocation) {
		// 必须是上家打牌后
//		if (DISCARD.matchBy(al.getActionType())
//				&& playerLocation.getRelationOf(al.getLocation()) == Relation.PREVIOUS) {
//			return true;
//		}
		if (DISCARD.matchBy(al.getActionType())) {
			return true;
		}
		return false;
	}

	@Override
	protected int getActionTilesSize() {
		return 0;
	}

	@Override
	protected boolean isLegalActionWithPreconition(GameContext context,PlayerLocation location,
			Tile tiles) {
		// 牌墙中必须有超过14张牌才能摸，否则就算黄庄
		return context.getTable().getTileWallSize() > context.getHuangZhuangtTileNum();
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location, Tile tile) {
		Tile drawTile = context.getTable().draw(1);
		PlayerInfo playerInfo=context.getTable().getPlayerByLocation(location);
		if(playerInfo!=null){
		    context.getTable().getRemainderTileNum().addAndGet(-1);
			playerInfo._getSortAliveTiles().addTile(drawTile);
			playerInfo.setLastDrawedTile(drawTile);
			playerInfo.setDiscardAuth(true);
			context.getTable().resetPlayersLastDrawTile(location);
			context.getLocalDoneActions().add(new ActionAndLocation(new Action(DRAW, drawTile), location));
			context.getTable().printAllPlayTiles();
		}
	}

}
