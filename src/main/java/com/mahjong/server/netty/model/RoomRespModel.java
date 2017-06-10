package com.mahjong.server.netty.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.mahjong.server.game.context.GameContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.vo.MajiangPlayView;

public class RoomRespModel extends MajiangPlayView {
	private boolean result;
	private String msg;

	public RoomRespModel() {

	}

	// 此函数会隐藏其他玩家信息。
	public RoomRespModel(RoomContext roomContex, PlayerLocation playerLocation) {
		GameContext gameContext = roomContex.getGameContext();
		this.setCurUserLocation(playerLocation.getCode());
		this.setFangKaStrategy(gameContext.getGameStrategy().getRuleInfo().getFangKa().getCode());
		this.setRoomId(roomContex.getRoomNum());
		this.setRuleStrategy(gameContext.getGameStrategy().getRuleInfo().getMysqlRule());
		List<PlayerInfo> players = new ArrayList<PlayerInfo>();
		players.addAll(gameContext.getTable().getPlayerInfos().values());
		this.setPlayers(players);
		if (gameContext.getTable().getFanhui() != 0) {
		Tile tile = Tile.getHuiPai(gameContext.getTable().getFanhui());
		this.setHui1(tile.getPai()[0]);
		this.setHui2(tile.getPai()[1]);
		}
	}

	// 创建房间的时候调用此构造函数，其余请调用上一个构造。此函数不会隐藏其他玩家信息。
	public RoomRespModel(String weixinId, RoomContext roomContex) {
		
		if(roomContex!=null){
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
			if (gameContext.getTable().getFanhui() != 0) {
				Tile tile = Tile.getHuiPai(gameContext.getTable().getFanhui());
				this.setHui1(tile.getPai()[0]);
				this.setHui2(tile.getPai()[1]);
			}
		}
		
		
	}
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
