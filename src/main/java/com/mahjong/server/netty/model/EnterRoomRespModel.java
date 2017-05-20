package com.mahjong.server.netty.model;

import java.util.ArrayList;
import java.util.List;

import com.mahjong.server.bo.RoomRespModel;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.object.PlayerInfo;

public class EnterRoomRespModel extends RoomRespModel {
	private int roomState;
	
	public EnterRoomRespModel() {

	}
	public EnterRoomRespModel(String weixinId,boolean result,String mesg,RoomContext roomContex) {
		super();
		
		if(result){
			this.setResult(result);
			this.setMsg(mesg);
			GameContext gameContext = roomContex.getGameContext();
			this.setFangKaStrategy(gameContext.getGameStrategy().getRuleInfo().getFangKa().getCode());
			this.setRoomId(roomContex.getRoomNum());
			this.setRuleStrategy(gameContext.getGameStrategy().getRuleInfo().getMysqlRule());
			List<PlayerInfo> players = new ArrayList<PlayerInfo>();
			for(PlayerInfo play : players){
				if(weixinId.equals(play.getUserInfo().getWeixinMark())){
					this.setCurUserLocation(play.getUserLocation());
				}
			}
			players.addAll(gameContext.getTable().getPlayerInfos().values());
			this.setPlayers(players);
			this.roomState = roomContex.getRoomStatus().getCode();
		}else{
			this.setResult(result);
			this.setMsg(mesg);
		}
		
		
	}
	

	public int getRoomState() {
		return roomState;
	}

	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}

}
