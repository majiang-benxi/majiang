package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

public class Tile {
	private byte[] pai;// 存储相关的一组牌

	public static byte[] getOneBoxMahjong() {
		byte[] allPai = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, // 万（1-9）
				0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, // 万
				0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, // 万
				0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, // 万
				0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, // 条（17-25）
				0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, // 条
				0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, // 条
				0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, // 条
				0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, // 筒（33-41）
				0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, // 筒
				0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, // 筒
				0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, // 筒
				0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, // 番子[东西南北中发白]
				0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, // 番子
				0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, // 番子
				0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, // 番子（49-55）
		};
		return allPai;
	}

	public byte[] getPai() {
		return pai;
	}

	public void setPai(byte[] pai) {
		this.pai = pai;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		byte[] destPai = new byte[] {};
		System.arraycopy(this.pai, 0, destPai, 0, this.pai.length);
		return destPai;
	}

	public static Tile addTile(Tile tile1, Tile tile2) {
		Tile tile = new Tile();
		tile.setPai(ArrayUtils.addAll(tile1.getPai(), tile2.getPai()));
		return tile;
	}

	// 会改变当前类
	public Tile addTile(Tile tile) {
		if (pai == null) {
			setPai(tile.getPai());
			return this;
		}
		if (tile == null || tile.getPai() == null) {
			return this;
		}
		pai = ArrayUtils.addAll(pai, tile.getPai());
		return this;
	}

	public boolean containsAll(Tile tile) {
		if (pai == null) {
			return false;
		}
		if (tile.getPai() == null) {
			return true;
		}
		Set<Integer> set = new HashSet<Integer>();
		for (byte onePai : pai) {
			set.add((int) onePai);
		}
		for (byte onePai : tile.getPai()) {
			if (!set.contains((int) onePai)) {
				return false;
			}
		}
		return true;
	}

	public boolean removeAll(Tile tile) {
		if (tile == null) {
			return true;
		}
		if (pai == null) {
			return false;
		}
		List<Integer> list = new ArrayList<Integer>();
		for (byte onePai : pai) {
			list.add((int) onePai);
		}
		for (byte onePai : tile.getPai()) {
			list.remove(new Integer((int) onePai));// jdk缺陷，防止自动拆箱装箱
		}
		byte[] result = new byte[list.size()];
		int i = 0;
		for (Integer intByte : list) {
			result[i] = (byte) intByte.intValue();
			i++;
		}
		pai = result;
		return true;
	}

	public static void main(String[] args) {
		Tile allTile = new Tile();
		allTile.setPai(Tile.getOneBoxMahjong());

		byte[] pai = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09 }; // 万（1-9）
		Tile tile = new Tile();
		tile.setPai(pai);
		System.out.println(allTile.containsAll(tile));
		for (byte b : allTile.getPai()) {
			System.out.print(b + " ");
		}
		System.out.println(allTile.removeAll(tile));
		for (byte b : allTile.getPai()) {
			System.out.print(b + " ");

		}

	}
}
