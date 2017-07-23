package com.mahjong.server.game.rule.win;

import static com.mahjong.server.game.object.StandardTileUnitType.JIANG;
import static com.mahjong.server.game.object.StandardTileUnitType.KEZI;
import static com.mahjong.server.game.object.StandardTileUnitType.*;

import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileGroupType;
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

	public static CardPatternCheckResultVO x_hu_check(CardPatternCheckResultVO ck, int maxHunNum) {
		for (int i = 0; i <= maxHunNum; i++) {
			CardPatternCheckResultVO tempCk = ck.clone();
			boolean canhu = hu_check(tempCk, i);
			if (canhu) {
				tempCk.canhu = true;
				return tempCk;
			}
		}
		return null;
	}

	public static boolean hu_check(CardPatternCheckResultVO ck, int hasHunNum) {
		int unCheckPaiSize = ck.uncheckedTile.getPai().length;
		if (unCheckPaiSize == 0 && ck.huiUsedNum <= MAX_HUN_NUM) {
			return true;
		}
		if (unCheckPaiSize == 1) {
			byte pai = ck.uncheckedTile.getPai()[0];
			if (ck.duiZiNum == 0 && hasHunNum > 0) {
				ck.tileUnitInfos.add(new TileUnitInfo(JIANG, new Tile(new byte[] { pai, Tile.HUIPAI })));
				ck.huiUsedNum++;
				ck.duiZiNum++;
				return true;
			}
			if (hasHunNum >= 2) {
				ck.tileUnitInfos.add((new TileUnitInfo(KEZI, new Tile(new byte[] { pai, Tile.HUIPAI, Tile.HUIPAI }))));
				ck.huiUsedNum = ck.huiUsedNum + 2;
				ck.keZiNum++;
				ck.shunZiNum++;
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
					} else {
						if (Math.abs(b1 - b2) <= 2) {
							ck.tileUnitInfos
									.add((new TileUnitInfo(SHUNZI, new Tile(new byte[] { b1, b2, Tile.HUIPAI }))));
							ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2 }));// 第一张作为对子
							ck.huiUsedNum++;
							ck.shunZiNum++;
						} else {
							ck.tileUnitInfos.add((new TileUnitInfo(JIANG, new Tile(new byte[] { b1, Tile.HUIPAI }))));
							ck.uncheckedTile.removeAll(new Tile(new byte[] { b1 }));// 第一张作为对子
							ck.duiZiNum++;
							ck.huiUsedNum++;
						}
						return hu_check(ck, hasHunNum - 1);// 继续递归
					}

				}
			} else {
				if (hasHunNum == 0) {
					return false;
				}
				if (Math.abs(b1 - b2) <= 2) {
					ck.tileUnitInfos.add((new TileUnitInfo(SHUNZI, new Tile(new byte[] { b1, b2, Tile.HUIPAI }))));
					ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2 }));
					ck.shunZiNum++;
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
						ck.shunZiNum++;
						ck.keZiNum++;
						return hu_check(ck, hasHunNum - 4);// 继续递归
					}
				}
			}
		} else {// 大于等于三张
			
			// 第一张和另外3张构成一个组合(旋风杠或者暗杠)
			if(ck.uncheckedTile.getPai().length>3){
				for (int i = 0; i <  ck.uncheckedTile.getPai().length-3; i++) {
					byte b1 = ck.uncheckedTile.getPai()[i];
					byte b2 = ck.uncheckedTile.getPai()[i+1];
					byte b3 = ck.uncheckedTile.getPai()[i+2];
					byte b4 = ck.uncheckedTile.getPai()[i+3];
					Tile tile=new Tile(new byte[]{b1,b2,b3,b4});
					if(TileGroupType.ANGANG_GROUP.isLegalTile(tile)){
						ck.tileUnitInfos.add((new TileUnitInfo(GANGZI, tile)));
						ck.uncheckedTile.removeAll(tile);
						return hu_check(ck, hasHunNum - 4);// 继续递归
					}else if(TileGroupType.XUAN_FENG_GANG_DNXB_GROUP.isLegalTile(tile)){
						ck.tileUnitInfos.add((new TileUnitInfo(GANGZI, tile)));
						ck.uncheckedTile.removeAll(tile);
						return hu_check(ck, hasHunNum - 4);// 继续递归
					}
				}
			}
			
			boolean res = false;
			byte b1 = ck.uncheckedTile.getPai()[0];
			// 第一张和另外两张构成一个组合
			for (int i = 1; i <  ck.uncheckedTile.getPai().length; i++) {
				byte b2 = ck.uncheckedTile.getPai()[i];
				if (b2 - b1 > 2) {// 13444 134不可能连一起
					b1 = b2;
					continue;
				}

				if (i + 1 < ck.uncheckedTile.getPai().length) {
					if (b1 != b2 && ck.uncheckedTile.getPai()[i + 1] == b2) {
						continue;
					}
					byte b3 = ck.uncheckedTile.getPai()[i + 1];
					if (b1 == b2 && b2 == b3) {
						ck.tileUnitInfos.add((new TileUnitInfo(KEZI, new Tile(new byte[] { b1, b2, b3 }))));
						ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2, b3 }));
						ck.keZiNum++;
						res = hu_check(ck, hasHunNum);// 继续递归
						if (!res) {
							ck.uncheckedTile.addTile(new Tile(new byte[] { b1, b2, b3 })).sort();
						} else {
							return true;
						}

					}
					if ((b1 + 1 == b2||b1 + 2 == b2) && b2 + 1 == b3) {
						Tile tile = new Tile(new byte[] { b1, b2, b3 });
						if (SHUNZI.isLegalTile(tile)) {
							ck.tileUnitInfos.add((new TileUnitInfo(SHUNZI, tile)));
							ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2, b3 }));
							ck.shunZiNum++;
							res = hu_check(ck, hasHunNum);// 继续递归
							if (!res) {
								ck.uncheckedTile.addTile(new Tile(new byte[] { b1, b2, b3 })).sort();
							} else {
								return true;
							}
						}else if(ZI_ZFB_PAI.isLegalTile(tile)){
							ck.tileUnitInfos.add((new TileUnitInfo(ZI_ZFB_PAI, tile)));
							ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2, b3 }));
							res = hu_check(ck, hasHunNum);// 继续递归
							if (!res) {
								ck.uncheckedTile.addTile(new Tile(new byte[] { b1, b2, b3 })).sort();
							} else {
								return true;
							}
						}
						
					}
				}

			}
			// 第一个和第二个一铺
			b1 = ck.uncheckedTile.getPai()[0];

			for (int i = 1; i < ck.uncheckedTile.getPai().length; i++) {
				byte b2 = ck.uncheckedTile.getPai()[i];
				if (b2 - b1 > 2) {// 13444 134不可能连一起
					b1 = b2;
					continue;
				}

				if (b1 == b2) {
					if (hasHunNum > 0) {
						ck.tileUnitInfos.add((new TileUnitInfo(KEZI, new Tile(new byte[] { b1, b2, Tile.HUIPAI }))));
						ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2 }));
						ck.huiUsedNum++;
						ck.keZiNum++;
						res = hu_check(ck, hasHunNum - 1);// 继续递归
						if (!res) {
							ck.uncheckedTile.addTile(new Tile(new byte[] { b1, b2 })).sort();
						} else {
							return true;
						}
					}
					else if (ck.uncheckedTile.getPai().length <= 5) {// 最后这张剩下55777但没有混牌的时候
						ck.tileUnitInfos.add((new TileUnitInfo(JIANG, new Tile(new byte[] { b1, b2 }))));
						ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2 }));
						ck.duiZiNum++;
						res = hu_check(ck, hasHunNum);// 继续递归
						if (!res) {
							ck.uncheckedTile.addTile(new Tile(new byte[] { b1, b2 })).sort();
						} else {
							return true;
						}
					}
				}
				if ((b1 + 1 == b2||b1+2==b2) && hasHunNum > 0) {
					ck.tileUnitInfos.add((new TileUnitInfo(SHUNZI, new Tile(new byte[] { b1, b2, Tile.HUIPAI }))));
					ck.uncheckedTile.removeAll(new Tile(new byte[] { b1, b2 }));
					ck.huiUsedNum++;
					ck.shunZiNum++;
					res = hu_check(ck, hasHunNum - 1);// 继续递归
					if (!res) {
						ck.uncheckedTile.addTile(new Tile(new byte[] { b1, b2 })).sort();
					} else {
						return true;
					}
				}
			}
			b1 = ck.uncheckedTile.getPai()[0];
			// 第一个自己一铺
			if (hasHunNum > 2) {
				ck.tileUnitInfos.add((new TileUnitInfo(KEZI, new Tile(new byte[] { b1, Tile.HUIPAI, Tile.HUIPAI }))));
				ck.uncheckedTile.removeAll(new Tile(new byte[] { b1 }));
				ck.huiUsedNum += 2;
				ck.keZiNum++;
				ck.shunZiNum++;
				res = hu_check(ck, hasHunNum - 2);// 继续递归
				if (!res) {
					ck.uncheckedTile.addTile(new Tile(new byte[] { b1 })).sort();
				} else {
					return true;
				}
			}
		}
		return false;
	}
}
