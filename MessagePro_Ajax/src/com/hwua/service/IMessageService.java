package com.hwua.service;

import java.util.List;

import com.hwua.entity.Message;

public interface IMessageService {
    /**
     * 获取登录用户收到的所有短消息
     * 
     * @param loginid
     * @return
     */
    public List<Message> queryMessageByLoginUser(int loginid,int start,int pageSize);
    
    public Message queryMessageById(String id);
    
    public int sendMessage(Message msg);
    
    public int deleteMsgById(int id);
    
    public Long queryMsgCount(int loginid);
}
