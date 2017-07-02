package com.mahjong.server.exception;

import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.enums.PlayerLocation;

/**
 * 尝试执行非法动作时抛出此异常。
 * 
 * @author warter
 */
public class IllegalActionException extends Exception {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private Action action;

	public IllegalActionException(GameContext context, PlayerLocation location,
			Action action) {
		super(location + action.toString() + " context: " + context);
		this.action = action;
	}

}
