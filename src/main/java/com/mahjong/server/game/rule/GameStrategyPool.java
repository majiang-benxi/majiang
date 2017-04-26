package com.mahjong.server.game.rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mahjong.server.game.GameContext;
import com.mahjong.server.game.action.ActionAndLocation;
import com.mahjong.server.game.action.ActionType;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.PlayerLocation;

public class GameStrategyPool {
	
	private static String[] ruleS = new String[]{"11111111","222"};
	
	public static Map<String,GameStrategy> gameStrategyPool = new HashMap<String,GameStrategy>();
	static{
		
		for(String rule : ruleS){
			
			switch(rule){
			
			case "1": 
				gameStrategyPool.put(rule, new GameStrategy(){

					@Override
					public Set<? extends ActionType> getAllActionTypesInGame() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public ActionAndLocation getActionUserCanChoises(Map<PlayerLocation, PlayerInfo> playersInfos) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					protected PlayerLocation nextZhuangLocation(GameContext context) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public byte[] checkUserIsWin() {
						// TODO Auto-generated method stub
						return null;
					}});
				break;

			case "2": 
				gameStrategyPool.put(rule, new GameStrategy(){

					@Override
					public byte[] checkUserIsWin() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Set<? extends ActionType> getAllActionTypesInGame() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public ActionAndLocation getActionUserCanChoises(Map<PlayerLocation, PlayerInfo> playersInfos) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					protected PlayerLocation nextZhuangLocation(GameContext context) {
						// TODO Auto-generated method stub
						return null;
					}});
				break;
			case "3": 
				gameStrategyPool.put(rule, new GameStrategy(){

					@Override
					public byte[] checkUserIsWin() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Set<? extends ActionType> getAllActionTypesInGame() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public ActionAndLocation getActionUserCanChoises(Map<PlayerLocation, PlayerInfo> playersInfos) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					protected PlayerLocation nextZhuangLocation(GameContext context) {
						// TODO Auto-generated method stub
						return null;
					}});
				break;
			
			}
			
		}
		
		
	}

}
