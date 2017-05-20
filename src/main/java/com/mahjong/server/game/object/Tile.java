package com.mahjong.server.game.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

public class Tile {
	private byte[] pai;// 存储相关的一组牌

	public Tile() {

	}

	public Tile(byte[] pai) {
		this.pai = pai;
	}

	public enum HuaSe {
		WAN(1), TIAO(2), BING(3), ZI(4);
		public int type;

		HuaSe(int type) {
			this.type = type;
		}
	}

	public static byte HUIPAI = 0x00;
	public static byte QIANG = 0x27;
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

	public static Set<Byte> getNotUsedJiangPai() {
		byte[] notUsedJiangPai = new byte[] { 0x02, 0x05, 0x08, 0x12, 0x15, 0x18, 0x22, 0x25, 0x28 };
		Set<Byte> set = new HashSet<Byte>();
		for (Byte notUsedJiang : notUsedJiangPai) {
			set.add(notUsedJiang);
		}
		return set;
	}

	public static Set<Byte>tile2Set(Tile tile){
		Set<Byte> set = new HashSet<Byte>();
		for (Byte pai : tile.getPai()) {
			set.add(pai);
		}
		return set;
	}
	public byte[] getPai() {
		return pai;
	}

	public void setPai(byte[] pai) {
		this.pai = pai;
	}

	@Override
	public Tile clone() {
		byte[] destPai = new byte[] {};
		System.arraycopy(this.pai, 0, destPai, 0, this.pai.length);
		Tile tile = new Tile();
		tile.setPai(destPai);
		return tile;
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
	//从开头发一定数量的牌
	public Tile dealTile(int size){
		Tile tile=new Tile();
		tile.pai = new byte[size];
		System.arraycopy(pai, 0, tile.getPai(), 0, size);
		byte[] shengYuPai = new byte[pai.length - size];
		System.arraycopy(pai, size, shengYuPai, 0, pai.length - size);
		pai = shengYuPai;
		return tile;
	}

	// 从开头发一定数量的牌
	public Tile dealBottomTile(int size) {
		Tile tile = new Tile();
		tile.pai = new byte[size];
		System.arraycopy(pai, pai.length - size, tile.getPai(), 0, size);
		byte[] shengYuPai = new byte[pai.length - size];
		System.arraycopy(pai, 0, shengYuPai, 0, pai.length - size);
		pai = shengYuPai;
		return tile;
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

	public static List<Byte> getJANGPai(Tile tile) {
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
			if (e.getValue() >= 2 && !getNotUsedJiangPai().contains(e.getKey())) {
				jiangPAI.add(e.getKey());
			}
		}
		return jiangPAI;
	}

	public static Tile getSortedHuaSePaiFromPai(Tile tile, HuaSe huaSe) {
		List<Byte>list=new ArrayList<Byte>();
		for (Byte pai : tile.getPai()) {
			int paiNum=(int)pai;
			if (huaSe == HuaSe.WAN && paiNum > 0 && pai < 10) {
				list.add(pai);
			} else if (huaSe == HuaSe.WAN && paiNum > 16 && pai < 26) {
				list.add(pai);
			} else if (huaSe == HuaSe.TIAO && paiNum > 32 && pai < 42) {
				list.add(pai);
			} else if (huaSe == HuaSe.ZI && paiNum > 48 && pai < 56) {
				list.add(pai);
			}
		}
		Collections.sort(list);
		byte[]pais=new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			pais[i]=list.get(i);	
		}

		Tile result = new Tile(pais);
		return result;
	}

	public static Tile getHuiPai(byte fanHui) {
		byte[] result = new byte[2];
		if (fanHui == 0x09) {
			result[0] = (byte) 0x09;
			result[1] = (byte) 0x01;
		} else if (fanHui == 0x19) {
			result[0] = (byte) 0x19;
			result[1] = (byte) 0x11;
		} else if (fanHui == 0x29) {
			result[0] = (byte) 0x29;
			result[1] = (byte) 0x21;
		} else if (fanHui == 0x52) {
			result[0] = (byte) 0x49;
			result[1] = (byte) 0x52;
		} else if (fanHui == 0x55) {
			result[0] = (byte) 0x53;
			result[1] = (byte) 0x55;
		} else {
			result[0] = fanHui;
			result[1] = (byte) (fanHui + 1);
		}
		return new Tile(result);
	}
	public static void main(String[] args) {
		Tile allTile = new Tile();
		allTile.setPai(Tile.getOneBoxMahjong());
		/**
		 * byte[] pai = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
		 * 0x08, 0x09 }; // 万（1-9） Tile tile = new Tile(); tile.setPai(pai);
		 * System.out.println(allTile.containsAll(tile)); for (byte b :
		 * allTile.getPai()) { System.out.print(b + " "); }
		 * System.out.println(allTile.removeAll(tile)); for (byte b :
		 * allTile.getPai()) { System.out.print(b + " "); }
		 **/
		/**
		 * Tile tile = allTile.dealTile(5); for (byte b : allTile.getPai()) {
		 * System.out.print(b + " "); } System.out.println("******"); for (byte
		 * b : tile.getPai()) { System.out.print(b + " "); }
		 **/
		Tile tile = allTile.dealBottomTile(5);
		for (byte b : allTile.getPai()) {
			System.out.print(b + " ");
		}
		System.out.println("******");
		for (byte b : tile.getPai()) {
			System.out.print(b + " ");
		}
		System.out.println("******");
	}
}
