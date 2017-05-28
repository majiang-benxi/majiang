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
import com.mahjong.server.entity.UserInfo;
import com.mahjong.server.service.DBService;
import com.mahjong.server.util.DateUtil;
import com.mahjong.server.util.MD5Util;

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
		}
		
		if(userIds!=null){
			for(String uid : userIds){
				if(!StringUtils.isEmpty(uid)&&NumberUtils.isNumber(uid)){
					
					UserInfo userInfo = new UserInfo();
					userInfo.setId(Integer.parseInt(uid));
					

					if(!StringUtils.isEmpty(tostate)){
						userInfo.setState((byte) Integer.parseInt(tostate));
					}
					
					if(!StringUtils.isEmpty(roomcartEditNum)){
						userInfo.setRoomCartNum(Integer.parseInt(roomcartEditNum));
					}
					
					
					dbService.updateUserInfoById(userInfo);
				}
			}
		}
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("info", "success");
		return returnMap;
		
	}
	
	
	
}
