package com.mahjong.server.netty.session;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

public class ClientSession {
	public static Map<String, ChannelHandlerContext> sessionMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
	public static Map<String, Date> sessionHeartBeatTimeMap = new ConcurrentHashMap<String, Date>();

}
