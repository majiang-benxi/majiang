package com.mahjong.server.game.context;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.enums.RoomStatus;

public class RoomContext {

	/**
	 * 房间号
	 */
	private Integer roomNum;

	/**
	 * 房间状态
	 */
	private RoomStatus roomStatus;

	/**
	 * 游戏上下文
	 */
	private GameContext gameContext;

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public RoomStatus getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}

	public GameContext getGameContext() {
		return gameContext;
	}

	public void setGameContext(GameContext gameContext) {
		this.gameContext = gameContext;
	}

	public boolean joinRoom(UserInfo userInfo) {
		return this.gameContext.joinRoom( userInfo);
	}

}