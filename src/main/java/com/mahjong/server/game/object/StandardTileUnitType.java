package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一些标准的TileUnitType。 TODO TileUnitType拆分成单独的类、优化逻辑
 * 
 * @author warter
 */
public enum StandardTileUnitType implements TileUnitType {
	JIANG(2) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			byte[] pais = tile.getPai();
			if (pais[0] != pais[1]) {
				return false;
			}
			return true;
		}



	},
	KEZI(3) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			byte[] pais = tile.getPai();
			if (pais[0] != pais[1] || pais[1] != pais[2]) {
				return false;
			}
			return true;
		}
	},
	SHUNZI(3) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			List<Integer> list = new ArrayList<Integer>();
			for (byte pai : tile.getPai()) {
				int paiInt=(int) pai;
				if(paiInt>48){//东西南北中发白不能构成顺子
					return false;
				}
				list.add(paiInt);
			}
			Collections.sort(list);
			if ((list.get(0) + 1 == list.get(1)) && (list.get(1) == list.get(2) - 1)) {
				return true;
			} else {
				return false;
			}
		}

	},
	GANGZI(4) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			byte[] pais = tile.getPai();
			if (pais[0] == pais[1] && pais[0] == pais[2] && pais[0] == pais[3]) {
				return true;
			}
			return false;
		}

	},
	ZI_ZFB_PAI(3) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			byte[] pais = tile.getPai();
			List<Integer> list = new ArrayList<Integer>();
			for (byte pai : pais) {
				list.add((int) pai);
			}
			Collections.sort(list);
			if (list.get(0) == 53 && list.get(1) == 54 && list.get(2) == 55) {
				return true;
			}
			return false;
		}
	},
	ZI_DNXB_PAI(4) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			byte[] pais = tile.getPai();
			List<Integer> list = new ArrayList<Integer>();
			for (byte pai : pais) {
 				list.add((int) pai);
			}
			Collections.sort(list);
			if (list.get(0) == 49 && list.get(1) == 50 && list.get(2) == 51 && list.get(3) == 52) {
				return true;
			}
			return false;
		}
	},

	QIANGZI(1) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			if (tile.getPai()[0] == 0x27) {// 固定7饼
				return true;
			} else {
				return false;
			}
		}

	};
	private final int size;

	private StandardTileUnitType(int tileCount) {
		this.size = tileCount;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isLegalTile(Tile tile) {
		if (tile.getPai().length != size())
			return false;
		return isLegalTilesWithCorrectSize(tile);
	}

	/**
	 * 判断张数合法的情况下，牌是否合法
	 * 
	 * @param tile
	 * @return
	 */
	protected abstract boolean isLegalTilesWithCorrectSize(Tile tile);

}
