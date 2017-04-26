package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;

import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.GameContext.PlayerView;
import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.object.PlayerLocation;
import com.mahjong.server.game.object.PlayerLocation.Relation;
import com.mahjong.server.game.object.Tile;

/**
 * 动作类型“摸牌”。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
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
	protected boolean isLegalActionWithPreconition(PlayerView context,
			Tile tiles) {
		// 牌墙中必须有牌才能摸
		return context.getTableView().getTileWallSize() > 0;
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location, Tile tile) {
		Tile drawTile = context.getTable().draw(1);
		context.getPlayerInfoByLocation(location).getAliveTiles().addTile(drawTile);
		context.getPlayerInfoByLocation(location).setLastDrawedTile(drawTile);
	}

}
