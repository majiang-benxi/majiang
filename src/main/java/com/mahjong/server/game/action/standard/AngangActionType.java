package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.object.TileGroupType.ANGANG_GROUP;

import java.util.Collection;

import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.GameContext.PlayerView;
import com.mahjong.server.game.action.AbstractActionType;
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
	public boolean canPass(GameContext context, com.mahjong.server.game.object.PlayerLocation location) {
		return true;
	}

	@Override
	protected int getActionTilesSize() {
		return ANGANG_GROUP.size();
	}

	@Override
	protected boolean isLegalActionWithPreconition(PlayerView context,
			Tile tiles) {
		return ANGANG_GROUP.isLegalTile(tiles);
	}

	@Override
	protected void doLegalAction(GameContext context, com.mahjong.server.game.object.PlayerLocation location,
			Tile tiles) {
		PlayerInfo playerInfo = context.getPlayerInfoByLocation(location);
		playerInfo.getAliveTiles().removeAll(tiles);
		playerInfo.getTileGroups().add(new TileGroup(ANGANG_GROUP, tiles));
	}

	@Override
	public Collection<Tile> getLegalActionTiles(PlayerView context) {
		// TODO Auto-generated method stub
		return null;
	}

}
