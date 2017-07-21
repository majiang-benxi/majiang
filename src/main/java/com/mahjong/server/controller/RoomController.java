package com.mahjong.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mahjong.server.entity.RoomRecord;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.service.DBService;
import com.mahjong.server.util.DateUtil;

@Controller
@RequestMapping(value = "room")
public class RoomController {
	@Autowired
	private DBService dbService;
	
	@RequestMapping(value = "/roomrecordinfo")
	public ModelAndView getAdminUser(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("roomrecordinfo");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/getRoomRecordInfoList")
	public ModelAndView getRoomRecordInfoList(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String roomNum = request.getParameter("roomNum");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		List<RoomRecord> newInfoList = new ArrayList<RoomRecord>();
		
		if(StringUtils.isEmpty(roomNum)){
			roomNum = null;
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
		
		int totalcount = dbService.selectRoomRecordInfoCount(roomNum, datemin, datemax);
		
		List<RoomRecord> roomRecordInfoList = dbService.selectRoomRecordInfoLimit(roomNum, datemin, datemax ,curP*eachCount,eachCount);
		if(CollectionUtils.isNotEmpty(roomRecordInfoList)){
			
			for(RoomRecord roomRecordInfo : roomRecordInfoList){
				
				List<UserRoomRecord> userInfoList = dbService.selectUserRoomRecordInfoByRoomId(roomRecordInfo.getId());
				
				if(CollectionUtils.isNotEmpty(userInfoList)){
					
					//1：东，2：南：3：西，4：北',
					for(UserRoomRecord userRoomRecord : userInfoList){
						if(userRoomRecord.getUserDirection()==null){
							continue;
						}
						
						if(userRoomRecord.getUserDirection()==1&&roomRecordInfo.getEastUid()==null){
							roomRecordInfo.setEastUid(userRoomRecord.getUserId());
						}else if(userRoomRecord.getUserDirection()==2&&roomRecordInfo.getSouthUid()==null){
							roomRecordInfo.setSouthUid(userRoomRecord.getUserId());
						}else if(userRoomRecord.getUserDirection()==3&&roomRecordInfo.getWestUid()==null){
							roomRecordInfo.setWestUid(userRoomRecord.getUserId());
						}else if(userRoomRecord.getUserDirection()==4&&roomRecordInfo.getNorthUid()==null){
							roomRecordInfo.setNorthUid(userRoomRecord.getUserId());
						} 
					}
				}
				if(roomRecordInfo.getEndTime()!=null){
					roomRecordInfo.setEndTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(roomRecordInfo.getEndTime()));
				}
				roomRecordInfo.setCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(roomRecordInfo.getCreateTime()));
				
				newInfoList.add(roomRecordInfo);
			}
			
		}
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
 		modelAndView.addObject("newInfoList", newInfoList);
		
		modelAndView.setViewName("ajax/roomrecordinfolist");
		
		return modelAndView;
		
	}
}
