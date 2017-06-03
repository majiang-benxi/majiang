package com.mahjong.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import com.mahjong.server.service.DBService;
import com.mahjong.server.util.DateUtil;
import com.mahjong.server.util.MD5Util;
import com.mahjong.server.util.ManageUserSessionUtil;

@Controller
@RequestMapping(value = "/user")
public class ManageUserController {
	
	@Autowired
	private DBService dbService;
	
	
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/backUserLogin")
	public ModelAndView backUserLogin(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String uname = request.getParameter("uname");
		String passwd = request.getParameter("passwd");
		
		ManageUser manageUser = dbService.selectManageUserByUname(uname);
		
		if(manageUser != null &&manageUser.getState()!=0 && MD5Util.checkPassword(passwd, manageUser.getUpasswd())){
			modelAndView.addObject("manageUser", manageUser);
			modelAndView.setViewName("index");
			Cookie uidCookie = new Cookie("manageuid",ManageUserSessionUtil.createManegeSession(manageUser));
			uidCookie.setMaxAge(3600);
			uidCookie.setPath("/");
			response.addCookie(uidCookie);
			
		}else{
			modelAndView.addObject("errorpassw", 1);
			modelAndView.setViewName("login");
		}
		return modelAndView;

	}
	
	
	@RequestMapping(value = "/getAdminUser")
	public ModelAndView getAdminUser(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("manageuser");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/getAdminUserList")
	public ModelAndView getAdminUserList(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String searchUname = request.getParameter("searchUname");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		List<ManageUser> newmanageUserList = new ArrayList<ManageUser>();
		
		
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
		
		int totalcount = dbService.selectAllManageUserCount(datemin, datemax, searchUname);
		
		List<ManageUser> manageUserList = dbService.selectAllManageUserLimit(datemin,datemax,searchUname,curP*eachCount,eachCount);
		if(CollectionUtils.isNotEmpty(manageUserList)){
			
			for(ManageUser manageUser : manageUserList){
				
				manageUser.setCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(manageUser.getCreateTime()));
				
				newmanageUserList.add(manageUser);
			}
			
		}
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
 		modelAndView.addObject("manageUserList", newmanageUserList);
		
		modelAndView.setViewName("ajax/muserlist");
		
		return modelAndView;
		
	}
	
	
	
	
	
	@RequestMapping(value = "/addAdminUser")
	@ResponseBody
	public <K> Object addAdminUser(HttpServletRequest request, HttpServletResponse response) {
		
		String username = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String userpasswd = request.getParameter("userpasswd");
		String usertel = request.getParameter("usertel");
		String usercardnum = request.getParameter("usercardnum");
		String userLevel = request.getParameter("adminrole");
		
		String toedit = request.getParameter("toedit");
		String userId = request.getParameter("userId");
		
		
		ManageUser manageUser = new ManageUser();
		
		if(!StringUtils.isEmpty(usercardnum)&&NumberUtils.isNumber(usercardnum)){
			manageUser.setCardHold(Integer.parseInt(usercardnum));
		}else{
			manageUser.setCardHold(0);
		}
		
		manageUser.setMobile(usertel);
		manageUser.setNickName(nickname);
		manageUser.setState(new Byte((byte) 1));
		manageUser.setUname(username);
		manageUser.setUpasswd(MD5Util.getMD5String(userpasswd));
		
		if(!StringUtils.isEmpty(userLevel)&&NumberUtils.isNumber(userLevel)){
			manageUser.setUserLevel((byte) Integer.parseInt(userLevel));
		}else{
			manageUser.setUserLevel((byte)1);
		}
		manageUser.setCreateTime(new Date());
		if(toedit.equals("1")){
			manageUser.setId(Integer.parseInt(userId));
			dbService.updateManageUserByID(manageUser);
		}else{
			dbService.insertManageUser(manageUser);
		}
		
		return "success!";
		
	}
	
	@RequestMapping(value = "/updateAdminUserSate")
	@ResponseBody
	public Object deleteAdminUser(HttpServletRequest request, HttpServletResponse response) {
		
		String checkedUserIds = request.getParameter("checkedUserIds");
		String tostate = request.getParameter("tostate");
		
		if(StringUtils.isEmpty(tostate)){
			tostate = "1";
		}
		
		String[] userIds = null;
		
		if(!StringUtils.isEmpty(checkedUserIds)){
			userIds = checkedUserIds.split(",");
		}
		
		if(userIds!=null){
			for(String uid : userIds){
				if(!StringUtils.isEmpty(uid)&&NumberUtils.isNumber(uid)){
					dbService.updateAdminUserSate(Integer.parseInt(uid),Integer.parseInt(tostate));
				}
			}
		}
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("info", "success");
		return returnMap;
		
	}
	
	@RequestMapping(value = "/toEditAdminUser")
	public Object toEditAdminUser(HttpServletRequest request, HttpServletResponse response) {
		
		String uid = request.getParameter("uid");
		
		ManageUser manageUser = dbService.selectManageUserByID(Integer.parseInt(uid));
		manageUser.setCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(manageUser.getCreateTime()));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("manageUser", manageUser);
		modelAndView.setViewName("adminadd");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/updateAdminUserRoomnNum")
	@ResponseBody
	public Object updateAdminUserRoomnNum(HttpServletRequest request, HttpServletResponse response) {
		
		String uid = request.getParameter("usid");
		String roomcartEditNum = request.getParameter("roomcartEditNum");
		ManageUser muser = dbService.selectManageUserByID(Integer.parseInt(uid));
		if(muser.getUserLevel()==2){
			return null;
		}
		
		ManageUser manageUser = new ManageUser();
		manageUser.setId(Integer.parseInt(uid));
		manageUser.setCardHold(Integer.parseInt(roomcartEditNum));
		
		dbService.updateManageUserByID(manageUser);
		
		Integer changeNum = (Integer.parseInt(roomcartEditNum) - (muser.getCardHold()==null?0:muser.getCardHold()));
		
		String value = ManageUserSessionUtil.getCookieValue(request.getCookies(), "manageuid");
		ManageUser opemanageUser = ManageUserSessionUtil.getManegeSession(value);
		
		
		RoomCartChange roomCartChange = new RoomCartChange();
		roomCartChange.setChangeNum(changeNum);
		roomCartChange.setChangeTime(new Date());
		roomCartChange.setChangecause("管理员充值");
		if(opemanageUser!=null){
			roomCartChange.setManageName(opemanageUser.getNickName());
			roomCartChange.setManageUserId(opemanageUser.getId());
		}else{
			roomCartChange.setManageName("someroot");
			roomCartChange.setManageUserId(0);
		}
		
		roomCartChange.setUserId(Integer.parseInt(uid));
		roomCartChange.setUserName(muser.getNickName());
		
		dbService.insertRoomCartChange(roomCartChange);
		
		return "success";
		
	}
	
}
