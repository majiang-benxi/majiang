package com.mahjong.server.game.enums;

/**
 * 操作类型枚举
 */
public enum EventEnum {
	
	/** 更新请求消息 */
    UPDATE_REQ(1),
	/** 更新响应消息 */
    UPDATE_RESP(2),
	
	/** 登录请求消息 */
    AUTH_REQ(3),
	/** 登录响应消息 */
    AUTH_RESP(4),
    
    /** 创建房间请求消息 */
	CREATE_ROOM_REQ(5),
	/** 创建房间响应消息 */
	CREATE_ROOM_RESP(6),
    
	/** 进入房间请求消息 */
	ROOM_ENTER_REQ(7),
	/** 进入房间响应消息 */
	ROOM_ENTER_RESP(8),
	/** 进入房间响应消息 */
	NEW_ENTER_RESP(9),
    
	/** 解散房间请求消息 */
	KILL_ROOM_REQ(10),
	/** 解散房间响应消息 */
	KILL_ROOM_NOTICE_RESP(11),
	/** 解散房间响应消息 */
	KILL_ROOM_RESP(12),
	
	/**一次出牌请求*/
	DISCARD_ONE_CARD_REQ(13),
	/** 一次出牌请求响应 */
	DISCARD_ONE_CARD_RESP(14),
	/**本剧战绩响应 */
	THIS_RECORD_SCORE_RESP(15),
	
	/**历史战绩请求*/
	HISTORY_RECORD_REQ(16),
	/**历史战绩响应 */
	HISTORY_RECORD_RESP(17),
	
	/**一次发消息请求*/
	SEND_MESG_REQ(18),
	/** 一次发消息响应 */
	SEND_MESG_RESP(19),
	/** 就绪之后的发牌响应 */
	DEAL_TILE_RESP(20),
	/** 赢牌响应**/
	WIN_TILE_RESP(21),
	/**
	 * 发牌响应
	 */
	DRAW_TILE_RESP(22),
	/**
	 * 询问玩家是否吃碰杠胡
	 */
	ASK_CHOICE_RESP(23),
	/**
	 * 非法动作响应信息
	 */
	ILLEGAL_ACTION_RESP(24);
	private int value;

	public int getValue() {
        return value;
    }

	EventEnum(int value) {
        this.value = value;
    }
}
