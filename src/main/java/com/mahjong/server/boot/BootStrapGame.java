package com.mahjong.server.boot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahjong.server.netty.server.WebSocketServer;

@Component
public class BootStrapGame implements InitializingBean, DisposableBean {
	private static final Logger logger = LoggerFactory.getLogger(BootStrapGame.class);
	@Autowired
	private WebSocketServer server;


	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			server.start();
			logger.info("start game..........");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BootStrapGame start game error", e);
		}

	}

	@Override
	public void destroy() throws Exception {
		server.shutdown();
		logger.info("BootStrapGame stop game");

	}

}
