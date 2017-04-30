package com.mahjong.server.service;

import java.util.List;

import com.mahjong.server.netty.model.AuthReqModel;
import com.mahjong.server.vo.User;

/**
 * 用户接口
 */
public interface UserService {

	public void save(User user);

	public User login(String username, String password);

	public boolean isUserRegister(String weixinId);

	public List<User> getAllUser();

	public void addUser(AuthReqModel authModel);

	public int getFangKaNum(String weixinId);
}
