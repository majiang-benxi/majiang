package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.enums.PlayerLocation.Relation.PREVIOUS;
import static com.mahjong.server.game.object.TileGroupType.CHI_GROUP;
import static com.mahjong.server.game.object.TileGroupType.PENG_GROUP;

import java.util.Collection;
import java.util.Collections;

import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.Tile;

/**
 * 一些标准的动作类型。<br>
 * 枚举的每种动作类型包含对应Type类的单例，并委托调用其对应的方法。
<<<<<<< HEAD
=======
 * 
 * @author warter
>>>>>>> refs/remotes/origin/master
 */
public enum StandardActionType implements ActionType {
	/**
	 * 发牌
	 */
	DEAL(new DealActionType()),
	/**
	 * 吃
	 */
	CHI(new CpgActionType(CHI_GROUP, Collections.singleton(PREVIOUS))),
	/**
	 * 碰
	 */
	PENG(new CpgActionType(PENG_GROUP)),
	/**
	 * 补杠
	 */
	BUGANG(new BugangActionType()),
	/**
	 * 暗杠
	 */
	ANGANG(new AngangActionType()),
	/**
	 * 字牌
	 */
	ZIPAI(new ZiPaiActionType()),
	/**
	 * 打牌
	 */
	DISCARD(new DiscardActionType()),
	/**
	 * 摸牌
	 */
	DRAW(new DrawActionType()),
	/**
	 * 摸底牌
	 */
	DRAW_BOTTOM(new DrawBottomActionType()),
	/**
	 * 和牌
	 */
	WIN(new WinActionType());

	private final ActionType type;

	private StandardActionType(ActionType type) {
		this.type = type;
	}

	// 以下都是委托方法，调用type的对应方法

	@Override
	public boolean canDo(GameContext context, PlayerLocation location) {
		return type.canDo(context, location);
	}

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return type.canPass(context, location);
	}


	@Override
	public void doAction(GameContext context, PlayerLocation location, Action action) throws IllegalActionException {
		type.doAction(context, location, action);
	}

	@Override
	public boolean matchBy(ActionType testType) {
		return type.matchBy(testType);
	}

	@Override
	public Class<? extends ActionType> getRealTypeClass() {
		return type.getRealTypeClass();
	}

	@Override
	public ActionType getRealTypeObject() {
		return type;
	}

	@Override
	public boolean isLegalAction(GameContext context, PlayerLocation location, Action action) {
		return type.isLegalAction(context, location, action);
	}

	@Override
	public Collection<Tile> getLegalActionTiles(GameContext context,PlayerLocation location) {
		return type.getLegalActionTiles( context, location);

	}

}
