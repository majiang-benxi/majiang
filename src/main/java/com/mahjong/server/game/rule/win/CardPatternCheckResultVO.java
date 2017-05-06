package com.mahjong.server.game.rule.win;

import java.util.List;

import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileUnitInfo;

public class CardPatternCheckResultVO {
	public Tile uncheckedTile;
	public List<TileUnitInfo> tileUnitInfos;
	public int huiUsedNum = 0;
	public int duiZiNum = 0;

	public CardPatternCheckResultVO(Tile uncheckedTile, List<TileUnitInfo> tileUnitInfos, int huiUsedNum) {
		super();
		this.uncheckedTile = uncheckedTile;
		this.tileUnitInfos = tileUnitInfos;
		this.huiUsedNum = huiUsedNum;
	}

}
