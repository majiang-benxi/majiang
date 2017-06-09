package com.mahjong.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mahjong.server.entity.ManageUser;
import com.mahjong.server.entity.RoomCartChange;
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.entity.UserRoomRecord;
import com.mahjong.server.service.DBService;
import com.mahjong.server.util.DateUtil;
import com.mahjong.server.util.ManageUserSessionUtil;
import com.mahjong.server.vo.UserRecordScoreVO;

@Controller
@RequestMapping(value = "/frontuser")
public class FrontUserController {
	
	@Autowired
	private DBService dbService;
	
	@RequestMapping(value = "/getUserInfo")
	public ModelAndView getAdminUser(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("userInfo");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/getUserInfoList")
	public ModelAndView getAdminUserList(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String uid = request.getParameter("uid");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		String searchUname = request.getParameter("searchUname");
		
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		List<UserInfo> newuserInfoList = new ArrayList<UserInfo>();
		
		if(StringUtils.isEmpty(uid)){
			uid = null;
		}
		
		if(StringUtils.isEmpty(datemin)){
			datemin = null;
		}
		if(StringUtils.isEmpty(datemax)){
			datemax = null;
		}
		if(StringUtils.isEmpty(searchUname)){
			searchUname = null;
		}
		
		
		if(StringUtils.isEmpty(eachPageCount)){
			eachPageCount = "10";
		}
		if(StringUtils.isEmpty(curPage)){
			curPage = "1";
		}
		
		Integer curP = Integer.parseInt(curPage)-1;
		Integer eachCount = Integer.parseInt(eachPageCount);
		
		int totalcount = dbService.selectAllUserCount(uid, datemin, datemax, searchUname);
		
		List<UserInfo> userInfoList = dbService.selectAllUserLimit(uid,datemin,datemax,searchUname,curP*eachCount,eachCount);
		if(CollectionUtils.isNotEmpty(userInfoList)){
			
			for(UserInfo userInfo : userInfoList){
				
				userInfo.setCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getCreateTime()));
				userInfo.setLastLoginTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getLastLoginTime()));
				
				newuserInfoList.add(userInfo);
			}
			
		}
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
 		modelAndView.addObject("newuserInfoList", newuserInfoList);
		
		modelAndView.setViewName("ajax/userinfolist");
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/editUserInfo")
	@ResponseBody
	public Object editUserInfo(HttpServletRequest request, HttpServletResponse response) {
		
		String checkedUserIds = request.getParameter("checkedUserIds");
		String tostate = request.getParameter("tostate");
		String roomcartEditNum = request.getParameter("roomcartEditNum");
		
		
		String[] userIds = null;
		
		if(!StringUtils.isEmpty(checkedUserIds)){
			userIds = checkedUserIds.split(",");
		}else{
			return "error";
		}
		

		String value = ManageUserSessionUtil.getCookieValue(request.getCookies(), "manageuid");
		ManageUser opemanageUser = ManageUserSessionUtil.getManegeSession(value);
		
		ManageUser updatemanageUser = new ManageUser();
		updatemanageUser.setId(opemanageUser.getId());
		
		if(userIds!=null){
			for(String uid : userIds){
				if(!StringUtils.isEmpty(uid)&&NumberUtils.isNumber(uid)){
					
					UserInfo dbuserInfo = dbService.selectUserInfoByID(Integer.parseInt(uid));
					
					UserInfo userInfo = new UserInfo();
					userInfo.setId(Integer.parseInt(uid));
					

					if(!StringUtils.isEmpty(tostate)){
						userInfo.setState((byte) Integer.parseInt(tostate));
					}
					
					if(!StringUtils.isEmpty(roomcartEditNum)){
						
						
						Integer roomCardNumChanged = Integer.parseInt(roomcartEditNum)-dbuserInfo.getRoomCartNum(); 
						
						if(opemanageUser.getUserLevel()==1){
							if((roomCardNumChanged)<=0 || (opemanageUser.getCardHold()-roomCardNumChanged)<0){
								return "error";
							}else{
								updatemanageUser.setCardHold(opemanageUser.getCardHold()-roomCardNumChanged);
								
								dbService.updateManageUserByID(updatemanageUser);
								
								RoomCartChange roomCartChange = new RoomCartChange();
								roomCartChange.setChangeNum(-Integer.parseInt(roomcartEditNum));
								roomCartChange.setChangeTime(new Date());
								roomCartChange.setChangecause("给用户充值");
								roomCartChange.setManageName(opemanageUser.getNickName());
								roomCartChange.setManageUserId(opemanageUser.getId());
								
								roomCartChange.setUserId(userInfo.getId());
								roomCartChange.setUserName(userInfo.getNickName());
								
								dbService.insertRoomCartChange(roomCartChange);
								
							}
							
						}
						
						userInfo.setRoomCartNum(Integer.parseInt(roomcartEditNum));
					}
					
					dbService.updateUserInfoById(userInfo);
					
					RoomCartChange roomCartChange = new RoomCartChange();
					roomCartChange.setChangeNum(Integer.parseInt(roomcartEditNum));
					roomCartChange.setChangeTime(new Date());
					roomCartChange.setChangecause("充值");
					roomCartChange.setManageName(opemanageUser.getNickName());
					roomCartChange.setManageUserId(opemanageUser.getId());
					
					roomCartChange.setUserId(userInfo.getId());
					roomCartChange.setUserName(userInfo.getNickName());
					
					dbService.insertRoomCartChange(roomCartChange);
					
				}
			}
		}
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("info", "success");
		return returnMap;
		
	}
	
	
	
	
	@RequestMapping(value = "/getRecordInfo")
	public ModelAndView recordInfo(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("recordInfo");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/getUserPlayRecordInfoList")
	public ModelAndView getUserPlayRecordInfoList(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String uid = request.getParameter("uid");
		String roomNum = request.getParameter("roomnum");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		List<UserRoomRecord> newuserInfoList = new ArrayList<UserRoomRecord>();
		
		if(StringUtils.isEmpty(uid)){
			uid = null;
		}
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
		
		int totalcount = dbService.getUserPlayRecordInfoCount(uid, roomNum, datemin, datemax);
		
		List<UserRoomRecord> userInfoList = dbService.getUserPlayRecordInfoLimit(uid,roomNum,datemin,datemax,curP*eachCount,eachCount);
		if(CollectionUtils.isNotEmpty(userInfoList)){
			
			for(UserRoomRecord userInfo : userInfoList){
				
				userInfo.setOperateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getOperateTime()));
				
				newuserInfoList.add(userInfo);
			}
			
		}
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
 		modelAndView.addObject("newInfoList", newuserInfoList);
		
		modelAndView.setViewName("ajax/recordInfolist");
		
		return modelAndView;
		
	}
	
	
	
	
	@RequestMapping(value = "/getUserScoreInfo")
	public ModelAndView getUserScoreInfo(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("userscoreinfo");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/getUserscoreinfoInfoList")
	public ModelAndView getUserscoreinfoInfoList(HttpServletRequest request, HttpServletResponse response) {
		
	ModelAndView modelAndView = new ModelAndView();
		
		String uid = request.getParameter("uid");
		String roomNum = request.getParameter("roomnum");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		if(StringUtils.isEmpty(uid)){
			uid = null;
		}
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
		
		int totalcount = dbService.getUserScoreInfoInfoCount(uid, roomNum, datemin, datemax);
		
		List<UserRecordScoreVO> userInfoList = dbService.getUserScoreInfoInfoListLimit(uid,roomNum,datemin,datemax,curP*eachCount,eachCount);
		
		if(CollectionUtils.isNotEmpty(userInfoList)){
			
			for(UserRecordScoreVO userInfo : userInfoList){
				userInfo.setRoomCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(userInfo.getRoomCreateTime()));
			}
			
		}
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
 		modelAndView.addObject("newInfoList", userInfoList);
		
		modelAndView.setViewName("ajax/scoreInfolist");
		
		return modelAndView;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
