package com.mahjong.server.game.object;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * 一些标准的TileUnitType。 TODO TileUnitType拆分成单独的类、优化逻辑
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum StandardTileUnitType implements TileUnitType {
	JIANG(2) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<Tile> getLackedTypesForLessTiles(Tile tile) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	KEZI(2){

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<Tile> getLackedTypesForLessTiles(Tile tile) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	SHUNZI(3) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<Tile> getLackedTypesForLessTiles(Tile tile) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	GANGZI(4) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<Tile> getLackedTypesForLessTiles(Tile tile) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	HUA_UNIT(1) {

		@Override
		protected boolean isLegalTilesWithCorrectSize(Tile tile) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<Tile> getLackedTypesForLessTiles(Tile tile) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private static final Logger logger = Logger.getLogger(StandardTileUnitType.class.getSimpleName());

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


	public List<Tile> getLackedTypesForTiles(Tile tile) {
		if (tile.getPai() == null)
			throw new IllegalArgumentException("tiles cannot be empty.");
		else if (tile.getPai().length >=size()){
			return Collections.emptyList();
		}
		else{
			return (List<Tile>) (isLegalTilesWithCorrectSize(tile) ? getLackedTypesForLessTiles(tile)
					: Collections.emptyList());
		}
	}

	public abstract List<Tile> getLackedTypesForLessTiles(Tile tile);

	
}
