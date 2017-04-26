package com.mahjong.server.game.action.standard;

import java.util.Set;

import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.GameContext.PlayerView;
import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.PlayerLocation;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import static com.mahjong.server.game.action.standard.StandardActionType.*;

/**
 * 动作类型“补花”。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class BuhuaActionType extends AbstractActionType {

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
		return BUHUA_GROUP.size();
	}

	@Override
	protected boolean isLegalActionWithPreconition(PlayerView context,
			Set<Tile> tiles) {
		return BUHUA_GROUP.isLegalTiles(tiles);
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location,
			Set<Tile> tiles) {
		PlayerInfo playerInfo = context.getPlayerInfoByLocation(location);
		playerInfo.getAliveTiles().removeAll(tiles);
		playerInfo.getTileGroups().add(new TileGroup(BUHUA_GROUP, tiles));
	}

}
