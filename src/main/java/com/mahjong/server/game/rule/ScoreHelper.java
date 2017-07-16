package com.mahjong.server.game.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.GetScoreType;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.win.StandardHuType;
import com.mahjong.server.netty.handler.HandlerHelper;

public class ScoreHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(ScoreHelper.class);

	private static int getBaseScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang,
			List<GetScoreType> typeScore) {
		int baseScore = 1;// basescore 底分为1分，庄家底分2分
		if (isZhuang) {
			baseScore = 2;
		}
		// 胡牌类型基本分
		int huTypeFanScore = winInfo.getHuType().getScoreFan();
		baseScore *= huTypeFanScore;

		if (winInfo.getHuType().equals(StandardHuType.NORMAL_HU)) {
			typeScore.add(GetScoreType.nor_hu);
		} else if (winInfo.getHuType().equals(StandardHuType.XIAO_HU)) {
			typeScore.add(GetScoreType.xiaohu);
		} else if (winInfo.getHuType().equals(StandardHuType.ZHA_QIANG_HU)) {
			typeScore.add(GetScoreType.zhaqiang);
		} else if (winInfo.getHuType().equals(StandardHuType.QIDUI_HU)) {
			typeScore.add(GetScoreType.qidui);
		} else if (winInfo.getHuType().equals(StandardHuType.QING_Yi_SE_HU)) {
			typeScore.add(GetScoreType.qingyise);
		} else if (winInfo.getHuType().equals(StandardHuType.QI_YI_SE_QI_DUI_HU)) {
			typeScore.add(GetScoreType.qingyise_qidui);
		}
		// 飘胡判断
		if (ruleInfo.getPlayRules().contains(PlayRule.PIAO_HU)) {
			boolean isPiao = winInfo.getHuType().getBaseWinType().isPiaoHU(winInfo);
			if (isPiao) {
				baseScore *= 4;
				typeScore.add(GetScoreType.piaohu);
			}
		}

		if (ruleInfo.getPlayRules().contains(PlayRule.QIONGHU)) {
			// 穷胡判断
			boolean maybeQiong = winInfo.getHuType().getBaseWinType().maybeQiongHu(winInfo);
			if (maybeQiong) {
				baseScore *= 4;
				typeScore.add(GetScoreType.qionghu);
			}
		}
		return baseScore;
	}

	private static int getSpecialTileScore(WinInfo winInfo, boolean isWinner, List<GetScoreType> typeScore) {
		int tileScore = 0;
		// 穷和会判断
		Set<Byte> set = Tile.tile2Set(winInfo.getHuiTile());
		for (byte pai : winInfo.getWinTile().getPai()) {
			if (set.contains(pai)) {// 每个会牌加一分，不论胡不胡牌 结算都加分
				tileScore += 1;
				typeScore.add(GetScoreType.huipai);
			}
			if (pai == Tile.QIANG && isWinner) {// 每个枪牌加一分，不胡不算
				tileScore += 1;
				typeScore.add(GetScoreType.qiangpai);
			}
		}
		return tileScore;
	}

	public static int getTileGroupScore(List<TileGroup> tileGroups, RuleInfo ruleInfo, List<GetScoreType> typeScore) {
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

	private static int getTotalScore(int baseScore, WinInfo winInfo, RuleInfo ruleInfo, boolean isWinner,
			List<GetScoreType> typeScore) {
		int specialTileScore = getSpecialTileScore(winInfo, isWinner, typeScore);
		int totalScore = baseScore + specialTileScore;
		return totalScore;
	}

	public static int getWinerScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang,
			List<GetScoreType> typeScore) {
		int baseScore = getBaseScore(winInfo, ruleInfo, isZhuang, typeScore);
		if (winInfo.isZiMo()) {// 这个需要在所有base项目统计完毕之后计算。表示其他向其他3个玩家收分。
			typeScore.add(GetScoreType.zimo);
			baseScore *= 6;
		}
		return getTotalScore(baseScore, winInfo, ruleInfo, true, typeScore);
	}

	public static int getPaoerScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang,
			List<GetScoreType> typeScore) {
		int baseScore = getBaseScore(winInfo, ruleInfo, isZhuang, new ArrayList<GetScoreType>()) * (-1);
		if (isZhuang) {
			baseScore *= 2;
		}

		int rest = getTotalScore(baseScore, winInfo, ruleInfo, false, typeScore);

		typeScore = new ArrayList<GetScoreType>();
		typeScore.add(GetScoreType.dianpao);

		return rest;

	}

	public static int getXianScore(WinInfo winInfo, RuleInfo ruleInfo, boolean isZhuang, List<GetScoreType> typeScore) {
		int baseScore = getBaseScore(winInfo, ruleInfo, isZhuang, new ArrayList<GetScoreType>()) * (-1);
		int rest = getTotalScore(baseScore, winInfo, ruleInfo, false, typeScore);
		typeScore = new ArrayList<GetScoreType>();
		return rest;

	}

	public static void computeUserScore(Map<PlayerLocation, PlayerInfo> playerInfos, PlayerLocation zhuangLocation,
			PlayerLocation winnerLocation, PlayerLocation paoerLocation, RuleInfo ruleInfo, WinInfo winInfo,
			boolean isZimo) {
		
		logger.info("computeUserScore**********"+zhuangLocation.name()+":"+winnerLocation.name()+":"+paoerLocation+":"+isZimo);

		Set<Byte> huiset = Tile.tile2Set(winInfo.getHuiTile());
		boolean dianpaobaosanjia = ruleInfo.getPlayRules().contains(PlayRule.PAO_PAY_THREE);
		
		logger.info("computeUserScore***********"+dianpaobaosanjia);

		int huiscore = 0;
		int qiangScore = 0;

		int zhuangNeedGiveScore = 0;
		int xianNeedGiveScore = 0;

		int paoNeedGiveScore = 0;

		if (winnerLocation != null) {

			// 计算会拍、抢拍
			for (byte pai : winInfo.getWinTile().getPai()) {
				if (huiset.contains(pai)) {// 会牌：每一张会牌加1分，不胡不算。
					huiscore += 1;
				}
				if (pai == Tile.QIANG) {
					if (huiset.contains(pai)) {// 如枪牌为会时。胡牌时一个枪加2分，不胡不算。
						qiangScore += 2;
					} else {// 枪牌：胡牌时手中每个枪加一分，不胡不算。
						qiangScore += 1;
					}
				}
			}
			
			
			logger.info("computeUserScore***********"+huiscore+":"+qiangScore);

			// 赢家是庄
			if (winnerLocation.getCode() == zhuangLocation.getCode()) {
				if (isZimo) {// 庄家自摸：每家4分。
					xianNeedGiveScore = 4;
				} else {// 闲家给庄家点炮：点炮者输4分，其余两门各输2分。
					xianNeedGiveScore = 2;
					paoNeedGiveScore = 4;
				}
			} else {
				if (isZimo) {// 闲家自摸：庄家输4分，其余两门各输2分。
					zhuangNeedGiveScore = 4;
					xianNeedGiveScore = 2;
				} else {
					if (paoerLocation.getCode() == zhuangLocation.getCode()) {// 庄家给闲家点炮：庄家输4分，其余两门各输1分
						zhuangNeedGiveScore = 4;
						xianNeedGiveScore = 1;
					} else {// 闲家给闲家点炮：庄家输2分，点炮家输2分，剩一家输1分
						zhuangNeedGiveScore = 2;
						paoNeedGiveScore = 2;
						xianNeedGiveScore = 1;
					}
				}
			}
			
			
			logger.info("computeUserScore***********"+zhuangNeedGiveScore+":"+paoNeedGiveScore+":"+xianNeedGiveScore);

			int ratio = winInfo.getHuType().getScoreFan()==1?0:winInfo.getHuType().getScoreFan();

			// 飘胡判断
			if (ruleInfo.getPlayRules().contains(PlayRule.PIAO_HU)) {
				boolean isPiao = winInfo.getHuType().getBaseWinType().isPiaoHU(winInfo);
				if (isPiao) {
					ratio += 4;
				}
			}

			if (ruleInfo.getPlayRules().contains(PlayRule.QIONGHU)) {
				// 穷胡判断
				boolean maybeQiong = winInfo.getHuType().getBaseWinType().maybeQiongHu(winInfo);
				if (maybeQiong) {
					ratio += 4;
				}
			}
			
			logger.info("computeUserScore***********"+ratio);
			

			zhuangNeedGiveScore *= ratio;
			xianNeedGiveScore *= ratio;
			paoNeedGiveScore *= ratio;
			
			logger.info("computeUserScore***********"+zhuangNeedGiveScore+":"+paoNeedGiveScore+":"+xianNeedGiveScore);

			zhuangNeedGiveScore = zhuangNeedGiveScore == 0 ? 0 : (zhuangNeedGiveScore + huiscore + qiangScore);
			xianNeedGiveScore = xianNeedGiveScore == 0 ? 0 : (xianNeedGiveScore + huiscore + qiangScore);
			paoNeedGiveScore = paoNeedGiveScore == 0 ? 0 : (paoNeedGiveScore + huiscore + qiangScore);
			
			logger.info("computeUserScore***********"+zhuangNeedGiveScore+":"+paoNeedGiveScore+":"+xianNeedGiveScore);
			

			int winScore = 0;
			PlayerInfo winPlayerInfo = null;

			if (dianpaobaosanjia) {

				int dianpaoScore = 0;
				PlayerInfo dianpaoPlayerInfo = null;

				for (Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
					PlayerLocation eachLocation = entry.getKey();
					PlayerInfo eachPlayer = entry.getValue();
					if (winnerLocation.getCode() == eachLocation.getCode()) {
						winPlayerInfo = eachPlayer;
						continue;
					}
					boolean hasCompute = false;

					if (paoerLocation.getCode() == eachLocation.getCode()) {
						dianpaoPlayerInfo = eachPlayer;
					}

					if (zhuangLocation.getCode() == eachLocation.getCode()) {
						dianpaoScore += zhuangNeedGiveScore;
						hasCompute = true;
					}

					if (paoerLocation.getCode() == eachLocation.getCode()) {
						if (zhuangLocation.getCode() == eachLocation.getCode()) {

						} else {
							dianpaoScore += paoNeedGiveScore;
						}
						hasCompute = true;
					}
					if (!hasCompute) {
						dianpaoScore += xianNeedGiveScore;
					}
				}

				winScore = dianpaoScore;
				dianpaoPlayerInfo.setCurScore(dianpaoPlayerInfo.getCurScore() - dianpaoScore);
				
				logger.info("computeUserScore***********dianpaoScore"+dianpaoScore);

			} else {
				for (Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
					PlayerLocation eachLocation = entry.getKey();
					PlayerInfo eachPlayer = entry.getValue();

					if (winnerLocation.getCode() == eachLocation.getCode()) {
						winPlayerInfo = eachPlayer;
						continue;
					}

					boolean hasCompute = false;

					if (zhuangLocation.getCode() == eachLocation.getCode()) {
						eachPlayer.setCurScore(eachPlayer.getCurScore() - zhuangNeedGiveScore);
						hasCompute = true;
						winScore += zhuangNeedGiveScore;
					}
					if (paoerLocation.getCode() == eachLocation.getCode()) {
						if (zhuangLocation.getCode() == eachLocation.getCode()) {

						} else {
							eachPlayer.setCurScore(eachPlayer.getCurScore() - paoNeedGiveScore);
							winScore += paoNeedGiveScore;
						}
						hasCompute = true;
					}
					if (!hasCompute) {
						winScore += xianNeedGiveScore;
						eachPlayer.setCurScore(eachPlayer.getCurScore() - xianNeedGiveScore);
					}
					
					logger.info("computeUserScore***********"+eachPlayer.getUserLocation()+":"+eachPlayer.getCurScore());

				}
			}

			winPlayerInfo.setCurScore(winPlayerInfo.getCurScore() + winScore);

			Map<PlayerLocation, Integer> locationAndScore = new HashMap<PlayerLocation, Integer>();
			locationAndScore.put(PlayerLocation.EAST, 0);
			locationAndScore.put(PlayerLocation.SOUTH, 0);
			locationAndScore.put(PlayerLocation.WEST, 0);
			locationAndScore.put(PlayerLocation.NORTH, 0);
			Map<PlayerLocation, Integer> tempLocationAndScore = new HashMap<PlayerLocation, Integer>();

			for (Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
				PlayerLocation eachLocation = entry.getKey();
				PlayerInfo eachPlayer = entry.getValue();

				List<TileGroup> tileGroups = eachPlayer.getTileGroups();

				for (TileGroup tileGroup : tileGroups) {

					int eachgangscore = 0;

					if (ruleInfo.getPlayRules().contains(PlayRule.GANG)) {
						if (tileGroup.getType() == TileGroupType.ANGANG_GROUP) {
							eachgangscore = 4;// 暗杠：每家4分 不论胡不胡结算都加分
						} else if (tileGroup.getType() == TileGroupType.BUGANG_GROUP) {
							eachgangscore = 2;// 明杠：每家2分 不论胡不胡结算都加分
						} else if (tileGroup.getType() == TileGroupType.XUAN_FENG_GANG_DNXB_GROUP) {
							eachgangscore = 4;// 旋风杠：东南西北每家4分 中发白每家2分。不论胡不胡牌
						} else if (tileGroup.getType() == TileGroupType.XUAN_FENG_GANG_ZFB_GROUP) {
							eachgangscore = 2;// 旋风杠：东南西北每家4分 中发白每家2分。不论胡不胡牌
						}
					}

					for (Entry<PlayerLocation, Integer> locationAndScoreEntry : locationAndScore.entrySet()) {
						PlayerLocation scorelocation = locationAndScoreEntry.getKey();
						Integer score = locationAndScoreEntry.getValue();
						if (eachLocation.getCode() == scorelocation.getCode()) {
							score += eachgangscore*3;
						} else {
							score -= eachgangscore;
						}
						tempLocationAndScore.put(scorelocation, score);

					}
					
					locationAndScore = tempLocationAndScore;

				}
			}
			
			
			for (Entry<PlayerLocation, PlayerInfo> entry : playerInfos.entrySet()) {
				PlayerLocation eachLocation = entry.getKey();
				PlayerInfo eachPlayer = entry.getValue();
				Integer score = locationAndScore.get(eachLocation);
				
				logger.info("computeUserScore***********"+eachLocation.name()+":"+score);
				
				eachPlayer.setCurScore(eachPlayer.getCurScore() + score);
			}
			

		}
	}

}