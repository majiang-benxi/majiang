package com.mahjong.server.game.rule.win;

import com.mahjong.server.game.object.Tile;

public class QiDuiWinType extends NormalWinType {

	@Override
	protected boolean check1or9(Tile winTile) {
		if (super.check1or9(winTile)) {
			mayBePiaoHU = true;
		} else {
			mayBePiaoHU = false;
		}
		return true;// 七对不需要19
	}

}
