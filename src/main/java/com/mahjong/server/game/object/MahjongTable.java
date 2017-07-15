package com.mahjong.server.game.object;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import com.mahjong.server.game.enums.PlayerLocation;

/**
 * 麻将桌。
 * 
 */
public class MahjongTable {
	/**
	 * 牌墙。从0处摸牌。
	 */
	private Tile tileWall;

	/**
	 * 所有玩家信息。
	 */
	private Map<PlayerLocation, PlayerInfo> playerInfos;
	private byte fanhui;
	private AtomicInteger  remainderTileNum=new AtomicInteger(136);
	public void init() {
		tileWall = new Tile(Tile.getOneBoxMahjong());
		playerInfos = new EnumMap<PlayerLocation, PlayerInfo>(PlayerLocation.class);
		for (PlayerLocation location : PlayerLocation.values()) {
			playerInfos.put(location, new PlayerInfo());
		}
	}

	/**
	 * 初始化，准备开始一局。即清空玩家的牌、洗牌
	 */
	public void readyForGame() {
		for (PlayerInfo playerInfo : playerInfos.values()) {
			playerInfo.clear();
		}
		tileWall.setPai(Tile.getOneBoxMahjong());
		Collections.shuffle(Arrays.asList(tileWall.getPai()));
	}

	/**
	 * 从牌墙的头部摸指定数量的牌并返回。
	 */
	public Tile draw(int count) {
		if (count <= 0 || count > tileWall.getPai().length) {
			return null;
		}
		return tileWall.dealTile(count);
	}

	/**
	 * 从牌墙的底部摸指定数量的牌并返回。
	 */
	public Tile drawBottom(int count) {
		if (count <= 0 || count > tileWall.getPai().length) {
			return null;
		}
		return tileWall.dealBottomTile(count);
	}

	/**
	 * 返回牌中的剩余牌数。
	 */
	public int getTileWallSize() {
		return tileWall.getPai().length;
	}



	public Map<PlayerLocation, PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}

	public void setPlayerInfos(Map<PlayerLocation, PlayerInfo> playerInfos) {
		this.playerInfos = playerInfos;
	}
	
	public boolean removePlayerInfos(String weixinId) {
		
		for(PlayerInfo playerInfo : playerInfos.values()){
			if(playerInfo!=null && playerInfo.getUserInfo()!=null && playerInfo.getUserInfo().getWeixinMark().equals(weixinId)){
				playerInfos.put(PlayerLocation.fromCode(playerInfo.getUserLocation()) ,  new PlayerInfo());
			}
		}
		
		return true;
	}
	
	public PlayerInfo getPlayerInfosByWeixinId(String weixinId) {
		
		for(PlayerInfo playerInfo : playerInfos.values()){
			if(playerInfo!=null && playerInfo.getUserInfo()!=null ){
				if(playerInfo.getUserInfo().getWeixinMark().equals(weixinId)){
					return playerInfo;
				}
			}
			
		}
		return null;
	}

	public PlayerInfo getPlayerByLocation(PlayerLocation location) {
		PlayerInfo info = playerInfos.get(location);
		return info == null ? null : info;
	}

	public void setPlayer(PlayerLocation location, PlayerInfo player) {
		playerInfos.put(location, player);
	}

	public Tile getTileWall() {
		return tileWall;
	}

	public void setTileWall(Tile tileWall) {
		this.tileWall = tileWall;
	}

	public byte getFanhui() {
		return fanhui;
	}

	public void setFanhui(byte fanhui) {
		this.fanhui = fanhui;
	}
	public void resetPlayersLastDiscardTile(PlayerLocation excludePlayerLocation){
		_resetPlayersLastDiscardTile(excludePlayerLocation);
		_resetPlayersLastDrawTile(null);
	}
	private void _resetPlayersLastDiscardTile(PlayerLocation excludePlayerLocation){
		for (PlayerInfo playerInfo : playerInfos.values()) {
			if(excludePlayerLocation!=null&&playerInfo.getUserLocation()==excludePlayerLocation.getCode()){
				continue;
			}else{
				playerInfo.resetDiscardTile();
			}
		}
	}
	public void resetPlayersLastDrawTile(PlayerLocation excludePlayerLocation){
		_resetPlayersLastDrawTile(excludePlayerLocation);
		_resetPlayersLastDiscardTile(null);
	}
	private void _resetPlayersLastDrawTile(PlayerLocation excludePlayerLocation){
		for (PlayerInfo playerInfo : playerInfos.values()) {
			if(excludePlayerLocation!=null&&playerInfo.getUserLocation()==excludePlayerLocation.getCode()){
				continue;
			}else{
				playerInfo.resetLastDrawTile();
			}
		}
	}
	public void resetPlayersLastTileGroupAction(){
		for (PlayerInfo playerInfo : playerInfos.values()) {
			playerInfo.resetLastTileGroupAction();
		}
	}
	
	public AtomicInteger getRemainderTileNum() {
		return remainderTileNum;
	}

	public void setRemainderTileNum(AtomicInteger remainderTileNum) {
		this.remainderTileNum = remainderTileNum;
	}

	public void printAllPlayTiles() {
		try {
		for (Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
				System.out.print(
					"方位:" + entry.getKey() +"用户weixinId="+entry.getValue().getUserInfo().getWeixinMark()+ " aliveTile: ");
				entry.getValue()._getSortAliveTiles().printTile();
		}
			System.out.print("会牌:");
			Tile.getHuiPai(fanhui).printTile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
