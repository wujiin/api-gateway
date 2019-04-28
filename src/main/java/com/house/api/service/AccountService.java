package com.house.api.service;

import java.util.List;

import com.house.api.feignclient.UserClient;
import com.house.api.model.User;
import com.house.api.utils.BeanHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import static com.house.api.common.RestCode.*;

/**
 * @Description 用户登录，注册，个人信息服务
 * @Author wujin
 **/
@Service
public class AccountService {

    @Value("${domain.name}")
    private String domainName;


    @Autowired
    private FileService fileService;

    @Autowired
    private UserClient userClient;

    /**
     * @Description 查询用户
     **/
    public List<User> getUserByQuery(User query) {
        List<User> users = userClient.getUserList(query).getResult();
        return users;
    }

    /**
     * @Description 添加用户
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
        userClient.addUser(account);
        return true;
    }


    /**
     * @Description 判断用户是否存在
     **/
    public boolean isExist(String email) {
        return getUser(email) != null;
    }

    /**
     * @Description 用户信息查询
     **/
    private User getUser(String email) {
        User queryUser = new User();
        queryUser.setEmail(email);
        List<User> users = getUserByQuery(queryUser);
        return !users.isEmpty() ? users.get(0) : null;
    }

    /**
     * @Description 返回成功与失败
     **/
    public boolean enable(String key) {
        return userClient.enable(key).getCode() == OK.code;
    }

    /**
     * @Description 调用重置通知接口
     **/
    @Async
    public void remember(String email) {
        userClient.resetNotify(email, "http://" + domainName + "/accounts/reset");
    }

    /**
     * @Description 重置密码操作
     **/
    public User reset(String key, String password) {
        return userClient.reset(key, password).getResult();
    }

    /**
     * @Description 获取redis token对应的账户
     **/
    public String getResetEmail(String key) {
        return userClient.getEmail(key).getResult();
    }

    /**
     * @Description 修改密码
     **/
    public User updateUser(User user) {
        //设置更新用户信息时间
        BeanHelper.onUpdate(user);
        return userClient.updateUser(user).getResult();
    }

    /**
     * @Description 登出
     **/
    public void logout(String token) {
        userClient.logout(token);
    }

    /**
     * @Description 校验用户名密码并返回用户对象
     **/
    public User auth(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return null;
        }
        User user = new User();
        user.setEmail(username);
        user.setPasswd(password);
        try {
            user = userClient.authUser(user).getResult();
        } catch (Exception e) {
            return null;
        }
        return user;
    }


}
