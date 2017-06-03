package com.mahjong.server.controller;

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
import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UserInfo;
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
		
		
		
		RoomOnLineVO roomOnLineVO = new RoomOnLineVO();
		
		roomOnLineVO.setRoomNum(325423);
		roomOnLineVO.setRoomState(1);
		
		String createTime = DateUtil.fromatDateToYYMMDDHHMMSS(new Date());
		
		roomOnLineVO.setRoomCreateTime(createTime);
		
		roomOnLineVO.setGameRuleStr("sw32t34");
		
		roomOnLineVO.setEastUid(1);
		roomOnLineVO.setEastUName("245325we");
		
		roomOnLineVO.setNorthUid(2);
		roomOnLineVO.setNorthUName("245325wwewe");
		
		roomOnLineVO.setWestUid(3);
		roomOnLineVO.setWestUName("142wa");
		
		roomOnLineVO.setSouthUid(4);
		roomOnLineVO.setSouthUName("32人微望轻");
		
		returnList.add(roomOnLineVO);
		returnList.add(roomOnLineVO);
		returnList.add(roomOnLineVO);
		returnList.add(roomOnLineVO);
		returnList.add(roomOnLineVO);
		
		//TODO
		
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
		
		
		List<UserInfo> userInfoList = dbService.selectAllUserLimit(userId,datemin,datemax,null,curP*eachCount,eachCount);
		if(CollectionUtils.isNotEmpty(userInfoList)){
			
			for(UserInfo userInfo : userInfoList){
				
				userInfo.setCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getCreateTime()));
				userInfo.setLastLoginTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getLastLoginTime()));
				userInfo.setCurrRoom(21321);
				returnList.add(userInfo);
			}
			
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
			killRoomRespModel.setMsg("解散成功！");
			
			protocolModel.setCommandId(EventEnum.KILL_ROOM_RESP.getValue());
			protocolModel.setBody(JSON.toJSONString(killRoomRespModel));
			
			for (PlayerInfo entry : roomContext.getGameContext().getTable().getPlayerInfos().values()) {
				String weixinmark = entry.getUserInfo().getWeixinMark();
				ChannelHandlerContext userCtx = ClientSession.sessionMap.get(weixinmark);
				userCtx.writeAndFlush(protocolModel);
				
				HouseContext.weixinIdToRoom.remove(weixinmark);
				
			}
			HouseContext.rommList.remove(roomNumber);
			
			RoomRecord roomRecordUpdate = new RoomRecord();
			
			List<RoomRecord> queryRecords = dbService.selectRoomRecordInfo(roomNumber, null, null);
			if(queryRecords!=null&&queryRecords.size()>0){
				RoomRecord roomRecord = queryRecords.get(0);
				roomRecordUpdate.setId(roomRecord.getId());
				roomRecordUpdate.setRoomState((byte) 3);
				dbService.updateRoomRecordInfoByPrimaryKey(roomRecordUpdate);
			}
			
		}
		
		return " success!";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
