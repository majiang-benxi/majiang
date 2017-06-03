package com.mahjong.server.game.context;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.enums.RoomStatus;
import com.mahjong.server.game.object.MahjongTable;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.rule.FangKa;
import com.mahjong.server.game.rule.GameStrategy;
import com.mahjong.server.game.rule.RuleInfo;

public class HouseContext {

	public static ConcurrentHashMap<Integer, RoomContext> rommList = new ConcurrentHashMap<Integer, RoomContext>();
	
	public static ConcurrentHashMap<String, RoomContext> weixinIdToRoom = new ConcurrentHashMap<String, RoomContext>();
	
	public static ConcurrentHashMap<String, UserInfo> weixinIdToUserInfo = new ConcurrentHashMap<String, UserInfo>();
	
	
	public static AtomicInteger onlineRoomNum = new AtomicInteger(0);
	public static AtomicInteger playRoomNum = new AtomicInteger(0);
	public static AtomicInteger waitRoomNum = new AtomicInteger(0);
	
	public static AtomicInteger onlineUserNum = new AtomicInteger(0);
	public static AtomicInteger playUserNum = new AtomicInteger(0);
	public static AtomicInteger waitUserNum = new AtomicInteger(0);
	
	
	public RoomContext getRoomByNum(Integer roomNum) {
		return rommList.get(roomNum);
	}

	public boolean isRoomExits(Integer roomNum) {
		return rommList.containsKey(roomNum);
	}

	public static RoomContext addRoom(UserInfo creator, String ruleStr,int fangKaJuShu) {
		RoomContext roomContext = new RoomContext();
		
		Integer roomNum = getRoomNum();
		int i = 0;
		while (rommList.get(roomNum) != null) {
			roomNum = getRoomNum();
			i++;
			System.out.println("创建房间号码本次循环次数=" + i);
		}
		rommList.put(roomNum, roomContext);
	    roomContext.setRoomNum(roomNum);
	   
	    MahjongTable table = new MahjongTable();
	    table.init();
	    PlayerInfo creatorPlayer = new PlayerInfo();
	    creatorPlayer.setUserInfo(creator);
	    creatorPlayer.setCurScore(1000);
	    creatorPlayer.setUserLocation(PlayerLocation.EAST.getCode());
	    table.setPlayer(PlayerLocation.EAST, creatorPlayer);
	    creatorPlayer.setZhuang(true);
	    GameStrategy gameStrategy = new GameStrategy();
	    RuleInfo ruleInfo = new RuleInfo();
		ruleInfo.setPlayRules(RuleInfo.parseRuleFromBitString(ruleStr));
	    ruleInfo.setFangKa(FangKa.fromCode(fangKaJuShu));
	    gameStrategy.setRuleInfo(ruleInfo);
	   
	    GameContext gameContext = new GameContext(table, gameStrategy);
		gameContext.setZhuangLocation(PlayerLocation.EAST);// 创建房间的人为庄
	    roomContext.setGameContext(gameContext);
	    roomContext.setRoomStatus(RoomStatus.WAIT_FOR_READY);
	   
		return roomContext;

	}

	private static int getRoomNum(){
		String roomNum = "";
		for(int i=0;i<6;i++){
			Random random = new Random();
			int eachNum = random.nextInt(10);
			roomNum += eachNum;
		}
		return Integer.parseInt(roomNum);
	}

}