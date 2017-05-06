package com.mahjong.server.game.rule.win;

import com.mahjong.server.game.object.Tile;

public class QingYiSeWinType extends NormalWinType {
	@Override
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
		} // 只有一种花色
		return (hasWan && !hasBing && !hasTiao) || (!hasWan && hasBing && !hasTiao) || (!hasWan && !hasBing && hasTiao);
	}

}
