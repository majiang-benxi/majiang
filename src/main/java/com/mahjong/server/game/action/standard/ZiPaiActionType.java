package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.DEAL;
import static com.mahjong.server.game.action.standard.StandardActionType.ZIPAI;
import static com.mahjong.server.game.object.TileGroupType.XUAN_FENG_GANG_DNXB_GROUP;
import static com.mahjong.server.game.object.TileGroupType.XUAN_FENG_GANG_ZFB_GROUP;

import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
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
	public void doAction(GameContext context, PlayerLocation location, Action action) throws IllegalActionException {
		// TODO Auto-generated method stub
		super.doAction(context, location, action);
	}

	@Override
	protected int getActionTilesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canDo(GameContext context, PlayerLocation location) {
		if (context.getLastAction().equals(DEAL)) {
			PlayerInfo playerInfo = context.getTable().getPlayerByLocation(location);
			if(playerInfo!=null){
				Tile aliveTile = playerInfo._getSortAliveTiles();
				if (aliveTile.containsAll(new Tile(new byte[] { 0x31, 0x32, 0x33, 0x34 }))
						|| aliveTile.containsAll(new Tile(new byte[] { 0x35, 0x36, 0x37 }))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected boolean isLegalActionWithPreconition(GameContext context,PlayerLocation location,
			Tile tile) {
		return XUAN_FENG_GANG_ZFB_GROUP.isLegalTile(tile) || XUAN_FENG_GANG_DNXB_GROUP.isLegalTile(tile);
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location,
			Tile tile) {
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(location);
		if(playerInfo!=null){
			playerInfo._getSortAliveTiles().removeAll(tile);
			if (tile.getPai().length == 3) {
				playerInfo.getTileGroups().add(new TileGroup(XUAN_FENG_GANG_ZFB_GROUP, tile));
				playerInfo.setDiscardAuth(false);
			} else {
				playerInfo.getTileGroups().add(new TileGroup(XUAN_FENG_GANG_DNXB_GROUP, tile));
				playerInfo.setDiscardAuth(true);			
			}
			context.getLocalDoneActions().add(new ActionAndLocation(new Action(ZIPAI, tile), location));
		}

	}

}
