package com.mahjong.server.game.context;
import java.util.concurrent.ConcurrentHashMap;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.object.PlayOnline;
import com.mahjong.server.game.object.PlayerLocation;

public class HouseContext {

	private static ConcurrentHashMap<Integer, RoomContext> rommList = new ConcurrentHashMap<Integer, RoomContext>();

	public RoomContext getRoomByNum(Integer roomNum) {
		return rommList.get(roomNum);
	}

	public boolean isRoomExits(Integer roomNum) {
		return rommList.containsKey(roomNum);
	}

	public RoomContext addRoom(Integer roomNum, UserInfo creator, String ruleStr) {
		RoomContext roomContext = new RoomContext();
		//
		// roomContext = rommList.putIfAbsent(roomNum, roomContext);
		//
		// if (roomContext.getGameContext() == null) {
		// return null;
		// }
		//
		// roomContext.setRoomNum(roomNum);
		//
		// MahjongTable table = new MahjongTable();
		// table.init();
		// PlayOnline creatorPlayer = new PlayOnline();
		// creatorPlayer.setUserInfo(creator);
		// table.setPlayer(PlayerLocation.EAST, creatorPlayer);
		//
		// GameStrategy gameStrategy =
		// GameStrategyPool.gameStrategyPool.get(ruleStr);
		//
		// GameContext gameContext = new GameContext(table, gameStrategy);
		//
		// roomContext.setGameContext(gameContext);
		// roomContext.setRoomStatus(RoomStatus.WAIT_FOR_READY);
		//
		return roomContext;

	}

	public boolean joinRoom(Integer roomNum, UserInfo userInfo) {
		RoomContext roomContext = getRoomByNum(roomNum);
		PlayOnline nowPlayer = new PlayOnline();
		nowPlayer.setUserInfo(userInfo);

		if (roomContext.getGameContext().getPlayerInfoByLocation(PlayerLocation.NORTH) == null) {
			roomContext.getGameContext().getTable().setPlayer(PlayerLocation.NORTH, nowPlayer);
		} else if (roomContext.getGameContext().getPlayerInfoByLocation(PlayerLocation.WEST) == null) {
			roomContext.getGameContext().getTable().setPlayer(PlayerLocation.WEST, nowPlayer);
		} else if (roomContext.getGameContext().getPlayerInfoByLocation(PlayerLocation.SOUTH) == null) {
			roomContext.getGameContext().getTable().setPlayer(PlayerLocation.SOUTH, nowPlayer);
		} else {
			return false;
		}

		return true;

	}

}