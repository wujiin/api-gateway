package com.house.api.common;

import com.house.api.model.User;

/**
 *@Description 获取当前登录用户  线程安全
 *@Author wujin
 **/
public class UserContext {
  //创建线程副本,防止多人登录修改用户登录信息
  private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();
  
  public static void setUser(User user){
    USER_HOLDER.set(user);
  }
  
  public static void remove() {
    USER_HOLDER.remove();
  }
  
  public static User getUser() {
    return USER_HOLDER.get();
  }

}
