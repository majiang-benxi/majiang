package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.object.TileGroupType.ZIPAI_GROUP;

import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
/**
 * 动作类型“字牌”。
 * 
 * @author warter
 */
public class ZiPaiActionType extends AbstractActionType {

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return true;
	}

	@Override
	protected boolean checkAliveTileSizeCondition(int size) {
		return size % 3 == 2;
	}

	@Override
	protected int getActionTilesSize() {
		return ZIPAI_GROUP.size();
	}

	@Override
	protected boolean isLegalActionWithPreconition(GameContext context,PlayerLocation location,
			Tile tile) {
		return ZIPAI_GROUP.isLegalTile(tile);
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location,
			Tile tile) {
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(location);
		playerInfo.getAliveTiles().removeAll(tile);
		playerInfo.getTileGroups().add(new TileGroup(ZIPAI_GROUP, tile));
	}

}
