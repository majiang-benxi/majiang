package com.mahjong.server.game.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.rule.RuleInfo;
import com.mahjong.server.game.rule.ScoreHelper;


/**
 * 一局游戏的结果。winnerLocation为null表示流局。
 * 
 * @author warter
 */
public class GameResult implements Serializable {
	private static final long serialVersionUID = 5927092445320750860L;

	private final Map<PlayerLocation, PlayerInfo> playerInfos;
	private final PlayerLocation zhuangLocation;
	private final RuleInfo ruleInfo;

	private PlayerLocation winnerLocation;
	private Tile winTile;
	private PlayerLocation paoerLocation;
	private WinInfo winInfo;
	public GameResult(Map<PlayerLocation, PlayerInfo> playerInfos,
			PlayerLocation zhuangLocation, RuleInfo ruleInfo) {
		this.playerInfos = playerInfos;
		this.zhuangLocation = zhuangLocation;
		this.ruleInfo = ruleInfo;
	}

	public PlayerLocation getWinnerLocation() {
		return winnerLocation;
	}

	public RuleInfo getRuleInfo() {
		return ruleInfo;
	}

	public WinInfo getWinInfo() {
		return winInfo;
	}

	public void setWinInfo(WinInfo winInfo) {
		this.winInfo = winInfo;
	}

	public void setWinnerLocation(PlayerLocation winnerLocation) {
		this.winnerLocation = winnerLocation;
	}

	public Tile getWinTile() {
		return winTile;
	}

	public void setWinTile(Tile winTile) {
		this.winTile = winTile;
	}

	public PlayerLocation getPaoerLocation() {
		return paoerLocation;
	}

	public void setPaoerLocation(PlayerLocation paoerLocation) {
		this.paoerLocation = paoerLocation;
	}


	public Map<PlayerLocation, PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}

	public PlayerLocation getZhuangLocation() {
		return zhuangLocation;
	}

	public void caclulateScore(boolean isZimo) {
		
		for ( Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
			boolean isZhuang=entry.getKey().getCode()==zhuangLocation.getCode();
			
			List<GetScoreType> typeScore = new ArrayList<GetScoreType>();
			
			if(entry.getKey()==winnerLocation){
				//用来获取的分项
				ScoreHelper.getWinerScore(winInfo, entry.getValue().getTileGroups(), ruleInfo, isZhuang,typeScore);
				
			}else if(entry.getKey()==paoerLocation){
				
				ScoreHelper.getPaoerScore(entry.getValue().getTileGroups(), ruleInfo, isZhuang,typeScore);
			} else {
				
				ScoreHelper.getXianScore(entry.getValue().getTileGroups(), ruleInfo, isZhuang,typeScore);
			}
			
			entry.getValue().setGatherScoreTypes(typeScore);
		}
		
		
		ScoreHelper.computeUserScore(playerInfos, zhuangLocation, winnerLocation, paoerLocation, ruleInfo, winInfo, isZimo);
	}

}
