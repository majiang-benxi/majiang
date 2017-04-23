package com.mahjong.server.exception;

import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.action.Action;

/**
 * 尝试执行非法动作时抛出此异常。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class IllegalActionException extends Exception {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private Action action;

	public IllegalActionException(GameContext context, com.mahjong.server.game.object.PlayerLocation location,
			Action action) {
		super(location + action.toString() + " context: " + context);
		this.action = action;
	}

}
