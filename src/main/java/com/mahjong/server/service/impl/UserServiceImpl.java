package com.mahjong.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahjong.server.netty.model.AuthReqModel;
import com.mahjong.server.service.UserService;
import com.mahjong.server.vo.User;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserRegister(String weixinId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addUser(AuthReqModel authModel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFangKaNum(String weixinId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
