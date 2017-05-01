package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.DEAL;
import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;
import static com.mahjong.server.game.action.standard.StandardActionType.DRAW;

import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.Tile;
public class WinActionType extends AbstractActionType {

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return true;
	}

	@Override
	protected int getActionTilesSize() {
		return 0;
	}

	@Override
	protected boolean checkLastActionCondition(ActionAndLocation al, PlayerLocation playerLocation) {
		// 必须是发牌、自己摸牌，或别人打牌后
		if (DEAL.matchBy(al.getActionType()) || (al.getLocation() == playerLocation ? DRAW.matchBy(al.getActionType())
				: DISCARD.matchBy(al.getActionType()))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location, Tile tile) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isLegalActionWithPreconition(GameContext context,PlayerLocation location, Tile tiles) {
		// TODO Auto-generated method stub
		return false;
	}

}
