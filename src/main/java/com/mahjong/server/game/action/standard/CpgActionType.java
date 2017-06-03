package com.mahjong.server.game.action.standard;

import static com.mahjong.server.game.action.standard.StandardActionType.CHI;
import static com.mahjong.server.game.action.standard.StandardActionType.DISCARD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;

 import com.mahjong.server.game.action.AbstractActionType;
import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.PlayerLocation.Relation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
/**
 * 吃、碰、直杠动作类型的统一逻辑。<br>
 * 这类动作的共同点是：
 * <li>都可以放弃；
 * <li>前提条件都是别的玩家出牌后；
 * <li>都是从特定关系的玩家的出牌中得牌，并组成一种group。
 * 
 * @author warter
 */
public class CpgActionType extends AbstractActionType {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(CpgActionType.class.getSimpleName());

	private TileGroupType groupType;
	private Collection<PlayerLocation.Relation> lastActionRelations = new ArrayList<PlayerLocation.Relation>();

	/**
	 * 新建实例。
	 * 
	 * @param groupType
	 *            组成的牌组类型
	 * @param lastActionRelations
	 *            限制上一个动作的玩家（出牌者）与当前玩家的位置关系
	 */
	public CpgActionType(TileGroupType groupType,
			Collection<Relation> lastActionRelations) {
		Objects.requireNonNull(groupType);
		this.groupType = groupType;
		if (CollectionUtils.isNotEmpty(lastActionRelations)) {
			this.lastActionRelations = lastActionRelations;
		} else {
			for (Relation relation : Relation.values()) {
				if (relation.isOther()) {
					this.lastActionRelations.add(relation);
				}
			}
		}
	}

	/**
	 * 新建实例。上一个动作的玩家（出牌者）与当前玩家的位置关系是所有其他人。
	 * 
	 * @param groupType
	 *            组成的牌组类型
	 */
	public CpgActionType(TileGroupType groupType) {
		this(groupType, null);
	}

	@Override
	public boolean canPass(GameContext context, PlayerLocation location) {
		return true;
	}

	/**
	 * 验证本次玩家操作跟上次操作之间位置关系是否合法
	 * 必须是指定关系的人出牌后
	 * @param lastAction
	 * @param playerLocation
	 * @return
	 */
	protected boolean checkLastActionCondition(Action lastAction, PlayerLocation playerLocation) {
		if (DISCARD.matchBy(lastAction.getType()) && lastActionRelations.contains(playerLocation)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected int getActionTilesSize() {
		return groupType.size() - 1;
	}

	@Override
	protected boolean isLegalActionWithPreconition(GameContext context,PlayerLocation location,
			Tile tile) {
		Tile testTiles = Tile.addTile(tile, context.getLastAction().getTile());
		boolean legal = groupType.isLegalTile(testTiles);
		return legal;
	}

	@Override
	protected void doLegalAction(GameContext context, PlayerLocation location,
			Tile tile) {
		PlayerInfo playerInfo = context.getTable().getPlayerByLocation(location);

		playerInfo._getSortAliveTiles().removeAll(tile);

		Tile gotTile = context.getLastAction().getTile();
		TileGroup group = new TileGroup(groupType, gotTile,
				location.getRelationOf(context.getLastActionLocation()),
				Tile.addTile(tile, gotTile));
		playerInfo.getTileGroups().add(group);
		playerInfo.setDiscardAuth(true);
		context.getLocalDoneActions().add(new ActionAndLocation(new Action(CHI, Tile.addTile(tile, gotTile)), location));// 存吃和碰这里没啥区别

	}

	/**
	 * 如果此类与testType的真正类是从属关系，并且testType的groupType与此对象相同，则视为match。
	 * 
	 * @see com.github.blovemaple.mj.action.AbstractActionType#matchBy(com.github.blovemaple.mj.action.ActionType)
	 */
	@Override
	public boolean matchBy(ActionType testType) {
		if (!CpgActionType.class.isAssignableFrom(testType.getRealTypeClass()))
			return false;
		if (!groupType.equals(
				((CpgActionType) testType.getRealTypeObject()).groupType))
			return false;
		return true;
	}

}
