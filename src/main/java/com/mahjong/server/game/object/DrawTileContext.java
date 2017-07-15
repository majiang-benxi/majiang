package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.mahjong.server.game.action.Action;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.enums.PlayerLocation;

public class DrawTileContext {
	private List<Action> canDoActions;// 存储可以操作动作情况
	private boolean canwin;//是否可以赢判断，方便快速判断
	private boolean canxfg;//是否可以旋风杠判断，方便快速判断
	private PlayerLocation playerLocation;
	private boolean isFirstDrawTile=true;
	public DrawTileContext(List<Action> canDoActions, boolean canwin, boolean canxfg, PlayerLocation playerLocation,boolean isFirstDrawTile) {
		super();
		this.canDoActions = canDoActions;
		this.canwin = canwin;
		this.canxfg = canxfg;
		this.playerLocation = playerLocation;
		this.isFirstDrawTile=isFirstDrawTile;
	}
	public DrawTileContext(PlayerLocation playerLocation){
		super();
		this.canDoActions = new ArrayList<Action>();
		this.canwin = false;
		this.canxfg = false;
		this.playerLocation = playerLocation;
		this.isFirstDrawTile=true;
	}
	public boolean containsAction(ActionType  actionType ){
		for (Action action : canDoActions) {
			if(action.getType()==actionType){
				return true;
			}
		}
		return false;
	}
	public void resetForNextUse(PlayerLocation playerLocation){
		this.canDoActions=new ArrayList<Action>();
		this.canwin = false;
		this.canxfg = false;
		this.playerLocation = playerLocation;
		this.isFirstDrawTile=false;
	}
	public PlayerLocation getPlayerLocation() {
		return playerLocation;
	}
	public void setPlayerLocation(PlayerLocation playerLocation) {
		this.playerLocation = playerLocation;
	}
	public List<Action> getCanDoActions() {
		return canDoActions;
	}
	public void setCanDoActions(List<Action> canDoActions) {
		this.canDoActions = canDoActions;
	}
	public boolean isCanwin() {
		return canwin;
	}
	public void setCanwin(boolean canwin) {
		this.canwin = canwin;
	}
	public boolean isCanxfg() {
		return canxfg;
	}
	public void setCanxfg(boolean canxfg) {
		this.canxfg = canxfg;
	}
	public boolean isFirstDrawTile() {
		return isFirstDrawTile;
	}
	public void setFirstDrawTile(boolean isFirstDrawTile) {
		this.isFirstDrawTile = isFirstDrawTile;
	}
}
