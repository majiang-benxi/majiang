package com.mahjong.server.game.action.standard;


import java.util.List;

import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
/**
 * 动作类型“补杠”。
 * @author warter
 */
public class BugangActionType extends AbstractActionType {

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return true;
	}

	protected boolean checkAliveTileSizeCondition(int size) {
		return size % 3 == 2;// 活牌数量至少有对将
	}

	@Override
	protected int getActionTilesSize() {
		return 1;
	}

	@Override
	protected boolean isLegalActionWithPreconition(GameContext context,PlayerLocation location,
			Tile tile) {
		return findLegalPengGroup( context.getTable().getPlayerByLocation(location), tile) != null;
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location,
			Tile tile) {
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(location);
		playerInfo.setDiscardAuth(true);
		TileGroup group = findLegalPengGroup(playerInfo, tile);
		if (group == null)
			// tiles不合法，抛异常，因为调用此方法时应该确保是合法的
			throw new IllegalArgumentException(
					"Illegal bugang tiles: " + tile);

		// 在aliveTiles中去掉动作牌
		playerInfo._getSortAliveTiles().removeAll(tile);

		// 把碰组改为补杠组，并加上动作牌
		TileGroup newGroup = new TileGroup(TileGroupType.BUGANG_GROUP, group.getGotTile(),
				group.getFromRelation(), Tile.addTile(group.getTile(), tile));
		List<TileGroup> groups = playerInfo.getTileGroups();
		int groupIndex = groups.indexOf(group);
		groups.remove(groupIndex);
		groups.add(groupIndex, newGroup);
	}

	/**
	 * 返回在玩家的牌中能与动作牌组成补杠的碰组（Optional）。
	 */
	private TileGroup findLegalPengGroup(PlayerInfo playerInfo,
			Tile actionTiles) { // 过滤出能与动作相关牌组成合法补杠的
		for (TileGroup tileGroup : playerInfo.getTileGroups()) {
			if (tileGroup.getType() == TileGroupType.PENG_GROUP) {
				// 取出碰组的牌，并加上动作中的tiles（应该只有一个tile）
				Tile gangTiles = Tile.addTile(tileGroup.getTile(), actionTiles);
				boolean res = TileGroupType.BUGANG_GROUP.isLegalTile(gangTiles);
				if (res) {
					return tileGroup;
				}
			}
			return null;
		}
		return null;
	}

}
