package com.mahjong.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/frame")
public class FrameController {
	
	
	@RequestMapping(value = "/static")
	@ResponseBody
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		String topage = request.getParameter("topage");
		modelAndView.setViewName(topage);
		return modelAndView;

	}

}
