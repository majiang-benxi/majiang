package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.ANGANG;
import static com.mahjong.server.game.object.TileGroupType.ANGANG_GROUP;

import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.game.rule.PlayRule;
/**
 * 动作类型“暗杠”。
 * 
 * @author warter
 */
public class AngangActionType extends CpgActionType  {

	public AngangActionType(TileGroupType groupType) {
		super(groupType);
	}

	@Override
	public boolean canPass(GameContext context, com.mahjong.server.game.enums.PlayerLocation location) {
		return true;
	}

	protected boolean checkAliveTileSizeCondition(int size) {
		return size % 3 == 2;// 活牌数量至少有对将
	}

	@Override
	protected int getActionTilesSize() {
		return ANGANG_GROUP.size();// 暗杠需要打出4张牌
	}

	@Override
	protected boolean isLegalActionWithPreconition(GameContext context,PlayerLocation location,Tile tiles) {
		return  context.getGameStrategy().getRuleInfo().getPlayRules().contains(PlayRule.GANG)&&ANGANG_GROUP.isLegalTile(tiles);
	}

	@Override
	protected void doLegalAction(GameContext context, com.mahjong.server.game.enums.PlayerLocation location,
			Tile tile) {
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(location);
		if(playerInfo!=null){
			playerInfo._getSortAliveTiles().removeAll(tile);
			playerInfo.getTileGroups().add(new TileGroup(ANGANG_GROUP, tile));
			playerInfo.setDiscardAuth(true);
			context.getLocalDoneActions().add(new ActionAndLocation(new Action(ANGANG, tile), location));
		}
	}
}
