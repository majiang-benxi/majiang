package com.mahjong.server.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 牌组类型。
 * 
 */
public class TileGroupTypeToName {
	
	private static Map<Integer,String> tileGroupTypeToName = new HashMap<Integer, String>();
	
	static{
		tileGroupTypeToName.put(0, "");
		tileGroupTypeToName.put(1, "吃");
		tileGroupTypeToName.put(2, "碰");
		tileGroupTypeToName.put(3, "补杠");
		tileGroupTypeToName.put(4, "暗杠");
		tileGroupTypeToName.put(5, "削");
		tileGroupTypeToName.put(6, "旋风杠");
		tileGroupTypeToName.put(7, "旋风杠");
		tileGroupTypeToName.put(8, "胡");
		tileGroupTypeToName.put(9, "");
	}

}