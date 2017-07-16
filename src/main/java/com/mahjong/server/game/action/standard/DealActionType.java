package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.DEAL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.MahjongTable;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;

/**
 * 动作类型“发牌”。<br>
 * 发牌动作不由玩家执行，只实现doAction方法。
 * 
 * @author warter
 */
public class DealActionType implements ActionType {

	@Override
	public boolean canDo(GameContext context, PlayerLocation location) {
		// 不作为常规动作
		return false;
	}

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Tile> getLegalActionTiles(GameContext context,PlayerLocation location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isLegalAction(GameContext context, PlayerLocation location, Action action) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doAction(GameContext context, PlayerLocation zhuangLocation, Action action)
			throws IllegalActionException {
		MahjongTable table = context.getTable();
		PlayerLocation zhuang = context.getZhuangLocation();
		for (int i = 0; i < 4; i++) {
			int drawCount = i < 3 ? 4 : 1;
			for (Relation relation : Relation.values()) {
				PlayerLocation playerLocation = zhuang.getLocationOf(relation);
				PlayerInfo playerInfo = context.getTable().getPlayerByLocation(playerLocation);
				if(playerInfo!=null){
					playerInfo._getSortAliveTiles().addTile(table.draw(drawCount));
				}
			}
		}
		Tile zhuangFirstTile=table.draw(1);
		context.getTable().getPlayerByLocation(zhuangLocation)._getSortAliveTiles()
				.addTile(zhuangFirstTile).sort();
		context.getTable().getPlayerByLocation(zhuangLocation).setDiscardAuth(true);
		context.getTable().getPlayerByLocation(zhuangLocation).setLastDrawedTile(zhuangFirstTile);
		context.getTable().resetPlayersLastDrawTile(zhuangLocation);
		context.getTable().setFanhui(table.drawBottom(1).getPai()[0]);// 系统翻出一张会牌
	    context.getTable().getRemainderTileNum().addAndGet(-54);
		  // test--------tianhu----begin---
 		PlayerInfo playerInfo3=	context.getTable().getPlayerByLocation(zhuangLocation);
 
 	    playerInfo3.setAliveTiles(new Tile(new byte[]{0x13,0x13,0x17,0x17}));
 		List<TileGroup> tileGroups3=new ArrayList<TileGroup>();
 		tileGroups3.add(new TileGroup(TileGroupType.CHI_GROUP, new Tile(new byte[]{0x01, 0x02, 0x03})));
 		tileGroups3.add(new TileGroup(TileGroupType.PENG_GROUP, new Tile(new byte[]{0x28,0x28,0x28})));
 		tileGroups3.add(new TileGroup(TileGroupType.PENG_GROUP, new Tile(new byte[]{0x37,0x37,0x37})));
 
 		playerInfo3.setTileGroups(tileGroups3);
 		context.getTable().getPlayerByLocation(zhuangLocation)._getSortAliveTiles()
 		.addTile(zhuangFirstTile).sort();
 		context.getTable().setFanhui((byte)0x12);
  		
  	    //test--------------end----
		 
		 		
		context.getTable().printAllPlayTiles();
		context.getLocalDoneActions().add(new ActionAndLocation(new Action(DEAL, zhuangFirstTile), zhuangLocation));
	}

	@Override
	public String name() {
		return this.getClass().getSimpleName();
	}

	/**
	 * 返回真正的动作类型类。<br>
	 * 默认实现为返回此对象的类。如果不是这样（例如{@link StandardActionType}）则需要重写此方法。
	 */
	@Override
	public Class<? extends ActionType> getRealTypeClass() {
		return this.getClass();
	}

	/**
	 * 返回真正的动作类型对象。<br>
	 * 默认实现为返回此对象。如果不是这样（例如{@link StandardActionType}）则需要重写此方法。
	 */
	public ActionType getRealTypeObject() {
		return this;
	}

	@Override
	public boolean matchBy(ActionType testType) {
		return getRealTypeClass().isAssignableFrom(testType.getRealTypeClass());
	}
}
