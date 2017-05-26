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
import com.mahjong.server.service.DBService;
import com.mahjong.server.util.DateUtil;
import com.mahjong.server.util.MD5Util;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
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
		
		if(manageUser != null && MD5Util.checkPassword(passwd, manageUser.getUpasswd())){
			modelAndView.addObject("manageUser", manageUser);
			modelAndView.setViewName("index");
		}else{
			modelAndView.addObject("errorpassw", 1);
			modelAndView.setViewName("login");
		}
		return modelAndView;

	}
	@RequestMapping(value = "/getAdminUser")
	public ModelAndView getAdminUser(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		String searchUname = request.getParameter("searchUname");
		
		List<ManageUser> newmanageUserList = new ArrayList<ManageUser>();
		
		List<ManageUser> manageUserList = dbService.selectAllManageUser();
		if(CollectionUtils.isNotEmpty(manageUserList)){
			
			for(ManageUser manageUser : manageUserList){
				
				String createDate = DateUtil.fromatDateToYYMMDDHHMMSS(manageUser.getCreateTime());
				if((!StringUtils.isEmpty(datemin)&&createDate.compareTo(datemin)<0)){
					continue;
				}
				if((!StringUtils.isEmpty(datemax)&&createDate.compareTo(datemax)>0)){
					continue;
				}
				if((!StringUtils.isEmpty(searchUname)&& !manageUser.getUname().contains(searchUname))){
					continue;
				}
				manageUser.setCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(manageUser.getCreateTime()));
				
				newmanageUserList.add(manageUser);
			}
			
		}
		
		modelAndView.addObject("manageUserList", newmanageUserList);
		
		modelAndView.setViewName("manageuserlist");
		
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
		}
		
		manageUser.setMobile(usertel);
		manageUser.setNickName(nickname);
		manageUser.setState(new Byte((byte) 1));
		manageUser.setUname(username);
		manageUser.setUpasswd(MD5Util.getMD5String(userpasswd));
		
		if(!StringUtils.isEmpty(userLevel)&&NumberUtils.isNumber(userLevel)){
			manageUser.setUserLevel((byte) Integer.parseInt(userLevel));
			manageUser.setCreateTime(new Date());
		}else{
			manageUser.setUserLevel((byte)1);
		}
		
		if(toedit.equals("1")){
			manageUser.setId(Integer.parseInt(userId));
			dbService.updateManageUserByID(manageUser);
		}else{
			dbService.insertManageUser(manageUser);
		}
		
		return " success!";
		
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
	
}
