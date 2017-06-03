package com.mahjong.server.game.rule.win;

import static com.mahjong.server.game.object.StandardTileUnitType.KEZI;
import static com.mahjong.server.game.object.StandardTileUnitType.SHUNZI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.mahjong.server.game.object.PlayerTiles;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.Tile.HuaSe;
import com.mahjong.server.game.object.TileGroup;
import com.mahjong.server.game.object.TileGroupType;
import com.mahjong.server.game.object.TileUnitInfo;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.FangKa;
import com.mahjong.server.game.rule.RuleInfo;

public class NormalWinType implements WinType {
	protected List<TileUnitInfo> tileUnitInfos = new ArrayList<TileUnitInfo>();// 选取当前玩法分数最高的作为返回值

	/**
	 * 三门齐（饼条万）有碰（111）有顺子（123）有19（带1和9的万饼条，风牌和字牌也为19）中发白做将免19免碰。
	 */

	@Override
	public boolean canWin(WinInfo winInfo, RuleInfo ruleInfo) {
		// 19check，7对不检查，需要重载
		if (!check1or9(winInfo.getWinTile())) {
			return false;
		}
		// 花色check。清一色检查规则特殊，需要重载
		if (!huaSeCheck(winInfo.getWinTile())) {
			return false;
		}
		// 将牌check
		List<Byte> jiangPai = Tile.getJANGPai(winInfo.getAliveTile());
		int huiNum = winInfo.getHuiTile().getPai().length;
		// 没有将牌【只有有会牌就算有将牌】直接胡不了

		if (CollectionUtils.isEmpty(jiangPai) && huiNum == 0) {
			return false;
		}
		// 以下用来检测癞子的情况下，是否可以胡，如果一个花色用完所有的癞子的情况下都不能构成一句话就胡不了，先排除掉
		CardPatternCheckResultVO wanCardPatternCheckResult = new CardPatternCheckResultVO(
				Tile.getSortedHuaSePaiFromPai(winInfo.getAliveTile(), HuaSe.WAN), tileUnitInfos, 0);
		// 赢牌组合check
		CardPatternCheckResultVO wanRes = HunTilePlayTools.x_hu_check(wanCardPatternCheckResult, huiNum);
		if (wanRes == null || wanRes.canhu == false) {
			return false;
		}
		CardPatternCheckResultVO tiaoCardPatternCheckResult = new CardPatternCheckResultVO(
				Tile.getSortedHuaSePaiFromPai(winInfo.getAliveTile(), HuaSe.TIAO), tileUnitInfos, 0);
		CardPatternCheckResultVO tiaoRes = HunTilePlayTools.x_hu_check(tiaoCardPatternCheckResult, huiNum);
		if (tiaoRes == null || tiaoRes.canhu == false) {
			return false;
		}
		CardPatternCheckResultVO bingCardPatternCheckResult = new CardPatternCheckResultVO(
				Tile.getSortedHuaSePaiFromPai(winInfo.getAliveTile(), HuaSe.BING), tileUnitInfos, 0);
		CardPatternCheckResultVO bingRes = HunTilePlayTools.x_hu_check(bingCardPatternCheckResult, huiNum);
		if (bingRes == null || bingRes.canhu == false) {
			return false;
		}
		CardPatternCheckResultVO ziCardPatternCheckResult = new CardPatternCheckResultVO(
				Tile.getSortedHuaSePaiFromPai(winInfo.getAliveTile(), HuaSe.ZI), tileUnitInfos, 0);
		CardPatternCheckResultVO ziRes = HunTilePlayTools.x_hu_check(ziCardPatternCheckResult, huiNum);
		if (ziRes == null || ziRes.canhu == false) {
			return false;
		}
		// 检测到这里表明至少用了癞子后每个花色都可以凑成对应的一句或者多句话。
		int totalDuizi = wanRes.duiZiNum + tiaoRes.duiZiNum + bingRes.duiZiNum + ziRes.duiZiNum;
		int totalHunAll = wanRes.huiUsedNum + tiaoRes.huiUsedNum + bingRes.huiUsedNum + ziRes.huiUsedNum;
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
	public boolean isPiaoHU(WinInfo winInfo) {
		if (!new NormalWinType().check1or9(winInfo.getWinTile())){
			return false;
		}
		int alivePengNum = 0, dropPengNum = 0;
		;
		// tempMap存放牌值的张数<牌值,张数>
		Map<Byte, Integer> tempMap = new HashMap<Byte, Integer>();
 		for (byte value : winInfo.getAliveTile().getPai()) {
			if (tempMap.containsKey(value)) {
				tempMap.put(value, tempMap.get(value) + 1);
			} else {
				tempMap.put(value, 1);
			}
		}
		// 将可能成为将牌的牌值存放到jiangPAI中
		for (Entry<Byte, Integer> e : tempMap.entrySet()) {
			if (e.getValue() == 3) {
				alivePengNum++;
			}
		}
		for (TileGroup tileGroup : winInfo.getDropTileGroups()) {
			if (tileGroup.getType() == TileGroupType.PENG_GROUP) {
				dropPengNum++;
			}
		}
		// 手中的牌和已经碰过的牌已有的碰子个数
		if (alivePengNum + dropPengNum == 7) {
			return true;
		} else {
			int aloneAliveTileSize = winInfo.getAliveTile().getPai().length - alivePengNum * 3;
			int huiNum = winInfo.getHuiTile().getPai().length;
			if (huiNum > 0 && aloneAliveTileSize * 2 == huiNum) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean maybeQiongHu(WinInfo winInfo) {
		Set<Byte> set = Tile.tile2Set(winInfo.getHuiTile());
		boolean qiongHu = true;
		for (byte pai : winInfo.getWinTile().getPai()) {
			if (set.contains(pai)) {// 会牌
				qiongHu = false;
			}
			if (pai == Tile.QIANG) {// 枪牌
				qiongHu = false;
			}
		}
		return qiongHu;
	}

	public static void main(String[] args) {
		byte[] pais = new byte[] { 0x01, 0x02, 0x03, 0x12, 0x12, 0x12, 0x21, 0x22, 0x23, 0x29, 0x29 };// 没有会牌
																										// true
		pais = new byte[] { 0x01, 0x02, 0x14, 0x12, 0x12, 0x12, 0x21, 0x22, 0x23, 0x29, 0x29 };// 1张会牌true
		pais = new byte[] { 0x01, 0x15, 0x14, 0x12, 0x12, 0x12, 0x21, 0x22, 0x23, 0x29, 0x29 };// 2张会牌true
		pais = new byte[] { 0x14, 0x15, 0x14, 0x12, 0x12, 0x12, 0x21, 0x22, 0x23, 0x29, 0x29 };// 3张会牌,缺花色false
		pais = new byte[] { 0x14, 0x15, 0x14, 0x12, 0x12, 0x12, 0x21, 0x01, 0x23, 0x29, 0x29 };// 3张会牌
		pais = new byte[] { 0x21, 0x22, 0x23, 0x22, 0x23, 0x14, 0x21, 0x22, 0x23, 0x29, 0x29 };// 清一色true
		// pais = new byte[] { 0x21, 0x15, 0x23, 0x22, 0x23, 0x24, 0x27, 0x27,
		// 0x27, 0x27, 0x28, 0x29, 0x14, 0x29 };// 清一色true
		// pais = new byte[] { 0x01, 0x02, 0x03, 0x02, 0x03, 0x04, 0x05, 0x06,
		// 0x07, 0x07, 0x07, 0x07, 0x05, 0x05 }; // 清一色带会,带枪,true
		// pais = new byte[] { 0x11, 0x15, 0x11, 0x12, 0x12, 0x12, 0x18, 0x18,
		// 0x18, 0x15, 0x17, 0x17, 0x16, 0x15 }; // 清一色飘
		// pais = new byte[] { 0x11, 0x11, 0x12, 0x12, 0x16, 0x16, 0x18, 0x18,
		// 0x13, 0x13, 0x17, 0x17, 0x19, 0x19 };// 清一色七小对true
		// pais = new byte[] { 0x01, 0x01, 0x01, 0x02, 0x02, 0x02, 0x07, 0x07,
		// 0x07, 0x08, 0x08, 0x08, 0x06, 0x06 }; // 清一色穷true
		// pais = new byte[] { 0x11, 0x11, 0x36, 0x36, 0x21, 0x21, 0x15, 0x31,
		// 0x32, 0x15, 0x16, 0x16, 0x37, 0x37 };// 七小对true
		pais = new byte[] { 0x11, 0x11, 0x36, 0x36, 0x21, 0x21, 0x31, 0x31, 0x32, 0x32, 0x16, 0x16, 0x37, 0x37 };// 七小对穷true
		pais = new byte[] { 0x11, 0x15, 0x11, 0x02, 0x02, 0x02, 0x28, 0x28, 0x28, 0x14, 0x17, 0x17, 0x16, 0x15 };// 飘true
		pais = new byte[] { 0x11, 0x11, 0x11, 0x02, 0x02, 0x02, 0x28, 0x28, 0x28, 0x17, 0x17, 0x17, 0x16, 0x16 };// 飘穷true
		pais = new byte[] { 0x27, 0x27, 0x14, 0x32, 0x33, 0x15, 0x12, 0x13, 0x14, 0x12, 0x13, 0x14, 0x36, 0x36 };// 普通胡带会带枪false
		pais = new byte[] { 0x11, 0x11, 0x11, 0x07, 0x08, 0x09, 0x33, 0x33, 0x33, 0x34, 0x34, 0x34, 0x21, 0x21 };// 穷胡true
		Tile tile = new Tile(pais);
		PlayerTiles playerTiles=new PlayerTiles();
		playerTiles.setAliveTiles(tile);
		WinInfo winInfo= WinInfo.fromPlayerTiles(playerTiles,(byte)0x14,false);
		RuleInfo ruleInfo = new RuleInfo();
		ruleInfo.setPlayRules(RuleInfo.parseRuleFromBitString("01111"));
		ruleInfo.setFangKa(FangKa.ONE_SIXTEEN);
		NormalWinType winType = new NormalWinType();
		// QingYiSeWinType winType = new QingYiSeWinType();
		// QiDuiWinType winType = new QiDuiWinType();
		System.out.println(winType.canWin(winInfo, ruleInfo));

	}
}
