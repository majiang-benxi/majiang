package com.mahjong.server.game.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.rule.PlayRule;
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

	public void caclulateScore() {
		
		boolean dianpaobaosanjia = ruleInfo.getPlayRules().contains(PlayRule.PAO_PAY_THREE);
		
		int winscore = 0;
		int dianpaoscore = 0;
		
		for ( Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
			boolean isZhuang=entry.getKey().getCode()==zhuangLocation.getCode();
			
			List<GetScoreType> typeScore = new ArrayList<GetScoreType>();
			
			int score = 0;
			if(entry.getKey()==winnerLocation){
				
				//用来获取的分项
				ScoreHelper.getWinerScore(winInfo, ruleInfo, isZhuang,typeScore);
				
			}else if(entry.getKey()==paoerLocation){
				
				score = ScoreHelper.getPaoerScore(winInfo, ruleInfo, isZhuang,typeScore);
				
			} else {
				score = ScoreHelper.getXianScore(winInfo, ruleInfo, isZhuang,typeScore);
			}
			
			
			if(entry.getKey() != winnerLocation){
				
				//赢家分数是其他几家之和
				winscore += score;
				
				if(dianpaobaosanjia){//点炮宝三家，点炮者分数是三家之和
					dianpaoscore += score;
				}else{
					//不是点炮宝三家，点炮者分数是自己的
					if(entry.getKey()==paoerLocation){
						dianpaoscore += score;
					}
				}
			}
			
			//赢家分数，核电跑分数后续计算
			if(entry.getKey()!=paoerLocation && entry.getKey()!=winnerLocation){
				entry.getValue().setCurScore(entry.getValue().getCurScore() + score);
			}
			
			entry.getValue().setGatherScoreTypes(typeScore);
		}
		
		for ( Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
			
			if(entry.getKey()!=paoerLocation && entry.getKey()!=winnerLocation && dianpaobaosanjia){
				entry.getValue().setCurScore(1000);
				continue;
			}
			
			//赢家分数是其他几家之和的正数
			if(entry.getKey()==winnerLocation){
				entry.getValue().setCurScore(entry.getValue().getCurScore() - winscore);
			}
			
			//点炮者分数计算
			if(entry.getKey()==paoerLocation){
				entry.getValue().setCurScore(entry.getValue().getCurScore() + dianpaoscore);
			}
			
		}
		
		
	}

}
