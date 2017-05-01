package com.mahjong.server.game.rule.win;

import static com.mahjong.server.game.object.StandardHuType.XIAO_HU;
import static com.mahjong.server.game.object.StandardTileUnitType.JIANG;
import static com.mahjong.server.game.object.StandardTileUnitType.KEZI;
import static com.mahjong.server.game.object.StandardTileUnitType.SHUNZI;
import static com.mahjong.server.game.rule.PlayRule.XIAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;

import com.mahjong.server.game.object.HuType;
import com.mahjong.server.game.object.StandardHuType;
import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileUnitInfo;
import com.mahjong.server.game.object.TileUnitType;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.RuleInfo;

public class NormalWinType implements WinType {
	protected HuType huType;// 本局胡牌类型
	protected List<TileUnitInfo> tileUnitInfos;// 选取当前玩法分数最高的作为返回值

	@Override
	public boolean canWin(WinInfo winInfo, RuleInfo ruleInfo) {
		// 三门齐（饼条万）有碰（111）有顺子（123）有19（带1和9的万饼条，风牌和字牌也为19）中发白做将免19免碰。
		if (xiaoHuCheck(winInfo, ruleInfo)) {
			huType = XIAO_HU;
			return true;
		}
		// 19check
		if (!check1or9(winInfo.getWinTile())) {
			return false;
		}
		// 花色check
		if (!huaSeCheck(winInfo.getWinTile())) {
			return false;
		}
		// 将牌check
		List<Byte> jiangPai = Tile.getJANGPai(winInfo.getWinTile());
		// 没有将牌【只有有会牌就算有将牌】直接胡不了
		if (CollectionUtils.isEmpty(jiangPai) && winInfo.getHuiTile().getPai().length == 0) {
			return false;
		}
		// 赢牌组合check
		Map<Byte, List<TileUnitInfo>> tileUnitTypeMap = parseTile2TileUnitTypes(jiangPai, winInfo);
		if (tileUnitTypeMap.isEmpty()) {
			return false;
		} else {
			chooseBestWinType(winInfo, tileUnitTypeMap);
			return true;
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

	// 按照将牌遍历出所有的赢牌的组合。
	protected Map<Byte, List<TileUnitInfo>> parseTile2TileUnitTypes(List<Byte> jiangPai, WinInfo winInfo) {
		Map<Byte, List<TileUnitInfo>> result = new HashMap<Byte, List<TileUnitInfo>>();
		for (Byte jiang : jiangPai) {
			int linkCount = 0;
			byte[] temp = winInfo.getWinTile().clone().getPai();
			List<TileUnitInfo> tileUnitInfos = new ArrayList<TileUnitInfo>();
			int jiangSizeTemp = 0;
			// 1、去除将牌
			for (int i = 0; i < temp.length; i++) {
				if (temp[i] == jiang) {
					temp[i] = 0x00;// 将牌置0
					jiangSizeTemp++;
					if (jiangSizeTemp == 2) // 将牌为2张
						tileUnitInfos.add(new TileUnitInfo(JIANG, new Tile(new byte[] { jiang, jiang })));
					break;
				}
			}
			Arrays.sort(temp);
			// 2、分离3同
			for (int i = 0; i < temp.length; i++) {// 3同
				if (i > 0 && i < temp.length - 1 && temp[i] > 0) {
					if (temp[i] == temp[i - 1] && temp[i] == temp[i + 1]) {
						tileUnitInfos.add(
								new TileUnitInfo(KEZI, new Tile(new byte[] { temp[i - 1], temp[i], temp[i + 1] })));
						temp[i - 1] = 0x00;
						temp[i] = 0x00;
						temp[i + 1] = 0x00;
						linkCount++;
					}
				}
			}
			Arrays.sort(temp);
			// 3、分离3连
			for (int i = 0; i < temp.length; i++) {// 3连
				if (i > 0 && i < temp.length - 1 && temp[i] > 0) {
					if (temp[i + 1] < 0X31 && temp[i - 1] != 0 && temp[i + 1] != 0 && temp[i] == temp[i - 1] + 1
							&& temp[i] == temp[i + 1] - 1) {
						tileUnitInfos.add(
								new TileUnitInfo(SHUNZI, new Tile(new byte[] { temp[i - 1], temp[i], temp[i + 1] })));
						temp[i - 1] = 0x00;
						temp[i] = 0x00;
						temp[i + 1] = 0x00;
						linkCount++;
					}
				}
			}
			if (linkCount == 4) {
				boolean isHu = checkHUForCandidate(jiang, tileUnitInfos);
				if (!isHu) {
					tileUnitInfos.clear();
				} else {
					result.put(jiang, tileUnitInfos);
				}
			} else {
				tileUnitInfos.clear();
			}
		}
		return result;

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

	private boolean check1or9(Tile winTile) {
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
