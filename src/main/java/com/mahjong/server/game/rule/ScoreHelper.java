package com.mahjong.server.game.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mahjong.server.game.object.GetScoreType;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.win.StandardHuType;

public class ScoreHelper {

	private static int getBaseScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang,List<GetScoreType> typeScore){
		int baseScore = 1;// basescore 底分为1分，庄家底分2分
		if (isZhuang) {
			baseScore = 2;
		}
		//胡牌类型基本分
		int huTypeFanScore=winInfo.getHuType().getScoreFan();
		baseScore*=huTypeFanScore;
		
		if(winInfo.getHuType().equals(StandardHuType.NORMAL_HU)){
			typeScore.add(GetScoreType.nor_hu);
		}else if(winInfo.getHuType().equals(StandardHuType.XIAO_HU)){
			typeScore.add(GetScoreType.xiaohu);
		}else if(winInfo.getHuType().equals(StandardHuType.ZHA_QIANG_HU)){
			typeScore.add(GetScoreType.zhaqiang);
		}else if(winInfo.getHuType().equals(StandardHuType.QIDUI_HU)){
			typeScore.add(GetScoreType.qidui);
		}else if(winInfo.getHuType().equals(StandardHuType.QING_Yi_SE_HU)){
			typeScore.add(GetScoreType.qingyise);
		}else if(winInfo.getHuType().equals(StandardHuType.QI_YI_SE_QI_DUI_HU)){
			typeScore.add(GetScoreType.qingyise_qidui);
		}
		//飘胡判断
		boolean isPiao=winInfo.getHuType().getBaseWinType().isPiaoHU(winInfo);
		if(isPiao){
			baseScore*=4;
			typeScore.add(GetScoreType.piaohu);
		}
		//穷胡判断
		boolean maybeQiong = winInfo.getHuType().getBaseWinType().maybeQiongHu(winInfo);
		if (maybeQiong) {
			baseScore *= 4;
			typeScore.add(GetScoreType.qionghu);
		}
		return baseScore;
	}
	private static int getSpecialTileScore(WinInfo winInfo,boolean isWinner,List<GetScoreType>  typeScore){
		int tileScore = 0;
		//穷和会判断
	    Set<Byte> set = Tile.tile2Set(winInfo.getHuiTile());
		for (byte pai : winInfo.getWinTile().getPai()) {
			if (set.contains(pai)) {// 每个会牌加一分，不论胡不胡牌 结算都加分
				tileScore += 1;
				typeScore.add(GetScoreType.huipai);
			}
			if (pai == Tile.QIANG&&isWinner) {// 每个枪牌加一分，不胡不算
				tileScore += 1;
				typeScore.add(GetScoreType.qiangpai);
			}
		}
		return tileScore;
	}

	public static int getTileGroupScore(List<TileGroup> tileGroups, RuleInfo ruleInfo,List<GetScoreType>  typeScore) {
		int result = 0;
		for (TileGroup tileGroup : tileGroups) {
			if (ruleInfo.getPlayRules().contains(PlayRule.GANG)) {
				if (tileGroup.getType() == TileGroupType.ANGANG_GROUP) {
					result += 12;// 暗杠：每家4分 不论胡不胡结算都加分
					typeScore.add(GetScoreType.angang);
				} else if (tileGroup.getType() == TileGroupType.BUGANG_GROUP) {
					result += 6;// 明杠：每家2分 不论胡不胡结算都加分
					typeScore.add(GetScoreType.bugang);
				} else if (tileGroup.getType() == TileGroupType.XUAN_FENG_GANG_DNXB_GROUP) {
					result += 12;// 旋风杠：东南西北每家4分 中发白每家2分。不论胡不胡牌 结算都加分。
					typeScore.add(GetScoreType.xuanfenggang);
				} else if (tileGroup.getType() == TileGroupType.XUAN_FENG_GANG_ZFB_GROUP) {
					result += 6;// 旋风杠：东南西北每家4分 中发白每家2分。不论胡不胡牌 结算都加分。
					typeScore.add(GetScoreType.xuanfenggang);
				}
			}

		}
		return result;
	}
	private static int getTotalScore(int baseScore, WinInfo winInfo, RuleInfo ruleInfo,boolean isWinner,List<GetScoreType>  typeScore) {
		int specialTileScore=getSpecialTileScore(winInfo,isWinner,typeScore);
		int totalScore = baseScore+specialTileScore;
		return totalScore;
	}
		
	public static int getWinerScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang,List<GetScoreType> typeScore) {
		int baseScore=getBaseScore(winInfo, ruleInfo, isZhuang,typeScore);
		if (winInfo.isZiMo()) {//这个需要在所有base项目统计完毕之后计算。表示其他向其他3个玩家收分。
			typeScore.add(GetScoreType.zimo);
			baseScore *= 6;
		}
		return getTotalScore(baseScore,winInfo,ruleInfo,true,typeScore);
	}
	public static int getPaoerScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang,List<GetScoreType> typeScore) {
		int baseScore=getBaseScore(winInfo, ruleInfo, isZhuang,new ArrayList<GetScoreType>())*(-1);
		if(isZhuang){
			baseScore*=2;
		}
		
		int rest =  getTotalScore(baseScore,winInfo,ruleInfo,false,typeScore);
		
		typeScore = new ArrayList<>();
		typeScore.add(GetScoreType.dianpao);
		
		return rest;
		
	}

	public static int getXianScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang,List<GetScoreType> typeScore) {
		int baseScore=getBaseScore(winInfo, ruleInfo, isZhuang,new ArrayList<GetScoreType>())*(-1);
		int rest = getTotalScore(baseScore,winInfo,ruleInfo,false,typeScore);
		typeScore = new ArrayList<>();
		return rest;
		
	}
}