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

import com.alibaba.fastjson.JSON;
import com.mahjong.server.entity.MessageInfo;
import com.mahjong.server.game.enums.EventEnum;
import com.mahjong.server.netty.model.NoticeRespModel;
import com.mahjong.server.netty.model.ProtocolModel;
import com.mahjong.server.netty.session.ClientSession;
import com.mahjong.server.service.DBService;
import com.mahjong.server.util.DateUtil;

import io.netty.channel.ChannelHandlerContext;

@Controller
@RequestMapping(value = "message")
public class MessageController {
	@Autowired
	private DBService dbService;
	
	@RequestMapping(value = "/messageinfo")
	public ModelAndView getAdminUser(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("messageinfo");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/getMessageInfoList")
	public ModelAndView getMessageInfoList(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String msgPosition = request.getParameter("msgPosition");
		String mesgstate = request.getParameter("mesgstate");
		String datemin = request.getParameter("datemin");
		String datemax = request.getParameter("datemax");
		
		String eachPageCount = request.getParameter("eachPageCount");
		String curPage = request.getParameter("curPage");
		
		List<MessageInfo> newInfoList = new ArrayList<MessageInfo>();
		
		Integer mesgstatenum = null;
		Integer msgPositionnum = null;
		
		if(StringUtils.isEmpty(mesgstate)){
			mesgstate = null;
		}else{
			mesgstatenum = Integer.parseInt(mesgstate);
		}
		
		if(StringUtils.isEmpty(msgPosition)){
			msgPositionnum = null;
		}else{
			msgPositionnum = Integer.parseInt(msgPosition);
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
		int totalcount = dbService.selectMessageInfoCount(msgPositionnum, mesgstatenum, datemin, datemax);
		
		List<MessageInfo> messageInfoList = dbService.selectMessageInfoLimit(msgPositionnum, mesgstatenum, datemin, datemax ,curP*eachCount,eachCount);
		if(CollectionUtils.isNotEmpty(messageInfoList)){
			
			for(MessageInfo messageInfo : messageInfoList){
				
				messageInfo.setCreateTimeStr(DateUtil.fromatDateToYYMMDDHHMMSS(messageInfo.getCreateTime()));
				
				newInfoList.add(messageInfo);
			}
			
		}
		
		modelAndView.addObject("currentPage", curPage);
		modelAndView.addObject("pageCount", totalcount%eachCount==0? (totalcount/eachCount):(totalcount/eachCount+1));
 		modelAndView.addObject("newInfoList", newInfoList);
		
		modelAndView.setViewName("ajax/messageinfolist");
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/changeMessageState")
	@ResponseBody
	public Object changeMessageState(HttpServletRequest request, HttpServletResponse response) {
		
		String checkedItemIds = request.getParameter("checkedItemIds");
		String mesgstate = request.getParameter("tostate");
		
		Byte mesgstatenum = null;
		
		if(StringUtils.isEmpty(mesgstate)){
			return null;
		}else{
			mesgstatenum = Byte.parseByte(mesgstate);
		}
		
		
		String[] itemids = null;
		
		if(!StringUtils.isEmpty(checkedItemIds)){
			itemids = checkedItemIds.split(",");
		}else{
			return null;
		}
		
		if(itemids!=null){
			for(String uid : itemids){
				if(!StringUtils.isEmpty(uid)&&NumberUtils.isNumber(uid)){
					
					MessageInfo messg = new MessageInfo();
					messg.setId(Integer.parseInt(uid));
					messg.setState(mesgstatenum);
					
					dbService.updateMessageInfoById(messg);
					MessageInfo messageInfo = dbService.selectMessageInfoById(Integer.parseInt(uid));
					
					
					ProtocolModel protocolModel = new ProtocolModel();
					
					NoticeRespModel noticeRespModel = new NoticeRespModel();
					noticeRespModel.setMesID(Integer.parseInt(uid));
					noticeRespModel.setIntervalTime(messageInfo.getIntervalTime());
					noticeRespModel.setMesPosition(messageInfo.getMesPosition());
					noticeRespModel.setMessageContent(messageInfo.getMessageContent());
					noticeRespModel.setMesTitle(messageInfo.getMesTitle());
					noticeRespModel.setMesType(messageInfo.getMesType());
					noticeRespModel.setMesState(messageInfo.getState().intValue());
					
					Map<String, ChannelHandlerContext> sessionMap = ClientSession.sessionMap;
					for(ChannelHandlerContext ctx : sessionMap.values()){
						
						protocolModel.setCommandId(EventEnum.NOTICE_CHANGE_RESP.getValue());
						protocolModel.setBody(JSON.toJSONString(noticeRespModel));
						
						ctx.writeAndFlush(protocolModel);
						
					}
					
					
				}
			}
		}
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("info", "success");
		return returnMap;
		
	}
	

	
	@RequestMapping(value = "/addMessageInfo")
	@ResponseBody
	public Object addMessageInfo(HttpServletRequest request, HttpServletResponse response) {
		
		String messageType = request.getParameter("messageType");
		String messagePosition = request.getParameter("messagePosition");
		String messageTitle = request.getParameter("messageTitle");
		String messageContent = request.getParameter("messageContent");
		String messageinterval = request.getParameter("messageinterval");
		
		MessageInfo messg = new MessageInfo();
		
		Byte messageTypeB = Byte.parseByte(messageType);
		messg.setMesType(messageTypeB);
		
		if(messageTypeB.intValue()==2){
			messg.setMesPosition(Byte.parseByte(messagePosition));
		}
		
		messg.setMesTitle(messageTitle);
		messg.setMessageContent(messageContent);
		messg.setState((byte) 0);
		messg.setIntervalTime(Integer.parseInt(messageinterval));
		messg.setCreateTime(new Date());
		
		Integer msgID = dbService.inserMessageInfo(messg);
		
		ProtocolModel protocolModel = new ProtocolModel();
		
		NoticeRespModel noticeRespModel = new NoticeRespModel();
		noticeRespModel.setMesID(msgID);
		noticeRespModel.setIntervalTime(messg.getIntervalTime());
		noticeRespModel.setMesPosition(messg.getMesPosition());
		noticeRespModel.setMessageContent(messageContent);
		noticeRespModel.setMesTitle(messg.getMesTitle());
		noticeRespModel.setMesType(messg.getMesType());
		noticeRespModel.setMesState(0);
		
		Map<String, ChannelHandlerContext> sessionMap = ClientSession.sessionMap;
		for(ChannelHandlerContext ctx : sessionMap.values()){
			
			protocolModel.setCommandId(EventEnum.NOTICE_RESP.getValue());
			protocolModel.setBody(JSON.toJSONString(noticeRespModel));
			
			ctx.writeAndFlush(protocolModel);
			
		}
		
		return "success";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
