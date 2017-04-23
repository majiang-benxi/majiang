package com.mahjong.server.service;

import java.util.List;

import com.mahjong.server.entity.ManageUser;
import com.mahjong.server.entity.MessageInfo;
import com.mahjong.server.entity.RoomCartChange;
import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UserActionScore;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.vo.UserLatestPlayRecord;

public interface DBService {
	
	/**
	 * 根据ID查用户
	 * @param userId  用户ID
	 * @return 记录ID
	 */
	public Integer insertUserInfoByID(UserInfo userInfo);
	
	/**
	 * 根据ID查用户
	 * @param userId  用户ID
	 * @return 用户信息
	 */
	public UserInfo selectUserInfoByID(Integer userId);
	/**
	 *  根据微信唯一标识查用户
	 * @param weiXinMark 微信唯一标识
	 * @return 用户信息
	 */
	public UserInfo selectUserInfoByWeiXinMark(String weiXinMark);
	/**
	 * 更新用户信息
	 * @param userInfo 用户信息
	 * @return 更新是否成功
	 */
	public boolean updateUserInfoById(UserInfo userInfo);
	/**
	 * 根据ID删除用户
	 * @param userId  用户ID
	 * @return 是否成功
	 */
	public boolean deleteUserInfoByID(Integer userId);
	/**
	 *  根据微信唯一标识删除用户
	 * @param weiXinMark 微信唯一标识
	 * @return 是否成功
	 */
	public boolean deleteUserInfoByWeiXinMark(String weiXinMark);
	
	

	/**
	 * 根据房间状态查房间记录信息
	 * @param id 数据库主键ID
	 * @return 房间记录信息
	 */
	RoomRecord selectRoomRecordInfoByID(Integer id);

	/**
	 * 根据房间状态查房间记录信息
	 *  @param roomNum：房间号；
	 *  @param creatorId：创建者ID；
	 *  @param roomState：房间状态,1:在线，2：等待，3：解散',
	 * @return 房间记录信息
	 */
	List<RoomRecord> selectRoomRecordInfo(Integer roomNum,Integer creatorId,Integer roomState);

	

	/**
	 * 加入一条房间记录信息
	 * @param queryRecord 房间记录信息
	 * @return 记录ID
	 */
	Integer insertRoomRecordInfo(RoomRecord queryRecord);

	/**
	 * 更新房间记录信息
	 * @param queryRecord 房间记录信息
	 * @return 成功如否
	 */
	boolean updateRoomRecordInfoByPrimaryKey(RoomRecord queryRecord);

	/**
	 * 删除房间记录信息
	 * @param recordId 房间记录信息ID
	 * @return 成功如否
	 */
	boolean deleteRoomRecordInfoByID(Integer recordId);
	
	
	
	
	
	
	
	
	
	/**
	 * 根据用户ID查用户当前房间记录信息
	 * @param userId  用户ID
	 * @return 用户房间记录信息
	 */
	List<UserRoomRecord> selectUserRoomRecordInfoByUserID(Integer userId);
	/**
	 * 根据用户ID查用户最新房间记录信息
	 * @param userId  用户ID
	 * @param topNum  最近多少条
	 * @return 用户房间记录信息
	 */
	List<UserRoomRecord> selectLatestUserRoomRecordInfo(Integer userId,Integer topNum);
	/**
	 * 插入一条用户房间记录信息
	 * @param userRoomRecord 用户房间记录信息
	 * @return 记录ID
	 */
	Integer insertUserRoomRecordInfo(UserRoomRecord userRoomRecord);
	
	/**
	 * 更新房间记录信息
	 * @param queryRecord 房间记录信息
	 * @return 成功如否
	 */
	boolean updateUserRoomRecordInfoPrimaryKey(UserRoomRecord userRoomRecord);

	/**
	 * 删除房间记录信息
	 * @param recordId 房间记录信息ID
	 * @return 成功如否
	 */
	boolean deleteUserRoomRecordInfoByID(Integer recordId);
	
	
	
	

	/**
	 * 记录用户在房间战绩点信息
	 * @param userActionScore 战绩点信息
	 * @return 记录ID
	 */
	Integer insertUserActionScoreInfo(UserActionScore userActionScore);

	/**
	 * 根据用户在房间玩牌记录ID，查询此次用户得分点列表
	 * @param recorId 玩牌记录ID
	 * @return 得分点列表 
	 */
	List<UserActionScore> selectUserActionScoreInfoByRecorId(Integer recorId);

	/**
	 * 更新用户战绩点信息
	 * @param userActionScore
	 * @return
	 */
	boolean updateUserActionScoreInfoPrimaryKey(UserActionScore userActionScore);

	/**
	 * 删除用户战绩点
	 * @param recordId 记录ID
	 * @return
	 */
	boolean deleteUserActionScoreInfoByID(Integer id);
	

	
	
	
	/**
	 * 查询用户最新战绩信息 (查询用户top10战绩)
	 * @param userId 用户ID
	 * @param topNum 最新条数
	 * @return 战绩信息
	 */
	List<UserLatestPlayRecord> selectUserLatestPlayRecord(Integer userId, Integer topNum);

	
	
	
	
	
	/**
	 * 查询广播公告信息
	 * @param mesType '消息类型，1：公告，2：广播',
	 * @param mesPosition '广播消息发放位置：1：大厅，2：房间，3：大厅房间同时(广播才有）',
	 * @param  mesgState '消息状态：0：未发送，1：已发送，2：删除',
	 * @return 广播公告信息
	 */
	List<MessageInfo> selectMessageInfo(Integer mesType, Integer mesPosition, Integer mesgState);

	/**
	 * 保存广播公告信息
	 * @param messageInfo 广播公告信息
	 * @return 主键ID
	 */
	Integer inserMessageInfo(MessageInfo messageInfo);
	
	/**
	 * 更新广播公告信息
	 * @param messageInfo 广播公告信息
	 * @return 更新是否成功
	 */
	boolean updateMessageInfoById(MessageInfo messageInfo);

	/**
	 * 删除广播公告信息
	 * @param id 主键ID
	 * @return 删除成功与否
	 */
	boolean deleteMessageInfoById(Integer id);

	/**
	 * 根据用户名查管理员用户
	 * @param userName 用户名
	 * @return 管理员用户
	 */
	ManageUser selectManageUserByUname(String userName);

	/**
	 * 更新管理员用户
	 * @param manageUser 管理员用户
	 * @return 更新是否成功
	 */
	boolean updateManageUserByID(ManageUser manageUser);

	/**
	 * 插入管理员用户记录
	 * @param manageUser 管理员用户记录
	 * @return 主键ID
	 */
	boolean insertManageUser(ManageUser manageUser);

	/**
	 * 删除管理员用户
	 * @param manageUserID 用户ID
	 * @return 删除是否成功
	 */
	boolean deleteManageUserByID(Integer manageUserID);

	/**
	 * 插入充值房卡记录
	 * @param roomCartChange 充值房卡记录
	 * @return 是否成功
	 */
	boolean insertRoomCartChange(RoomCartChange roomCartChange);

	/**
	 * 为用户冲房卡
	 * @param userId 用户ID
	 * @param userName 用户名
	 * @param cartNum 冲卡数
	 * @param manageUser 管理员用户
	 * @return 充值是否成功
	 */
	boolean insertRoomCart(Integer userId, String userName, Integer cartNum, ManageUser manageUser);
	
	
	
	
	
	

	

}
