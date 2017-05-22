package com.mahjong.server.game.action;

import java.util.Objects;

import com.mahjong.server.game.object.Tile;

/**
 * 动作。
 * 
 * @author warter
 */
public class Action {
	/**
	 * 动作类型。
	 */
	private ActionType type;
	/**
	 * 与动作相关的牌。
	 */
	private Tile tile;
	/**
	 * 此动作发生的时间
	 */
	private long time;

	/**
	 * 新建一个实例，没有相关的牌。
	 */
	public Action(ActionType type) {
		this(type, null);
	}

	/**
	 * 新建一个实例。
	 */
	public Action(ActionType type, Tile tile) {
		Objects.requireNonNull(type);
		this.type = type;
		this.tile = tile;
		this.time = System.currentTimeMillis();
	}

	public ActionType getType() {
		return type;
	}


	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tile == null) ? 0 : tile.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Action))
			return false;
		Action other = (Action) obj;
		if (tile == null) {
			if (other.tile != null)
				return false;
		} else if (!tile.equals(other.tile))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "[" + type + ", " + tile + "," + time + "]";
	}

}
