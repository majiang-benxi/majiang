package com.mahjong.server.game.rule;

import java.util.Set;

import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.WinInfo;
import com.mahjong.server.game.rule.win.WinType;

public class ScoreHelper {
	public static int getZhuangScore(WinInfo winInfo, WinType winType) {
		int totalScore = 0;
		int baseScore = 1;// basescore
		if (winInfo.isZhuang()) {
			baseScore = 2;
		}
		if (winInfo.isZiMo()) {
			baseScore = 4;
		}
		Set<Byte> set = Tile.tile2Set(winInfo.getHuiTile());
		boolean qiongHu = true;
		for (byte pai : winInfo.getWinTile().getPai()) {
			if (set.contains(pai)) {// 每个会牌加一分
				totalScore += 1;
				qiongHu = false;
			}
			if (pai == Tile.QIANG) {// 每个枪牌加一分
				totalScore += 1;
				qiongHu = false;
			}
		}
		if (qiongHu) {
			baseScore *= 4;
		}
		totalScore += baseScore;
		return totalScore;

	}

//	public static int getScoreFromTileGroups(List<TileGroup> tileGroups) {
//		for (TileGroup tileGroup : tileGroups) {
//			if (tileGroup.getType() == TileGroupType.ANGANG_GROUP) {
//
//			}
//		}
	// }
}
