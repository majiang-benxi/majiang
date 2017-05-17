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
			if (pais[0] != pais[1] && pais[1] != pais[2]) {
				return false;
			}
			return true;
		}
	},
	SHUNZI(3) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			byte[] pais = tile.getPai();
			List<Integer> list = new ArrayList<Integer>();
			for (byte pai : tile.getPai()) {
				list.add((int) pai);
			}
			Collections.sort(list);
			if ((list.get(0) + 1 == list.get(2)) && (list.get(2) == list.get(3) - 1)) {
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
			if (pais[0] == pais[1] && pais[0] == pais[2] && pais[0] == pais[3] && pais[0] == pais[4]) {
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
			if (list.get(0) == 35 && list.get(1) == 36 && list.get(2) == 37) {
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
			if (list.get(0) == 31 && list.get(1) == 32 && list.get(2) == 33 && list.get(2) == 34) {
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
