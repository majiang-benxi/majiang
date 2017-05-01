package com.mahjong.server.game.context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
	
	/**
	 * 统一解散房间人数
	 */
	private AtomicInteger agreeKillRoomNum = new AtomicInteger(0);
	/**
	 * 不同意解散房间人数
	 */
	private AtomicInteger disAgreeKillRoomNum = new AtomicInteger(0);
	
	private List<String> disagreeUserNames = new ArrayList<String>();

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

	public AtomicInteger getAgreeKillRoomNum() {
		return agreeKillRoomNum;
	}

	public void setAgreeKillRoomNum(AtomicInteger agreeKillRoomNum) {
		this.agreeKillRoomNum = agreeKillRoomNum;
	}

	public AtomicInteger getDisAgreeKillRoomNum() {
		return disAgreeKillRoomNum;
	}

	public void setDisAgreeKillRoomNum(AtomicInteger disAgreeKillRoomNum) {
		this.disAgreeKillRoomNum = disAgreeKillRoomNum;
	}

	public List<String> getDisagreeUserNames() {
		return disagreeUserNames;
	}

	public void setDisagreeUserNames(List<String> disagreeUserNames) {
		this.disagreeUserNames = disagreeUserNames;
	}

}