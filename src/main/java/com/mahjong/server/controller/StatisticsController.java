package com.mahjong.server.controller;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mahjong.server.entity.RoomCartChange;
import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.game.context.HouseContext;
import com.mahjong.server.game.context.RoomContext;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.game.enums.PlayerLocation;
import com.mahjong.server.game.object.PlayerInfo;
import com.mahjong.server.netty.model.KillRoomRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;
import com.mahjong.server.util.DateUtil;
import com.mahjong.server.vo.RoomOnLineVO;

import io.netty.channel.ChannelHandlerContext;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {
	

	@Autowired
	private DBService dbService;
	
	
	
	@RequestMapping(value = "/statistics")
	public ModelAndView statistics(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		Integer onlineRoomNum = HouseContext.onlineRoomNum.get();
		Integer playRoomNum = HouseContext.playRoomNum.get();
		Integer waitRoomNum = HouseContext.waitRoomNum.get();
		
		Integer onlineUserNum = HouseContext.onlineUserNum.get();
		Integer playUserNum = HouseContext.playUserNum.get();
		Integer waitUserNum = HouseContext.waitUserNum.get();
		
		modelAndView.addObject("onlineRoomNum", onlineRoomNum);
		modelAndView.addObject("playRoomNum", playRoomNum);
		modelAndView.addObject("waitRoomNum", waitRoomNum);
		modelAndView.addObject("onlineUserNum", onlineUserNum);
		modelAndView.addObject("playUserNum", playUserNum);
		modelAndView.addObject("waitUserNum", waitUserNum);
		
		modelAndView.setViewName("statisticsinfo");
		
		return modelAndView;
		
	}
	
	
	
	@RequestMapping(value = "/getRoomOnLineInfo")
	public ModelAndView getRoomOnLineInfo(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("onlineroominfo");
		
		return modelAndView;
		
	}
	@RequestMapping(value = "/getRoomOnLineList")
	public ModelAndView getRoomOnLineList(HttpServletRequest request, HttpServletResponse response) {
		
		
		String roomnum = request.getParameter("roomnum");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		if(StringUtils.isEmpty(roomnum)){
			roomnum = null;
		}
		
		if(StringUtils.isEmpty(datemin)){
			datemin = null;
		}
		if(StringUtils.isEmpty(datemax)){
			datemax = null;
		}
		
		if(StringUtils.isEmpty(eachPageCount)){
			eachPageCount = "10";
		}
		if(StringUtils.isEmpty(curPage)){
			curPage = "1";
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		List<RoomOnLineVO> returnList = new ArrayList<RoomOnLineVO>();
		
		Map<Integer, RoomContext> roomListMap = HouseContext.rommList;
		
		
		Integer totalcount = roomListMap.size();
		
		Set<Entry<Integer, RoomContext>>  entSet = roomListMap.entrySet();
		Iterator<Entry<Integer, RoomContext>> ite = entSet.iterator();
		
		Integer curP = Integer.parseInt(curPage)-1;
		Integer eachCount = Integer.parseInt(eachPageCount);
		Integer start = curP*eachCount;
		
		int index = 0;
		
		while(ite.hasNext()){
			
			
			Entry<Integer, RoomContext> ent = ite.next();
			
			Integer roomNum = ent.getKey();
			
			if(roomnum!=null&&Integer.parseInt(roomnum)!=roomNum.intValue()){
				continue;
			}
			
			
			RoomContext roomContext = ent.getValue();
			
			RoomOnLineVO roomOnLineVO = new RoomOnLineVO();
			
			roomOnLineVO.setRoomNum(roomNum);
			roomOnLineVO.setRoomState(roomContext.getRoomStatus().getCode());
			
			String createTime = DateUtil.fromatDateToYYMMDDHHMMSS(roomContext.getCreateTime());
			
			roomOnLineVO.setRoomCreateTime(createTime);
			
			if(datemin!=null && createTime.compareTo(datemin)<0){
				continue;
			}
			if(datemax!=null && createTime.compareTo(datemax)>0){
				continue;
			}
			
			
			
			if(index<start){
				continue;
			}
			
			if(index>=(start+eachCount)){
				break;
			}
			
			
			roomOnLineVO.setGameRuleStr(roomContext.getGameContext().getGameStrategy().toString());
			
			for (PlayerInfo entry : roomContext.getGameContext().getTable().getPlayerInfos().values()) {
				
				UserInfo userInfo = null;
				if(entry==null || (userInfo = entry.getUserInfo())==null){
					continue;
				}
				
				if(entry.getUserLocation().intValue()==PlayerLocation.EAST.getCode()){
					
					roomOnLineVO.setEastUid(userInfo.getId());
					roomOnLineVO.setEastUName(userInfo.getNickName());
					
				}else if(entry.getUserLocation().intValue()==PlayerLocation.NORTH.getCode()){
					
					roomOnLineVO.setNorthUid(userInfo.getId());
					roomOnLineVO.setNorthUName(userInfo.getNickName());
					
				}else if(entry.getUserLocation().intValue()==PlayerLocation.WEST.getCode()){
					
					roomOnLineVO.setWestUid(userInfo.getId());
					roomOnLineVO.setWestUName(userInfo.getNickName());
					
				}else if(entry.getUserLocation().intValue()==PlayerLocation.SOUTH.getCode()){
					
					roomOnLineVO.setSouthUid(userInfo.getId());
					roomOnLineVO.setSouthUName(userInfo.getNickName());
				}
				
			}
			
			returnList.add(roomOnLineVO);
			
			
		}
		
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
 		modelAndView.addObject("newInfoList", returnList);
		
		
		modelAndView.setViewName("ajax/roomonlinelist");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/getonlineuserinfo")
	public ModelAndView getonlineuserinfo(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("onlineuserinfo");
		
		return modelAndView;
		
	}
	
	
	
	@RequestMapping(value = "/getOnLineUserList")
	public ModelAndView getOnLineUserList(HttpServletRequest request, HttpServletResponse response) {
		
		
		String userId = request.getParameter("userId");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		if(StringUtils.isEmpty(userId)){
			userId = null;
		}
		
		if(StringUtils.isEmpty(datemin)){
			datemin = null;
		}
		if(StringUtils.isEmpty(datemax)){
			datemax = null;
		}
		
		if(StringUtils.isEmpty(eachPageCount)){
			eachPageCount = "10";
		}
		if(StringUtils.isEmpty(curPage)){
			curPage = "1";
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		List<UserInfo> returnList = new ArrayList<UserInfo>();
		
		Map<String, UserInfo> roomListMap = HouseContext.weixinIdToUserInfo;
		
		
		Integer totalcount = roomListMap.size();
		
		Set<Entry<String, UserInfo>>  entSet = roomListMap.entrySet();
		Iterator<Entry<String, UserInfo>> ite = entSet.iterator();
		
		Integer curP = Integer.parseInt(curPage)-1;
		Integer eachCount = Integer.parseInt(eachPageCount);
		Integer start = curP*eachCount;
		
		int index = 0;
		
		while(ite.hasNext()){
			
			
			Entry<String, UserInfo> ent = ite.next();
			
			String weixinId = ent.getKey();
			UserInfo userInfo = ent.getValue();
			
			if(userId!=null&&Integer.parseInt(userId)!=userInfo.getId().intValue()){
				continue;
			}
			
			
			String createTime = DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getLastLoginTime());
			
			if(datemin!=null && createTime.compareTo(datemin)<0){
				continue;
			}
			if(datemax!=null && createTime.compareTo(datemax)>0){
				continue;
			}
			
			
			
			if(index<start){
				continue;
			}
			
			if(index>=(start+eachCount)){
				break;
			}
			
			
			RoomContext roomContext = HouseContext.weixinIdToRoom.get(weixinId);
			
			if(roomContext!=null){
				userInfo.setCurrRoom(roomContext.getRoomNum());
			}
			
			userInfo.setCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getCreateTime()));
			userInfo.setLastLoginTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getLastLoginTime()));
			
			returnList.add(userInfo);
			
		}
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
		modelAndView.addObject("newInfoList", returnList);
		
		
		modelAndView.setViewName("ajax/useronlinelist");
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/killRoom")
	@ResponseBody
	public <K> Object killRoom(HttpServletRequest request, HttpServletResponse response) {
		
		String roomNum = request.getParameter("roomNum");
		
		Integer roomNumber = Integer.parseInt(roomNum);
		
		ProtocolModel protocolModel = new ProtocolModel();
		
		if(HouseContext.rommList.containsKey(roomNumber)){
			
			RoomContext roomContext = HouseContext.rommList.get(roomNumber);
			
			KillRoomRespModel killRoomRespModel = new KillRoomRespModel();
			killRoomRespModel.setResult(true);
			killRoomRespModel.setMsg("管理员强制解散成功！");
			
			protocolModel.setCommandId(EventEnum.KILL_ROOM_RESP.getValue());
			protocolModel.setBody(JSON.toJSONString(killRoomRespModel));
			
			for (PlayerInfo entry : roomContext.getGameContext().getTable().getPlayerInfos().values()) {
				String weixinmark = entry.getUserInfo().getWeixinMark();
				ChannelHandlerContext userCtx = ClientSession.sessionMap.get(weixinmark);
				userCtx.writeAndFlush(protocolModel);
				
				HouseContext.weixinIdToRoom.remove(weixinmark);
				
			}
			
			HouseContext.rommList.remove(roomContext.getRoomNum());
			
			HouseContext.playRoomNum.decrementAndGet();
			HouseContext.playUserNum.addAndGet(-4);
			
			for(Entry<PlayerLocation, PlayerInfo>  ent : roomContext.getGameContext().getTable().getPlayerInfos().entrySet()){
				PlayerInfo playerInfo = ent.getValue();
				HouseContext.weixinIdToRoom.remove(playerInfo.getUserInfo().getWeixinMark());
				
				UserRoomRecord userRoomRecord = new UserRoomRecord();
				
				if(playerInfo.getUserLocation().intValue() == PlayerLocation.NORTH.getCode()){
					userRoomRecord.setUserDirection((byte) 4);
					
				}else if(playerInfo.getUserLocation().intValue() == PlayerLocation.WEST.getCode()){
					userRoomRecord.setUserDirection((byte) 3);
					
				}else if(playerInfo.getUserLocation().intValue() == PlayerLocation.SOUTH.getCode()){
					userRoomRecord.setUserDirection((byte) 2);
					
				}
				
				Date now = new Date();
				
				ChannelHandlerContext userCtx = ClientSession.sessionMap.get(playerInfo.getUserInfo().getWeixinMark());
				
				InetSocketAddress socketAddr = (InetSocketAddress) userCtx.channel().remoteAddress();
				
				userRoomRecord.setHuTimes(0);
				userRoomRecord.setLoseTimes(0);
				userRoomRecord.setOperateCause("解散房间，离开");
				userRoomRecord.setOperateType((byte) 2);
				userRoomRecord.setOperateTime(now);
				
				userRoomRecord.setRoomNum(roomContext.getRoomNum());
				
				userRoomRecord.setRoomRecordId(roomContext.getRoomRecordID());
				
				userRoomRecord.setUserId(playerInfo.getUserInfo().getId());
				userRoomRecord.setUserIp(socketAddr.getAddress().getHostAddress());
				userRoomRecord.setWinTimes(0);
				
				Integer userRoomRecordId = dbService.insertUserRoomRecordInfo(userRoomRecord);
				
			}
			
			RoomRecord roomRecord = new RoomRecord();
			roomRecord.setId(roomContext.getRoomRecordID());
			roomRecord.setRoomState((byte) 3);
			boolean flg = dbService.updateRoomRecordInfoByPrimaryKey(roomRecord);
			
		}
		
		return " success!";
		
	}
	
	
	
	
	
	@RequestMapping(value = "/roomCardChangeInfo")
	public ModelAndView roomCardChangeInfo(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("roomcardchangeinfo");
		
		return modelAndView;
		
	}
	
	
	
	@RequestMapping(value = "/getRoomCardCahngeInfoList")
	public ModelAndView getRoomCardCahngeInfoList(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String changeType = request.getParameter("changeType");
		String userid = request.getParameter("userid");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		List<RoomCartChange> newInfoList = new ArrayList<RoomCartChange>();
		
		Integer userID = null;
		
		if(StringUtils.isEmpty(userid)){
			userid = null;
		}else{
			userID = Integer.parseInt(userid);
		}
		
		Integer changeTypeNum = null;
		
		if(StringUtils.isEmpty(changeType)){
			changeType = null;
		}else{
			changeTypeNum = Integer.parseInt(changeType);
		}
		if(StringUtils.isEmpty(datemin)){
			datemin = null;
		}
		if(StringUtils.isEmpty(datemax)){
			datemax = null;
		}
		
		if(StringUtils.isEmpty(eachPageCount)){
			eachPageCount = "10";
		}
		if(StringUtils.isEmpty(curPage)){
			curPage = "1";
		}
		
		Integer curP = Integer.parseInt(curPage)-1;
		Integer eachCount = Integer.parseInt(eachPageCount);
		
		int totalcount = dbService.selectRoomCardChangeInfoCount(userID,changeTypeNum, datemin, datemax);
		
		List<RoomCartChange> roomRecordInfoList = dbService.selectRoomCardChangeInfoLimit(userID,changeTypeNum, datemin, datemax ,curP*eachCount,eachCount);
		if(CollectionUtils.isNotEmpty(roomRecordInfoList)){
			
			for(RoomCartChange roomRecordInfo : roomRecordInfoList){
				
				roomRecordInfo.setChangeTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(roomRecordInfo.getChangeTime()));
				
				newInfoList.add(roomRecordInfo);
			}
			
		}
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
 		modelAndView.addObject("newInfoList", newInfoList);
		
		modelAndView.setViewName("ajax/roomcardchangeinfolist");
		
		return modelAndView;
		
	}
	
}
