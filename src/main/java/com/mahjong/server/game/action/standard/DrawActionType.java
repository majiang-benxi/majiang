package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;

import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.Tile;

/**
 * 动作类型“摸牌”。
<<<<<<< HEAD
=======
 * 
 * @author warter
>>>>>>> refs/remotes/origin/master
 */
public class DrawActionType extends AbstractActionType {

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return false;
	}

	@Override
	protected boolean checkLastActionCondition(ActionAndLocation al, PlayerLocation playerLocation) {
		// 必须是上家打牌后
		if (DISCARD.matchBy(al.getActionType())
				&& playerLocation.getRelationOf(al.getLocation()) == Relation.PREVIOUS) {
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
		return context.getTable().getTileWallSize() > 14;
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location, Tile tile) {
		Tile drawTile = context.getTable().draw(1);
		context.getTable().getPlayerByLocation(location).getAliveTiles().addTile(drawTile);
		context.getTable().getPlayerByLocation(location).setLastDrawedTile(drawTile);
	}

}
