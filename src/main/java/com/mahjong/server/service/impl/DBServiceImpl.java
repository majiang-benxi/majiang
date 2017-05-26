package com.mahjong.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.mahjong.server.entity.ManageUserExample;
import com.mahjong.server.entity.MessageInfo;
import com.mahjong.server.entity.RoomCartChange;
import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UpdateInfo;
import com.mahjong.server.entity.UserActionScore;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.service.DBService;
import com.mahjong.server.vo.UserLatestPlayRecord;

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
		return userInfoMapper.insert(userInfo);
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
		return userInfoMapper.updateByPrimaryKey(userInfo)>0;
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
		return roomRecordMapper.insert(queryRecord);
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
		return roomRecordMapper.updateByPrimaryKey(queryRecord)>0;
	}
	@Override
	public boolean deleteRoomRecordInfoByID(Integer recordId) {
		return roomRecordMapper.deleteRoomRecordInfoByID(recordId)>0;
	}
	
	/********用户房间记录**********/
	@Override
	public Integer insertUserRoomRecordInfo(UserRoomRecord userRoomRecord) {
		return userRoomRecordMapper.insert(userRoomRecord);
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
	@Override
	public boolean updateUserRoomRecordInfoPrimaryKey(UserRoomRecord userRoomRecord) {
		return userRoomRecordMapper.updateByPrimaryKey(userRoomRecord)>0;
	}
	@Override
	public boolean deleteUserRoomRecordInfoByID(Integer recordId) {
		return userRoomRecordMapper.deleteByPrimaryKey(recordId)>0;
	}
	
	
	/********用户房间得分记录**********/
	@Override
	public Integer insertUserActionScoreInfo(UserActionScore userActionScore) {
		return userActionScoreMapper.insert(userActionScore);
	}
	@Override
	public List<UserActionScore> selectUserActionScoreInfoByRecorId(Integer userId) {
		return userActionScoreMapper.selectUserActionScoreInfoByRecordId(userId);
	}
	@Override
	public boolean updateUserActionScoreInfoPrimaryKey(UserActionScore userActionScore) {
		return userActionScoreMapper.updateByPrimaryKey(userActionScore)>0;
	}
	@Override
	public boolean deleteUserActionScoreInfoByID(Integer recordId) {
		return userActionScoreMapper.deleteByPrimaryKey(recordId)>0;
	}
	
	
	/****************查询用户top10战绩*******************/
	@Override
	public List<UserLatestPlayRecord> selectUserLatestPlayRecord(Integer userId,Integer topNum){
		List<UserLatestPlayRecord> returnRecordList = new ArrayList<UserLatestPlayRecord>();
		List<UserRoomRecord> latestRecords = selectLatestUserRoomRecordInfo(userId,topNum);
		if(CollectionUtils.isNotEmpty(latestRecords)){
			for(UserRoomRecord userRoomRecord : latestRecords){
				UserLatestPlayRecord userLatestPlayRecord = new UserLatestPlayRecord();
				List<UserActionScore>  actionRecordList = selectUserActionScoreInfoByRecorId(userRoomRecord.getId());
				RoomRecord roomRecord = selectRoomRecordInfoByID(userRoomRecord.getRoomRecordId());
				userLatestPlayRecord.setRoomRecord(roomRecord);
				userLatestPlayRecord.setUserRoomRecord(userRoomRecord);
				userLatestPlayRecord.setUserActionScoreList(actionRecordList);
				
				returnRecordList.add(userLatestPlayRecord);
			}
		}
		return returnRecordList;
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
	public Integer inserMessageInfo(MessageInfo messageInfo){
		return messageInfoMapper.insert(messageInfo);
	}
	
	@Override
	public boolean updateMessageInfoById(MessageInfo messageInfo){
		return messageInfoMapper.updateByPrimaryKey(messageInfo)>0;
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
		return manageUserMapper.updateByPrimaryKey(manageUser)>0;
	}
	
	
	@Override
	public boolean insertManageUser(ManageUser manageUser){
		ManageUser existManageUser = selectManageUserByUname(manageUser.getUname());
		if(existManageUser!=null&&existManageUser.getState()!=0){
			return false;
		}else{
			return manageUserMapper.insert(manageUser)>0;
		}
	}
		
	@Override
	public boolean deleteManageUserByID(Integer manageUserID){
		return manageUserMapper.updateUserSate(manageUserID,0)>0;
	}
	
	/************************充值记录**********************/
	
	@Override
	public boolean insertRoomCartChange(RoomCartChange roomCartChange){
		return roomCartChangeMapper.insert(roomCartChange)>0;
	}
	
	
	/************************充值房卡**********************/
	

	@Override
	public boolean insertRoomCart(Integer userId,String userName,Integer cartNum,ManageUser manageUser){
		
		Integer rest = userInfoMapper.updateUserRoomCard(userId,cartNum);
		
		RoomCartChange roomCartChange = new RoomCartChange();
		roomCartChange.setChangeNum(cartNum);
		roomCartChange.setChangeTime(new Date());
		roomCartChange.setIsSuccess((byte)(rest>0?1:0));
		roomCartChange.setManageName(manageUser.getNickName());
		roomCartChange.setManageUserId(roomCartChange.getId());
		roomCartChange.setUserId(userId);
		roomCartChange.setUserName(userName);
		
		roomCartChangeMapper.insert(roomCartChange);
		
		return rest>0;
	}
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
		return updateInfoMapper.insertSelective(updateInfo);
	}
	@Override
	public boolean updateUpdateInfoById(UpdateInfo updateInfo) {
		return updateInfoMapper.updateByPrimaryKey(updateInfo)>0;
	}
	
	@Override
	public List<ManageUser> selectAllManageUser() {
		ManageUserExample manageUserExample = new ManageUserExample();
		return manageUserMapper.selectByExample(manageUserExample);	
	}
	@Override
	public void updateAdminUserSate(Integer uid, Integer tostate) {
		manageUserMapper.updateUserSate(uid, tostate);
	}
	
	

}
