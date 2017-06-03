package com.mahjong.server.game.rule.win;

import java.util.ArrayList;
import java.util.List;

import com.mahjong.server.game.object.Tile;
import com.mahjong.server.game.object.TileUnitInfo;

public class CardPatternCheckResultVO {
	public boolean canhu = false;
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

	public CardPatternCheckResultVO() {
	}

	public CardPatternCheckResultVO clone() {
		CardPatternCheckResultVO resultVO = new CardPatternCheckResultVO();
		resultVO.uncheckedTile = uncheckedTile.clone();
		resultVO.huiUsedNum = huiUsedNum;
		resultVO.duiZiNum = duiZiNum;
		resultVO.tileUnitInfos = new ArrayList<TileUnitInfo>();
		for (TileUnitInfo tileUnitInfo : tileUnitInfos) {
			resultVO.tileUnitInfos.add(tileUnitInfo.clone());
		}
		return resultVO;
	}
}
