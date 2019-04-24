package com.house.api.service;

import java.util.List;

import com.house.api.dao.UserDao;
import com.house.api.model.User;
import com.house.api.utils.BeanHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

/**
 *@Description 用户登录，注册，个人信息服务
 *@Author wujin
 **/
@Service
public class AccountService {

    @Value("${domain.name}")
    private String domainName;


    @Autowired
    private FileService fileService;


    @Autowired
    private UserDao userDao;


    public User getUserById(Long id) {
        User queryUser = new User();
        queryUser.setId(id);
        List<User> users = getUserByQuery(queryUser);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    public List<User> getUserByQuery(User query) {
        List<User> users = userDao.getUserList(query);
        return users;
    }

    /**
     *@Description 添加用户
     **/
    public boolean addAccount(User account) {
        //头像上传
        if (account.getAvatarFile() != null) {
            List<String> imags = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
            account.setAvatar(imags.get(0));
        }
        account.setEnableUrl("http://" + domainName + "/accounts/verify");
        BeanHelper.setDefaultProp(account, User.class);
        //添加用户信息
        userDao.addUser(account);
        return true;
    }


    /**
     * @Description 判断用户是否存在
     **/
    public boolean isExist(String email) {
        return getUser(email) != null;
    }

    private User getUser(String email) {
        User queryUser = new User();
        queryUser.setEmail(email);
        List<User> users = getUserByQuery(queryUser);
        return !users.isEmpty() ? users.get(0) : null;
    }

    /**
     * @param key
     * @return 返回成功与失败
     */
    public boolean enable(String key) {
        userDao.enable(key);
        return true;
    }


    /**
     * 调用重置通知接口
     *
     * @param email
     */
    @Async
    public void remember(String email) {
        userDao.resetNotify(email, "http://" + domainName + "/accounts/reset");
    }

    /**
     * 重置密码操作
     *
     * @param email
     * @param key
     */
    public User reset(String key, String password) {
        return userDao.reset(key, password);
    }


    public String getResetEmail(String key) {
        String email = userDao.getEmail(key);
        return email;
    }


    public User updateUser(User user) {
        BeanHelper.onUpdate(user);
        return userDao.updateUser(user);
    }

    public void logout(String token) {
        userDao.logout(token);
    }

    /**
     * 校验用户名密码并返回用户对象
     *
     * @param username
     * @param password
     * @return
     */
    public User auth(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return null;
        }
        User user = new User();
        user.setEmail(username);
        user.setPasswd(password);
        try {
            user = userDao.authUser(user);
        } catch (Exception e) {
            return null;
        }
        return user;
    }


}
