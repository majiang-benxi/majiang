package com.mahjong.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahjong.server.dao.ManageUserMapper;
import com.mahjong.server.dao.MessageInfoMapper;
import com.mahjong.server.dao.RoomCartChangeMapper;
import com.mahjong.server.dao.RoomRecordMapper;
import com.mahjong.server.dao.UpdateInfoMapper;
import com.mahjong.server.dao.UserActionScoreMapper;
import com.mahjong.server.dao.UserInfoMapper;
import com.mahjong.server.dao.UserRoomRecordMapper;
import com.mahjong.server.entity.ManageUser;
import com.mahjong.server.entity.MessageInfo;
import com.mahjong.server.entity.RoomCartChange;
import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UpdateInfo;
import com.mahjong.server.entity.UserActionScore;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.service.DBService;
import com.mahjong.server.vo.UserLatestPlayRecord;
import com.mahjong.server.vo.UserRecordScoreVO;
import com.mahjong.server.vo.UserRoomActScore;

@Service
public class DBServiceImpl implements DBService {
	
	@Autowired
	ManageUserMapper manageUserMapper;
	@Autowired
	MessageInfoMapper messageInfoMapper;
	@Autowired
	RoomCartChangeMapper roomCartChangeMapper;
	@Autowired
	RoomRecordMapper roomRecordMapper;
	@Autowired
	UserActionScoreMapper userActionScoreMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	UserRoomRecordMapper userRoomRecordMapper;
	@Autowired
	UpdateInfoMapper updateInfoMapper;
	
	/********用户**********/
	
	@Override
	public Integer insertUserInfo(UserInfo userInfo) {
		 userInfoMapper.insertSelective(userInfo);
		return userInfo.getId();
	}
	@Override
	public UserInfo selectUserInfoByID(Integer userId) {
		return userInfoMapper.selectByPrimaryKey(userId);
	}
	@Override
	public UserInfo selectUserInfoByWeiXinMark(String weiXinMark) {
		return userInfoMapper.selectUserInfoByWeiXinMark(weiXinMark);
	}
	@Override
	public boolean updateUserInfoById(UserInfo userInfo) {
		return userInfoMapper.updateByPrimaryKeySelective(userInfo)>0;
	}
	@Override
	public boolean deleteUserInfoByID(Integer userId) {
		return userInfoMapper.deleteUserInfoByID(userId)>0;
	}
	@Override
	public boolean deleteUserInfoByWeiXinMark(String weiXinMark) {
		return userInfoMapper.deleteUserInfoByWeiXinMark(weiXinMark)>0;
	}
	
	
	/********房间**********/
	@Override
	public Integer insertRoomRecordInfo(RoomRecord queryRecord) {
		roomRecordMapper.insertSelective(queryRecord);
		return queryRecord.getId();
	}
	@Override
	public RoomRecord selectRoomRecordInfoByID(Integer id) {
		return roomRecordMapper.selectByPrimaryKey(id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RoomRecord> selectRoomRecordInfo(Integer roomNum,Integer creatorId,Integer roomState) {
		RoomRecord queryRecord = new RoomRecord();
		if(roomNum!=null&&roomNum>0){
			queryRecord.setRoomNum(roomNum);
		}
		
		if(creatorId!=null&&creatorId>0){
			queryRecord.setCreatorId(creatorId);	
		}
				
		if(roomState!=null&&roomState>0){
			queryRecord.setRoomState(new Byte(roomState.byteValue()));
		}

		return roomRecordMapper.selectByCdt(queryRecord);
	}
	@Override
	public boolean updateRoomRecordInfoByPrimaryKey(RoomRecord queryRecord) {
		return roomRecordMapper.updateByPrimaryKeySelective(queryRecord)>0;
	}
	@Override
	public boolean deleteRoomRecordInfoByID(Integer recordId) {
		return roomRecordMapper.deleteRoomRecordInfoByID(recordId)>0;
	}
	
	/********用户房间记录**********/
	@Override
	public Integer insertUserRoomRecordInfo(UserRoomRecord userRoomRecord) {
		 userRoomRecordMapper.insertSelective(userRoomRecord);
		return userRoomRecord.getId();
	}
	@Override
	public List<UserRoomRecord> selectUserRoomRecordInfoByUserID(Integer userId) {
		return userRoomRecordMapper.selectUserRoomRecordInfoByUserID(userId);
	}
	@Override
	public List<UserRoomRecord> selectLatestUserRoomRecordInfo(Integer userId, Integer topNum) {
		if(topNum==null){
			topNum = 10;
		}
		return userRoomRecordMapper.selectLatestUserRoomRecordInfo(userId,topNum);
	}
/*	@Override
	public boolean updateUserRoomRecordInfoPrimaryKey(UserRoomRecord userRoomRecord) {
		return userRoomRecordMapper.updateByPrimaryKeySelective(userRoomRecord)>0;
	}*/
	@Override
	public boolean deleteUserRoomRecordInfoByID(Integer recordId) {
		return userRoomRecordMapper.deleteByPrimaryKey(recordId)>0;
	}
	
	
	/********用户房间得分记录**********/
	@Override
	public Integer insertUserActionScoreInfo(UserActionScore userActionScore) {
		 userActionScoreMapper.insertSelective(userActionScore);
		return userActionScore.getId();
	}
	@Override
	public List<UserActionScore> selectUserActionScoreInfoByRecorId(Integer userId) {
		return userActionScoreMapper.selectUserActionScoreInfoByRecordId(userId);
	}
	@Override
	public boolean updateUserActionScoreInfoPrimaryKey(UserActionScore userActionScore) {
		return userActionScoreMapper.updateByPrimaryKeySelective(userActionScore)>0;
	}
	@Override
	public boolean deleteUserActionScoreInfoByID(Integer recordId) {
		return userActionScoreMapper.deleteByPrimaryKey(recordId)>0;
	}
	
	
	/****************查询用户top10战绩*******************/
	@Override
	public List<UserLatestPlayRecord> selectUserLatestPlayRecord(Integer userId,Integer topNum){
		
		List<UserLatestPlayRecord> returnRecordList = new ArrayList<UserLatestPlayRecord>();
		
		List<UserActionScore> latestRecords = selectLatestUserRoomRecordScoreInfo(userId,topNum);
		
		
		Map<Integer,RoomRecord> roomRecordMap = new HashMap<Integer, RoomRecord>();
		
		if(CollectionUtils.isNotEmpty(latestRecords)){
			for(UserActionScore userRoomScore : latestRecords){
				
				Integer roomRecordId = userRoomScore.getRoomRecordId();
				
				RoomRecord roomRecord = null;
				if(roomRecordMap.containsKey(roomRecordId)){
					roomRecord = roomRecordMap.get(roomRecordId);
				}else{
					roomRecord = selectRoomRecordInfoByID(roomRecordId);
					roomRecordMap.put(roomRecordId, roomRecord);
				}
				
				UserLatestPlayRecord userLatestPlayRecord = new UserLatestPlayRecord();
				userLatestPlayRecord.setRoomRecord(roomRecord);
				
				UserRoomActScore userRoomActScore = new UserRoomActScore();
				userRoomActScore.setActionScore(userRoomScore.getActionScore());
				userRoomActScore.setWinActionTypes(userRoomScore.getWinActionTypes());
				
				returnRecordList.add(userLatestPlayRecord);
					
			}
			
		}
		
		return returnRecordList;
	}
	
	
	private List<Integer> selectLatestRoomRecordIds(Integer userId, Integer topNum) {
		return userActionScoreMapper.selectLatestRoomRecordIds( userId,  topNum);
	}
	private List<UserActionScore> selectLatestUserRoomRecordScoreInfo(Integer userId, Integer topNum) {
		return userActionScoreMapper.selectLatestUserRoomRecordScoreInfo(userId,topNum);
	}
	/****************查询用户top10战绩*******************/
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MessageInfo> selectMessageInfo(Integer mesType,Integer mesPosition, Integer mesgState){
		
		MessageInfo queryRecord = new MessageInfo();
		
		if(mesType!=null&&mesType>0){
			queryRecord.setMesType(mesType.byteValue());	
		}
		
		if(mesPosition!=null&&mesPosition>0){
			queryRecord.setMesPosition(mesPosition.byteValue());
		}
		
		if(mesgState!=null&&mesgState>0){
			queryRecord.setState(mesgState.byteValue());
		}
		
		return messageInfoMapper.selectByCdt(queryRecord);
		
	}
	
	
	@Override
	public MessageInfo selectMessageInfoById(Integer mesId){
		return messageInfoMapper.selectByPrimaryKey(mesId);
	}
	
	@Override
	public Integer inserMessageInfo(MessageInfo messageInfo){
		 messageInfoMapper.insertSelective(messageInfo);
		return messageInfo.getId();
	}
	
	@Override
	public boolean updateMessageInfoById(MessageInfo messageInfo){
		return messageInfoMapper.updateByPrimaryKeySelective(messageInfo)>0;
	}
	
	@Override
	public boolean deleteMessageInfoById(Integer id){
		return messageInfoMapper.deleteMessageInfoById(id)>0;
	}
	
	
	
	/***********************管理员用户***********************/
	@Override
	public ManageUser selectManageUserByUname(String userName){
		return manageUserMapper.selectManageUserByUname(userName);
	}
	
	@Override
	public ManageUser selectManageUserByID(Integer uid){
		return manageUserMapper.selectByPrimaryKey(uid);
	}
	
	@Override
	public boolean updateManageUserByID(ManageUser manageUser){
		return manageUserMapper.updateByPrimaryKeySelective(manageUser)>0;
	}
	
	
	@Override
	public boolean insertManageUser(ManageUser manageUser){
		ManageUser existManageUser = selectManageUserByUname(manageUser.getUname());
		if(existManageUser!=null&&existManageUser.getState()!=0){
			return false;
		}else{
			return manageUserMapper.insertSelective(manageUser)>0;
		}
	}
		
	@Override
	public boolean deleteManageUserByID(Integer manageUserID){
		return manageUserMapper.updateUserSate(manageUserID,0)>0;
	}
	
	/************************充值记录**********************/
	
	@Override
	public boolean insertRoomCartChange(RoomCartChange roomCartChange){
		return roomCartChangeMapper.insertSelective(roomCartChange)>0;
	}
	
	
	/************************充值房卡**********************/
	

	
	@Override
	public UpdateInfo selectUpdateInfoByDeviceType(Integer deviceType,float version ) {
		List<UpdateInfo> updateInfos =  updateInfoMapper.selectUpdateInfoByDeviceType(deviceType,version);
		if(CollectionUtils.isNotEmpty(updateInfos)){
			return updateInfos.get(0);
		}
		return null;
	}
	@Override
	public Integer inserUpdateInfo(UpdateInfo updateInfo) {
		 updateInfoMapper.insertSelective(updateInfo);
		return updateInfo.getId();
	}
	@Override
	public boolean updateUpdateInfoById(UpdateInfo updateInfo) {
		return updateInfoMapper.updateByPrimaryKeySelective(updateInfo)>0;
	}
	
	@Override
	public List<ManageUser> selectAllManageUserLimit(String datemin,String datemax,String searchUname,Integer start,Integer count) {
		return manageUserMapper.selectAllManageUserLimit(datemin,datemax,searchUname,start, count);	
	}
	@Override
	public int selectAllManageUserCount(String datemin,String datemax,String searchUname) {
		return manageUserMapper.selectAllManageUserCount(datemin,datemax,searchUname);	
	}
	@Override
	public void updateAdminUserSate(Integer uid, Integer tostate) {
		manageUserMapper.updateUserSate(uid, tostate);
	}
	@Override
	public int selectAllUserCount(String uid, String datemin, String datemax, String searchUname) {
		return userInfoMapper.selectAllUserCount(uid, datemin, datemax, searchUname);
	}
	@Override
	public List<UserInfo> selectAllUserLimit(String uid,String datemin, String datemax, String searchUname, Integer startIndex,	Integer eachCount) {
		return userInfoMapper.selectAllUserLimit(uid, datemin, datemax, searchUname, startIndex, eachCount);
	}
	@Override
	public int getUserPlayRecordInfoCount(String uid, String roomNum, String datemin, String datemax) {
		return userRoomRecordMapper.getUserPlayRecordInfoCount( uid,  roomNum,  datemin,  datemax);
	}
	@Override
	public List<UserRoomRecord> getUserPlayRecordInfoLimit(String uid, String roomNum, String datemin, String datemax, Integer startIndex, Integer eachCount) {
		return userRoomRecordMapper.getUserPlayRecordInfoLimit( uid,roomNum, datemin, datemax,  startIndex, eachCount);
	}
	@Override
	public List<RoomRecord> selectRoomRecordInfoList(Set<Integer> recordIdList) {
		return roomRecordMapper.selectRoomRecordInfoList(recordIdList);
	}
	@Override
	public List<UserActionScore> selectUserActionScoreInfos(Set<Integer> userRoomRecordIdList) {
		return userActionScoreMapper.selectUserActionScoreInfos(userRoomRecordIdList);
	}
	@Override
	public int getUserScoreInfoInfoCount(String uid, String roomNum, String datemin, String datemax) {
		return userActionScoreMapper.getUserPlayRecordInfoCount( uid,  roomNum,  datemin,  datemax);
	}
	
	@Override
	public List<UserRecordScoreVO> getUserScoreInfoInfoListLimit(String uid, String roomNum, String datemin,
			String datemax, Integer startIndex, Integer eachCount) {
		return userActionScoreMapper.getUserPlayRecordInfoLimit( uid,roomNum, datemin, datemax,  startIndex, eachCount);
	}
	@Override
	public int selectRoomRecordInfoCount(String roomNum, String datemin, String datemax) {
		return roomRecordMapper.selectRoomRecordInfoCount( roomNum,  datemin,  datemax);
	}
	@Override
	public List<RoomRecord> selectRoomRecordInfoLimit(String roomNum, String datemin, String datemax, Integer start,
			Integer eachCount) {
		return roomRecordMapper.selectRoomRecordInfoLimit( roomNum,  datemin,  datemax, start, eachCount);
	}
	@Override
	public List<UserRoomRecord> selectUserRoomRecordInfoByRoomId(Integer roomid) {
		return userRoomRecordMapper.selectUserRoomRecordInfoByRoomId(roomid);
	}
	@Override
	public int selectMessageInfoCount(Integer msgPositionnum,Integer mesgstate, String datemin, String datemax) {
		return messageInfoMapper.selectMessageInfoCount(msgPositionnum, mesgstate,  datemin,  datemax);
	}
	@Override
	public List<MessageInfo> selectMessageInfoLimit(Integer msgPositionnum,Integer mesgstate, String datemin, String datemax, Integer start,
			Integer eachCount) {
		return messageInfoMapper.selectMessageInfoLimit(msgPositionnum, mesgstate,  datemin,  datemax, start, eachCount);
	}
	
	
	@Override
	public int selectRoomCardChangeInfoCount(Integer userID,Integer changeTypeNum, String datemin, String datemax) {
		return roomCartChangeMapper.selectRoomCardChangeInfoCount( userID,changeTypeNum,  datemin,  datemax);
	}
	@Override
	public List<RoomCartChange> selectRoomCardChangeInfoLimit(Integer userID,Integer changeTypeNum, String datemin, String datemax, int start,
			Integer eachCount) {
		return roomCartChangeMapper.selectRoomCardChangeInfoLimit( userID,changeTypeNum,  datemin,  datemax, start, eachCount);
	}
	@Override
	public UserRoomRecord selectUserRoomRecordInfoByID(Integer userRoomRecordInfoID) {
		return userRoomRecordMapper.selectByPrimaryKey(userRoomRecordInfoID);
	}
	@Override
	public void updateUserRoomRecordInfoPrimaryKey(UserRoomRecord winuserRoomRecForUpdate) {
		userRoomRecordMapper.updateByPrimaryKeySelective(winuserRoomRecForUpdate);
	}
	
	

}
