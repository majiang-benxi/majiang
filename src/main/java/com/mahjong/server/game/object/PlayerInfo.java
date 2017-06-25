package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.List;

import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.game.enums.PlayerLocation;

/**
 * 麻将桌上一个玩家的信息，包括玩家对象、牌，以及其他信息。
 * @author warter
 */
public class PlayerInfo extends PlayerTiles implements Cloneable {
	/**
	 * 玩家。
	 */
	private UserInfo userInfo;
	
	private Integer userRoomRecordInfoID;
	
	private PlayerLocation userLocation;

	/**
	 * 最后摸的牌。
	 */
	private Tile lastDrawedTile = new Tile();
	/**
	 * 已经打出的牌。
	 */
	private Tile discardedTiles = new Tile();
	/**
	 * 最后打的一张牌
	 */
	private Tile lastDiscardTile= new Tile();//上一个玩家打的牌
	private int lastTileGroupAction=0;//当前执行的动作,执行通知结束之后会清空

	/**
	 * 是否胡牌。
	 */
	private boolean isHu = false;
	
	private int curScore;// 分数
	private boolean zhuang=false;
	private boolean offline=false;
	private boolean discardAuth=false;//默认都没有打牌权限，只有吃碰杠和摸牌之后才可以
	
	List<GetScoreType> gatherScoreTypes = new ArrayList<GetScoreType>();
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Tile getLastDrawedTile() {
		return lastDrawedTile;
	}

	public void setLastDrawedTile(Tile lastDrawedTile) {
		this.lastDrawedTile = lastDrawedTile;
	}


	public Tile getDiscardedTiles() {
		return discardedTiles;
	}

	public void setDiscardedTiles(Tile discardedTiles) {
		this.discardedTiles = discardedTiles;
	}

	public boolean isHu() {
		return isHu;
	}

	public void setHu(boolean isHu) {
		this.isHu = isHu;
	}

	public void setUserLocation(PlayerLocation userLocation) {
		this.userLocation = userLocation;
	}

	/**
	 * 清空玩家的牌，回到初始状态。
	 */
	public void clear() {
		aliveTiles.setPai(null);
		lastDrawedTile = null;
		discardedTiles.setPai(null);
		tileGroups.clear();
		isHu = false;
	}

	public PlayerInfo clone() {
		PlayerInfo c;
		try {
			c = (PlayerInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			// 不可能，因为PlayerInfo已经实现了Cloneable
			throw new RuntimeException(e);
		}
		c.aliveTiles = this.aliveTiles == null ? new Tile() : this.aliveTiles.clone();
		c.discardedTiles = this.discardedTiles == null ? new Tile() : this.discardedTiles.clone();
		c.userInfo = userInfo == null ? null : userInfo.clone();
		c.tileGroups = new ArrayList<TileGroup>(tileGroups);
		c.lastDiscardTile=this.lastDiscardTile == null ? new Tile() : this.lastDiscardTile.clone();
		c.lastTileGroupAction=this.lastTileGroupAction;
		return c;
	}

	public int getCurScore() {
		return curScore;
	}

	public void setCurScore(int curScore) {
		this.curScore = curScore;
	}

	public Integer getUserLocation() {
		return userLocation != null ? userLocation.getCode() : null;
	}

	public void setUserLocation(int userLocation) {
		this.userLocation = PlayerLocation.fromCode(userLocation);
	}


	public boolean isZhuang() {
		return zhuang;
	}

	public void setZhuang(boolean zhuang) {
		this.zhuang = zhuang;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public Tile getLastDiscardTile() {
		return lastDiscardTile;
	}

	public void setLastDiscardTile(Tile lastDiscardTile) {
		this.lastDiscardTile = lastDiscardTile;
	}

	/**
	 * 获取其他玩家的视图。不要以get开头
	 */
	public PlayerInfo _getOtherPlayerInfoView() {
		PlayerInfo playerInfo = this.clone();
		if (playerInfo.getAliveTiles() != null) {
		playerInfo.setAliveTiles(
				playerInfo.getAliveTiles().getOtherPlayerTileView(playerInfo.getAliveTiles().getPai().length));// 活牌不可见

		}
		if (playerInfo.getLastDrawedTile() != null) {
			playerInfo.setLastDrawedTile(
				playerInfo.getLastDrawedTile().getOtherPlayerTileView(playerInfo.getLastDrawedTile().getPai().length));// 最后摸的牌不可见

		}
		return playerInfo;
	}
	
	public boolean isDiscardAuth() {
		return discardAuth;
	}

	public void setDiscardAuth(boolean discardAuth) {
		this.discardAuth = discardAuth;
	}
	public void resetDiscardTile() {
		this.lastDiscardTile=new Tile();		
	}

	public int getLastTileGroupAction() {
		return lastTileGroupAction;
	}

	public void setLastTileGroupAction(int lastTileGroupAction) {
		this.lastTileGroupAction = lastTileGroupAction;
	}

	public void resetLastDrawTile() {
		this.lastDrawedTile=new Tile();		
	}
	
	public void resetLastTileGroupAction(){
		this.lastTileGroupAction=0;
	}
	
	public static void main(String[] args) {
		PlayerInfo playerInfo = new PlayerInfo();
		playerInfo.clone();
	}

	public Integer getUserRoomRecordInfoID() {
		return userRoomRecordInfoID;
	}

	public void setUserRoomRecordInfoID(Integer userRoomRecordInfo) {
		this.userRoomRecordInfoID = userRoomRecordInfo;
	}

	public List<GetScoreType> getGatherScoreTypes() {
		return gatherScoreTypes;
	}

	public void setGatherScoreTypes(List<GetScoreType> gatherScoreTypes) {
		this.gatherScoreTypes = gatherScoreTypes;
	}
	
}
