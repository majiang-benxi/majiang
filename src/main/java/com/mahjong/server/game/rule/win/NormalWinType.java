package com.mahjong.server.game.rule.win;

import static com.mahjong.server.game.object.StandardHuType.XIAO_HU;
import static com.mahjong.server.game.object.StandardTileUnitType.KEZI;
import static com.mahjong.server.game.object.StandardTileUnitType.SHUNZI;
import static com.mahjong.server.game.rule.PlayRule.XIAO;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;

import com.mahjong.server.game.object.HuType;
import com.mahjong.server.game.object.StandardHuType;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.Tile.HuaSe;
import com.mahjong.server.game.object.TileUnitInfo;
import com.mahjong.server.game.object.TileUnitType;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.RuleInfo;

public class NormalWinType implements WinType {
	protected HuType huType;// 本局胡牌类型
	protected List<TileUnitInfo> tileUnitInfos;// 选取当前玩法分数最高的作为返回值

	/**
	 * 三门齐（饼条万）有碰（111）有顺子（123）有19（带1和9的万饼条，风牌和字牌也为19）中发白做将免19免碰。
	 */

	@Override
	public boolean canWin(WinInfo winInfo, RuleInfo ruleInfo) {
		// 第一把还没打牌就赢了
		if (xiaoHuCheck(winInfo, ruleInfo)) {
			huType = XIAO_HU;
			return true;
		}
		// 19check，7对不检查，需要重载
		if (!check1or9(winInfo.getWinTile())) {
			return false;
		}
		// 花色check。清一色检查规则特殊，需要重载
		if (!huaSeCheck(winInfo.getWinTile())) {
			return false;
		}
		// 将牌check
		List<Byte> jiangPai = Tile.getJANGPai(winInfo.getWinTile());
		int huiNum = winInfo.getHuiTile().getPai().length;
		// 没有将牌【只有有会牌就算有将牌】直接胡不了

		if (CollectionUtils.isEmpty(jiangPai) && huiNum == 0) {
			return false;
		}
		// 以下用来检测癞子的情况下，是否可以胡，如果一个花色用完所有的癞子的情况下都不能构成一句话就胡不了，先排除掉
		CardPatternCheckResultVO wanCardPatternCheckResult = new CardPatternCheckResultVO(
				Tile.getSortedHuaSePaiFromPai(winInfo.getWinTile(), HuaSe.WAN), tileUnitInfos, huiNum);
		// 赢牌组合check
		boolean wanRes = HunTilePlayTools.hu_check(wanCardPatternCheckResult, huiNum);
		if (wanRes == false) {
			return false;
		}
		CardPatternCheckResultVO tiaoCardPatternCheckResult = new CardPatternCheckResultVO(
				Tile.getSortedHuaSePaiFromPai(winInfo.getWinTile(), HuaSe.TIAO), tileUnitInfos, huiNum);
		boolean tiaoRes = HunTilePlayTools.hu_check(tiaoCardPatternCheckResult, huiNum);
		if (tiaoRes == false) {
			return false;
		}
		CardPatternCheckResultVO bingCardPatternCheckResult = new CardPatternCheckResultVO(
				Tile.getSortedHuaSePaiFromPai(winInfo.getWinTile(), HuaSe.BING), tileUnitInfos, huiNum);
		boolean bingRes = HunTilePlayTools.hu_check(bingCardPatternCheckResult, huiNum);
		if (bingRes == false) {
			return false;
		}
		CardPatternCheckResultVO ziCardPatternCheckResult = new CardPatternCheckResultVO(
				Tile.getSortedHuaSePaiFromPai(winInfo.getWinTile(), HuaSe.ZI), tileUnitInfos, huiNum);
		boolean ziRes = HunTilePlayTools.hu_check(ziCardPatternCheckResult, huiNum);
		if (ziRes == false) {
			return false;
		}
		// 检测到这里表明至少用了癞子后每个花色都可以凑成对应的一句或者多句话。
		int totalDuizi = wanCardPatternCheckResult.duiZiNum + tiaoCardPatternCheckResult.duiZiNum
				+ bingCardPatternCheckResult.duiZiNum + ziCardPatternCheckResult.duiZiNum;
		int	totalHunAll = tiaoCardPatternCheckResult.huiUsedNum + bingCardPatternCheckResult.huiUsedNum
					+ ziCardPatternCheckResult.huiUsedNum+wanCardPatternCheckResult.huiUsedNum;
		if (totalDuizi == 0) {// 没有将的情况
			if (totalHunAll + 2 == huiNum) {// 还有两个会牌的话，可以胡牌
				return true;
			}else{
			return false;
			}
		}else{
			if ((totalDuizi - 1) + totalHunAll == huiNum) {// 把会牌跟多余的将牌凑成一句话。
				return true;
			} else {
				return false;
			}
		}
	}


	private void chooseBestWinType(final WinInfo winInfo, Map<Byte, List<TileUnitInfo>> tileUnitTypeMap) {
		for ( Entry<Byte, List<TileUnitInfo>> entry : tileUnitTypeMap.entrySet()) {
			HuType[] allHuTypes = StandardHuType.values();
			// 按照可能的分数从大到小排序
			Collections.sort(Arrays.asList(allHuTypes), new Comparator<HuType>() {
				@Override
				public int compare(HuType o1, HuType o2) {
					if (o1.getZhuangScore(winInfo.isZiMo()) < o1.getZhuangScore(winInfo.isZiMo())) {
						return 1;
					} else {
						return -1;
					}
				}
			});
			for (HuType huType : allHuTypes) {
				boolean canHu = huType.canHU(winInfo, entry.getKey(), entry.getValue());
				if (canHu) {
					this.huType=huType;
					this.tileUnitInfos = entry.getValue();
					break;
				}
			}
		}
	}

	// 默认没有特殊检查，特殊检查是指类似连三门齐顺子19都不需要满足的赢牌方法
	private boolean xiaoHuCheck(WinInfo winInfo, RuleInfo ruleInfo) {
		if (winInfo.isFirstTileCheck() && ruleInfo.getPlayRules().contains(XIAO)) {
			if (XIAO_HU.canHU(winInfo, null, null)) {
				return true;
			}
		}
		return false;
	}

	// 判断包含了将且有四句话的牌的组合是否可以赢牌，默认必须要顺子和碰
	protected boolean checkHUForCandidate(Byte jiang, List<TileUnitInfo> tileUnitInfos) {
		boolean hasPeng=false,hasShunZi=false;
		int jiangNum = (int) jiang;
		for (TileUnitInfo tileUnitInfo : tileUnitInfos) {
			// 中发白做将免19免碰，否则必须有碰
			if (tileUnitInfo.getTileUnitType() == KEZI || (jiangNum > 52 && jiangNum < 56)) {
				hasPeng = true;
			}
			if (tileUnitInfo.getTileUnitType() == SHUNZI) {
				hasShunZi = true;
			}
		}
		return hasPeng && hasShunZi;
	}

	protected boolean check1or9(Tile winTile) {
		boolean has19 = false;
		for (byte pai : winTile.getPai()) {
			int paiNum = (int) pai;
			if (paiNum == 1 || paiNum == 17 || paiNum == 33 || paiNum == 9 || paiNum == 25 || paiNum == 41
					|| (paiNum > 48 && paiNum < 56)) {
				has19 = true;
			}

		}
		return has19;
	}

	// 普通情况下，花色需要满足三门齐
	protected boolean huaSeCheck(Tile winTile) {
		boolean hasWan = false, hasBing = false, hasTiao = false;
		for (byte pai : winTile.getPai()) {
			int paiNum = (int) pai;
			if (paiNum > 0 && pai < 10) {
				hasWan = true;
			} else if (paiNum > 16 && pai < 26) {
				hasTiao = true;
			} else if (paiNum > 32 && pai < 42) {
				hasBing = true;
			}
		}
		return hasWan && hasBing && hasTiao;
	}

	@Override
	public List<TileUnitType> getWinTileUnitType() {
		return null;
	}

	public HuType getHuType() {
		return huType;
	}


}
