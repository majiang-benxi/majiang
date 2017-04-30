package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 一些标准的TileUnitType。 TODO TileUnitType拆分成单独的类、优化逻辑
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
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

		public List<Byte> getJANGPai(Tile tile) {
			List<Byte> jiangPAI = new ArrayList<Byte>();
			// tempMap存放牌值的张数<牌值,张数>
			Map<Byte, Integer> tempMap = new HashMap<Byte, Integer>();
			for (byte value : tile.getPai()) {
				if (tempMap.containsKey(value)) {
					tempMap.put(value, tempMap.get(value) + 1);
				} else {
					tempMap.put(value, 1);
				}
			}
			// 将可能成为将牌的牌值存放到jiangPAI中
			for (Entry<Byte, Integer> e : tempMap.entrySet()) {
				if (e.getValue() >= 2) {
					jiangPAI.add(e.getKey());
				}
			}
			return jiangPAI;
		}

	},
	KEZI(2) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			byte[] pais = tile.getPai();
			if (pais[0] != pais[1]) {
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
	HUA_UNIT(1) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			// TODO Auto-generated method stub
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
