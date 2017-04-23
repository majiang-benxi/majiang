package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.object.TileGroupType.*;
import java.util.Set;
import java.util.function.Predicate;

import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;

/**
 * 动作类型“暗杠”。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class AngangActionType extends AbstractActionType {

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return true;
	}

	@Override
	protected Predicate<Integer> getAliveTileSizePrecondition() {
		return size -> size % 3 == 2;
	}

	@Override
	protected int getActionTilesSize() {
		return ANGANG_GROUP.size();
	}

	@Override
	protected boolean isLegalActionWithPreconition(PlayerView context,
			Tile tiles) {
		return ANGANG_GROUP.isLegalTiles(tiles);
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location,
			Tile tiles) {
		PlayerInfo playerInfo = context.getPlayerInfoByLocation(location);
		playerInfo.getAliveTiles().removeAll(tiles);
		playerInfo.getTileGroups().add(new TileGroup(ANGANG_GROUP, tiles));
	}

}
