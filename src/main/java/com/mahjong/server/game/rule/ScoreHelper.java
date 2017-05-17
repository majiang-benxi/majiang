package com.mahjong.server.game.rule;

import java.util.List;
import java.util.Set;

import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.game.object.WinInfo;

public class ScoreHelper {

	private static int getBaseScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang){
		int baseScore = 1;// basescore 底分为1分，庄家底分2分
		if (isZhuang) {
			baseScore = 2;
		}
		//胡牌类型基本分
		int huTypeFanScore=winInfo.getHuType().getScoreFan();
		baseScore*=huTypeFanScore;
		//飘胡判断
		boolean isPiao=winInfo.getHuType().getBaseWinType().isPiaoHU(winInfo);
		if(isPiao){
			baseScore*=2;
		}
		//穷胡判断
		boolean maybeQiong = winInfo.getHuType().getBaseWinType().maybeQiongHu(winInfo);
		if (maybeQiong) {
			baseScore *= 4;
		}
		return baseScore;
	}
	private static int getSpecialTileScore(WinInfo winInfo,boolean isWinner){
		int tileScore = 0;
		//穷和会判断
	    Set<Byte> set = Tile.tile2Set(winInfo.getHuiTile());
		for (byte pai : winInfo.getWinTile().getPai()) {
			if (set.contains(pai)) {// 每个会牌加一分，不论胡不胡牌 结算都加分
				tileScore += 1;
			}
			if (pai == Tile.QIANG&&isWinner) {// 每个枪牌加一分，不胡不算
				tileScore += 1;
			}
		}
		return tileScore;
	}

	private static int getTileGroupScore(List<TileGroup> tileGroups, RuleInfo ruleInfo) {
		int result = 0;
		for (TileGroup tileGroup : tileGroups) {
			if (ruleInfo.getPlayRules().contains(PlayRule.GANG)) {
				if (tileGroup.getType() == TileGroupType.ANGANG_GROUP) {
					result += 12;// 暗杠：每家4分 不论胡不胡结算都加分
				} else if (tileGroup.getType() == TileGroupType.BUGANG_GROUP) {
					result += 6;// 明杠：每家2分 不论胡不胡结算都加分
				} else if (tileGroup.getType() == TileGroupType.XUAN_FENG_GANG_DNXB_GROUP) {
					result += 12;// 旋风杠：东南西北每家4分 中发白每家2分。不论胡不胡牌 结算都加分。
				} else if (tileGroup.getType() == TileGroupType.XUAN_FENG_GANG_ZFB_GROUP) {
					result += 6;// 旋风杠：东南西北每家4分 中发白每家2分。不论胡不胡牌 结算都加分。
				}
			}

		}
		return 0;
	}
	private static int getTotalScore(int baseScore, WinInfo winInfo, RuleInfo ruleInfo,boolean isWinner) {
		int tileGroupScore = getTileGroupScore(winInfo.getTileGroups(), ruleInfo);
		int specialTileScore=getSpecialTileScore(winInfo,isWinner);
		int totalScore = baseScore+specialTileScore+tileGroupScore;
		return totalScore;
	}
		
	public static int getWinerScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang) {
		int totalScore = 0;
		int baseScore=getBaseScore(winInfo, ruleInfo, isZhuang);
		if (winInfo.isZiMo()) {//这个需要在所有base项目统计完毕之后计算。表示其他向其他3个玩家收分。
			baseScore *= 3;
		}
		return getTotalScore(baseScore,winInfo,ruleInfo,true);
	}
	public static int getPaoerScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang) {
		int baseScore=getBaseScore(winInfo, ruleInfo, isZhuang)*(-1);
		if(isZhuang){
			baseScore*=2;
		}
		return getTotalScore(baseScore,winInfo,ruleInfo,false);
	}

	public static int getXianScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang) {
		int baseScore=getBaseScore(winInfo, ruleInfo, isZhuang)*(-1);
		return getTotalScore(baseScore,winInfo,ruleInfo,false);
	}
}