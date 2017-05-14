package com.mahjong.server.netty.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.mahjong.server.bo.RoomRespModel;
import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;

public class CreateRoomRespModel extends RoomRespModel {

	public CreateRoomRespModel(String weixinId ,boolean result,RoomContext roomContex) {
		super();
		
		if(result){
			this.setResult(result);
			this.setMsg("恭喜您，创建房间成功！");
			GameContext gameContext = roomContex.getGameContext();
			this.setFangKaStrategy(gameContext.getGameStrategy().getRuleInfo().getFangKa().getCode());
			this.setRoomId(roomContex.getRoomNum());
			this.setRuleStrategy(gameContext.getGameStrategy().getRuleInfo().getMysqlRule());
			List<PlayerInfo> players = new ArrayList<PlayerInfo>();
			for (Entry<PlayerLocation, PlayerInfo> entry : gameContext.getTable().getPlayerInfos().entrySet()) {
				if (weixinId.equals(entry.getValue().getUserInfo().getWeixinMark())) {
					this.setCurUserLocation(entry.getValue().getUserLocation());
					break;
				}
			}
			players.addAll(gameContext.getTable().getPlayerInfos().values());
			this.setPlayers(players);
		}else{
			this.setResult(result);
			this.setMsg("创建房间失败！");
		}
		
		
	}
}
