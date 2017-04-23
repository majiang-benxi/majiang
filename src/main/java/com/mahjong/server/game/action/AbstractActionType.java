package com.mahjong.server.game.action;


import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.mahjong.server.exception.IllegalActionException;
import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.GameContext.PlayerView;
import com.mahjong.server.game.action.standard.CpgActionType;
import com.mahjong.server.game.action.standard.StandardActionType;
import com.mahjong.server.game.object.PlayerLocation;
import com.mahjong.server.game.object.Tile;

/**
 * 各种ActionType的共同逻辑。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public abstract class AbstractActionType implements ActionType {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(AbstractActionType.class.getSimpleName());

	/**
	 * 名称，用作唯一标识。<br>
	 * 默认实现为{@code this.getClass().getSimpleName()}。<br>
	 * 注意：使用{@link StandardActionType}时，name为枚举值的{@code name(})。
	 * 
	 */

	protected String name() {
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
	protected ActionType getRealTypeObject() {
		return this;
	}

	/**
	 * 返回指定的动作类型是否是此类表示的动作。<br>
	 * 有些动作有上下从属关系，比如“摸底牌”是“摸牌”，但反过来不是。<br>
	 * 默认实现为判断真正类的从属关系。如果不是这样（例如{@link StandardActionType}和{@link CpgActionType}
	 * ）则需要重写此方法。
	 */
	public boolean matchBy(ActionType testType) {
		return getRealTypeClass().isAssignableFrom(testType.getRealTypeClass());
	}

	/**
	 * 判断当前状态下指定玩家是否符合做出此类型动作的前提条件（比如“碰”的前提条件是别人刚出牌）。<br>
	 * 默认实现调用相应方法对上一个动作和活牌数量进行限制，进行判断。
	 */
	protected boolean meetPrecondition(GameContext.PlayerView context) {
		// // 验证aliveTiles数量条件
		// Predicate<Integer> aliveTileSizeCondition =
		// getAliveTileSizePrecondition();
		// if (aliveTileSizeCondition != null)
		// if (!aliveTileSizeCondition
		// .test(context.getMyInfo().getAliveTiles().getPai().length))
		// return false;
		//
		// // 验证上一个动作条件
		// BiPredicate<ActionAndLocation, PlayerLocation> lastActionPrecondition
		// = getLastActionPrecondition();
		// if (lastActionPrecondition != null) {
		// ActionAndLocation lastAction = context.getLastActionAndLocation();
		// if (lastAction != null)
		// if (!lastActionPrecondition.test(lastAction,
		// context.getMyLocation()))
		// return false;
		// }
		//
		return true;
	}

	/**
	 * 返回进行此类型动作时对对活牌数量的限制条件。<br>
	 * 不允许返回null，不限制应该返回恒null的函数。默认返回恒null。<br>
	 * 此方法用于{@link #meetPrecondition}的默认实现。
	 */
	protected Predicate<Integer> getAliveTileSizePrecondition() {
		return null;
	}

	/**
	 * 判断指定状态下指定位置的玩家可否做此种类型的动作。调用此方法之前已经使用{@link #meetPrecondition}判断过符合前提条件。
	 * <br>
	 * 默认实现为：判断{@link #legalActionTilesStream}返回的流不为空。
	 */
	protected boolean canDoWithPrecondition(GameContext context,
			PlayerLocation location) {
		return false;
	}


	/**
	 * {@inheritDoc}<br>
	 * 默认实现为将action为null的和动作类型不符合的报异常，然后用{@link #isLegalActionTiles}检查是否合法。
	 * 
	 * @see com.github.blovemaple.mj.action.ActionType#isLegalAction(GameContext,
	 *      PlayerLocation, com.github.blovemaple.mj.action.Action)
	 */
	@Override
	public boolean isLegalAction(GameContext context, PlayerLocation location,
			Action action) {
		Objects.requireNonNull(action);
		if (!matchBy(action.getType()))
			throw new IllegalArgumentException(
					action.getType().getRealTypeClass().getSimpleName()
							+ " is not " + getRealTypeClass());
		if (!isLegalActionTiles(context.getPlayerView(location),
				action.getTile()))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}<br>
	 * 默认实现为用{@link #isLegalAction}检查是否合法，如果合法则调用{@link #doLegalAction}执行动作。
	 * 
	 * @see com.github.blovemaple.mj.action.ActionType#doAction(GameContext,
	 *      PlayerLocation, com.github.blovemaple.mj.action.Action)
	 */
	@Override
	public void doAction(GameContext context, PlayerLocation location,
			Action action) throws IllegalActionException {
		if (!isLegalAction(context, location, action))
			throw new IllegalActionException(context, location, action);

		doLegalAction(context, location, action.getTile());
	}

	// /**
	// * 返回一个流，流中包含指定状态下指定玩家可作出的此类型的所有合法动作的相关牌集合。<br>
	// * 默认实现为：在玩家手中的牌中选取所有合法数量个牌的组合，并使用{@link #isLegalActionTiles}过滤出合法的组合。<br>
	// * 如果合法的相关牌不限于手中的牌，则需要子类重写此方法。
	// */
	// protected Tile legalActionTilesStream(
	// GameContext.PlayerView context) {
	// return null;
	// }

	/**
	 * 返回合法动作中相关牌的可选范围。<br>
	 * 默认实现为指定玩家的aliveTiles。
	 */
	protected Tile getActionTilesRange(GameContext.PlayerView context,
			PlayerLocation location) {
		return context.getMyInfo().getAliveTiles();
	}

	/**
	 * 返回合法动作中相关牌的数量。可以为0。
	 */
	protected abstract int getActionTilesSize();

	/**
	 * 判断动作是否合法。<br>
	 * 默认实现为：先检查前提条件、相关牌数量、相关牌范围，如果满足再调用{@link #isLegalActionWithPreconition}。
	 */
	protected boolean isLegalActionTiles(GameContext.PlayerView context,
			Tile tile) {
		PlayerLocation location = context.getMyLocation();
		if (!meetPrecondition(context)) {
			return false;
		}

		int legalTilesSize = getActionTilesSize();
		if (legalTilesSize > 0
				&& (tile == null || tile.getPai().length != legalTilesSize)) {
			return false;
		}

		Tile legalTilesRange = getActionTilesRange(context, location);
		if (tile != null && legalTilesRange != null && !legalTilesRange.containsAll(tile)) {
			return false;
		}

		boolean legal = isLegalActionWithPreconition(context, tile);
		return legal;
	}

	/**
	 * 判断动作是否合法。调用此方法之前已经判断确保符合前提条件、相关牌数量、相关牌范围。
	 */
	protected abstract boolean isLegalActionWithPreconition(PlayerView context,
			Tile tiles);

	/**
	 * 执行动作。调用此方法之前已经确保符合动作类型，并使用{@link #isLegalActionTiles}判断过动作的合法性。
	 */
	protected abstract void doLegalAction(GameContext context,
			PlayerLocation location, Tile tile);

	@Override
	public String toString() {
		return name();
	}

}
