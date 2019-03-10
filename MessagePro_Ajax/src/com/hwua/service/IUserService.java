package com.hwua.service;

import java.util.List;

import com.hwua.entity.User;

/**
 * 
 * @author Administrator
 *
 */
public interface IUserService {
   public User login(User user);//登录
   public boolean register(User user);//注册
   public List<User> getAllUsers();
   public User queryUserBySendid(int sendid);
   public boolean queryUserByName(String name);
  
}
