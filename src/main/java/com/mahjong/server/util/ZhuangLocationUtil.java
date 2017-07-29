package com.mahjong.server.util;

import com.mahjong.server.game.enums.PlayerLocation;

/**
 * 玩家位置。
 * 
 * @author blovemaple
 */
public class ZhuangLocationUtil {
	
	public static PlayerLocation getNextZhuangLocation(PlayerLocation nowZhuang){
		
		if(nowZhuang==null){
			return PlayerLocation.EAST; 
		}
		
		if(nowZhuang.getCode()==PlayerLocation.EAST.getCode()){
			return PlayerLocation.SOUTH;
		}else if(nowZhuang.getCode()==PlayerLocation.SOUTH.getCode()){
			return PlayerLocation.WEST;
		}else if(nowZhuang.getCode()==PlayerLocation.WEST.getCode()){
			return PlayerLocation.NORTH;
		}else if(nowZhuang.getCode()==PlayerLocation.NORTH.getCode()){
			return PlayerLocation.EAST;
		}else{
			return PlayerLocation.EAST; 
		}
		
		
	}
	
	
	
}