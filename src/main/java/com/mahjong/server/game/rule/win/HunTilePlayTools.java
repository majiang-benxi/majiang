package com.mahjong.server.game.rule.win;

import static com.mahjong.server.game.object.StandardTileUnitType.JIANG;
import static com.mahjong.server.game.object.StandardTileUnitType.KEZI;
import static com.mahjong.server.game.object.StandardTileUnitType.SHUNZI;

import java.util.Arrays;

import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileUnitInfo;

public class HunTilePlayTools {
	private static final int MAX_HUN_NUM = 7;// 最多7个会牌

	public static int getModNeedNum(byte[] pais, boolean hasJiang) {
		if (pais.length <= 0) {
			return 0;
		}
		int modNum = pais.length % 3;
		int[] needNumArr = new int[] { 0, 2, 1 };
		if (hasJiang) {
			needNumArr = new int[] { 2, 1, 0 };
		}
		return needNumArr[modNum];
	}

	public static boolean hu_check(CardPatternCheckResultVO ck, int hasHunNum) {
		int unCheckPaiSize = ck.uncheckedTile.getPai().length;
		if (unCheckPaiSize == 0 && ck.huiUsedNum <= MAX_HUN_NUM) {
			return true;
		}
		if (unCheckPaiSize == 1) {
			byte pai = ck.uncheckedTile.getPai()[0];
			if (ck.duiZiNum == 0 && hasHunNum > 0) {
				ck.tileUnitInfos.add(new TileUnitInfo(JIANG, new Tile(new byte[] { pai,Tile.HUIPAI})));
				ck.huiUsedNum++;
				ck.duiZiNum++;
 				return true;
			}
			if(hasHunNum>=2){
				ck.tileUnitInfos.add((new TileUnitInfo(KEZI, new Tile(new byte[] { pai, Tile.HUIPAI, Tile.HUIPAI }))));
				ck.huiUsedNum = ck.huiUsedNum + 2;
				return true;
			} else {
				return false;
			}
		}
		if (unCheckPaiSize == 2) {
			byte b1 = ck.uncheckedTile.getPai()[0];
			byte b2 = ck.uncheckedTile.getPai()[1];
			if (ck.duiZiNum == 0) {
				if (b1 == b2) {
					ck.tileUnitInfos.add((new TileUnitInfo(JIANG, new Tile(new byte[] { b1, b2 }))));
					ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2 }));
					ck.duiZiNum++;
					return hu_check(ck, hasHunNum);// 继续递归
				} else {
					if (hasHunNum == 0) {
						return false;
					}
					ck.tileUnitInfos.add((new TileUnitInfo(JIANG, new Tile(new byte[] { b1, Tile.HUIPAI }))));
					ck.uncheckedTile.removeAll(new Tile(new byte[] { b1 }));// 第一张作为对子
					ck.duiZiNum++;
					return hu_check(ck, hasHunNum - 1);// 继续递归
				}
			} else {
				if (hasHunNum == 0) {
					return false;
				}
				if (Math.abs(b1 - b2) <= 2) {
				ck.tileUnitInfos.add((new TileUnitInfo(SHUNZI, new Tile(new byte[] { b1, b2, Tile.HUIPAI }))));
				ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2 }));
					return hu_check(ck, hasHunNum - 1);// 继续递归
				} else {
					if (hasHunNum < 4) {// 这个时候有2张散牌，混子数量需要大于等于4
						return false;
					} else {
						ck.tileUnitInfos
								.add((new TileUnitInfo(SHUNZI, new Tile(new byte[] { b1, Tile.HUIPAI, Tile.HUIPAI }))));
						ck.tileUnitInfos
								.add((new TileUnitInfo(SHUNZI, new Tile(new byte[] { b1, Tile.HUIPAI, Tile.HUIPAI }))));
						ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2 }));
						return hu_check(ck, hasHunNum - 4);// 继续递归
					}
				}
			}
		} else {// 大于等于三张
			byte[] temp = new byte[unCheckPaiSize];
			System.arraycopy(ck.uncheckedTile.getPai(), 0, temp, 0, unCheckPaiSize);
			// 分离3同
			for (int i = 0; i < temp.length; i++) {// 3同
				if (i > 0 && i < temp.length - 1 && temp[i] > 0) {
					if (temp[i] == temp[i - 1] && temp[i] == temp[i + 1]) {
						Tile tile=new Tile(new byte[] { temp[i - 1], temp[i], temp[i + 1] });
						ck.tileUnitInfos.add(
								new TileUnitInfo(KEZI,tile ));
						ck.uncheckedTile.removeAll(tile);
						temp[i - 1] = 0x00;
						temp[i] = 0x00;
						temp[i + 1] = 0x00;
					}
				}
			}
			Arrays.sort(temp);
			// 3、分离3连
			for (int i = 0; i < temp.length; i++) {// 3连
				if (i > 0 && i < temp.length - 1 && temp[i] > 0) {
					if (temp[i + 1] < 0X31 && temp[i - 1] != 0 && temp[i + 1] != 0 && temp[i] == temp[i - 1] + 1
							&& temp[i] == temp[i + 1] - 1) {
						Tile  tile =new Tile(new byte[] { temp[i - 1], temp[i], temp[i + 1] });
						ck.tileUnitInfos.add(
								new TileUnitInfo(SHUNZI, tile));
						ck.uncheckedTile.removeAll(tile);
						temp[i - 1] = 0x00;
						temp[i] = 0x00;
						temp[i + 1] = 0x00;
					}
				}
			}
			if(ck.uncheckedTile.getPai().length<3){
				return hu_check(ck, hasHunNum);
			}else{
				// 分离2同
				for (int i = 0; i < temp.length; i++) {// 2同
					if (i < temp.length - 1 && temp[i] > 0) {
						if (temp[i] == temp[i + 1]) {
							Tile tile=new Tile(new byte[] { Tile.HUIPAI, temp[i], temp[i + 1] });
							ck.tileUnitInfos.add(
									new TileUnitInfo(KEZI,tile ));
							ck.uncheckedTile.removeAll(tile);
							temp[i] = 0x00;
							temp[i + 1] = 0x00;
							return hu_check(ck, hasHunNum - 1);
						}
					}
				}
				Arrays.sort(temp);
				// 分离2连
				for (int i = 0; i < temp.length; i++) {// 2连
					if ( i < temp.length - 1 && temp[i] > 0) {
						if (temp[i + 1] < 0X31 && temp[i] != 0 && temp[i + 1] != 0&& Math.abs(temp[i] -temp[i + 1]) <= 2) {
							Tile  tile =new Tile(new byte[] { Tile.HUIPAI, temp[i], temp[i + 1] });
							ck.tileUnitInfos.add(
									new TileUnitInfo(SHUNZI, tile));
							ck.uncheckedTile.removeAll(tile);
							ck.huiUsedNum++;
							temp[i] = 0x00;
							temp[i + 1] = 0x00;
							return hu_check(ck, hasHunNum - 1);
						}
					}
				}
				if (hasHunNum >= 2) {// 分离完毕之后牌还多余三张。此时用2个混和第一张组成牌组
					byte b1 = ck.uncheckedTile.getPai()[0];
					Tile tile = new Tile(new byte[] { Tile.HUIPAI, Tile.HUIPAI, b1 });
					ck.tileUnitInfos.add(new TileUnitInfo(KEZI, tile));
					ck.uncheckedTile.removeAll(tile);
					ck.huiUsedNum = ck.huiUsedNum + 2;
					return hu_check(ck, hasHunNum - 2);
				} else {
					return false;
				}
			}
		}
	}
}
