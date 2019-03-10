package com.hwua.dao;
//这是别人提交的代码
import java.sql.SQLException;
import java.util.List;

import com.hwua.entity.User;

public interface IUserDao {
    public User query(User user) throws SQLException;//查询用户
    public int save(User user) throws SQLException;//鎻掑叆鐢ㄦ埛
    public List<User> query() throws SQLException;//鏌ヨ鎵�鏈夌殑鐢ㄦ埛淇℃伅
    public User query(int sendid) throws SQLException;
    public User query(String name) throws SQLException;
  
}
