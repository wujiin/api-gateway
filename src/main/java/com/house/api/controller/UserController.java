package com.house.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.house.api.common.ResultMsg;
import com.house.api.common.UserContext;
import com.house.api.model.Agency;
import com.house.api.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.house.api.service.AccountService;
import com.house.api.service.AgencyService;

/**
 * @Description 用户控制器
 * @Author wujin
 **/
@Controller
public class UserController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AgencyService agencyService;

    /**
     * @Description 提交注册请求
     **/
    @RequestMapping(value = "accounts/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String accountsSubmit(User account, ModelMap modelMap) {
        if (account == null || account.getName() == null) {
            //返回租房机构信息
            modelMap.put("agencyList", agencyService.getAllAgency());
            return "/user/accounts/register";
        }
        ResultMsg retMsg = UserHelper.validate(account);

        if (retMsg.isSuccess()) {
            //判断用户是否存在
            boolean exist = accountService.isExist(account.getEmail());
            if (!exist) {
                accountService.addAccount(account);
                modelMap.put("success_email", account.getEmail());
                return "/user/accounts/registerSubmit";
            } else {
                return "redirect:/accounts/register?" + ResultMsg.errorMsg("邮箱已被占用").asUrlParams();
            }
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("参数错误").asUrlParams();
        }
    }

    /**
     * @Description 注册账户激活
     **/
    @RequestMapping("accounts/verify")
    public String verify(String key) {
        boolean result = accountService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期").asUrlParams();
        }
    }


    /**
     * @Description 登录
     **/
    @RequestMapping(value = "/accounts/signin", method = {RequestMethod.POST, RequestMethod.GET})
    public String loginSubmit(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || password == null) {
            req.setAttribute("target", req.getParameter("target"));
            return "/user/accounts/signin";
        }
        //校验用户名密码
        User user = accountService.auth(username, password);
        if (user == null) {
            return "redirect:/accounts/signin?" + "username=" + username + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        } else {
            UserContext.setUser(user);
            return StringUtils.isNotBlank(req.getParameter("target")) ? "redirect:" + req.getParameter("target") : "redirect:/index";
        }
    }

    /**
     * @Description 登出
     **/
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest req) {
        User user = UserContext.getUser();
        accountService.logout(user.getToken());
        return "redirect:/index";
    }


    /**
     * @Description 忘记密码页面跳转
     **/
    @RequestMapping("accounts/remember")
    public String remember(String username, ModelMap modelMap) {
        if (StringUtils.isBlank(username)) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("邮箱不能为空").asUrlParams();
        }
        accountService.remember(username);
        modelMap.put("email", username);
        return "/user/accounts/remember";
    }

    /**
     * @Description 忘记密码重置密码邮箱链接
     **/
    @RequestMapping("accounts/reset")
    public String reset(String key, ModelMap modelMap) {
        //验证token是否失效（失效时间为30min）
        String email = accountService.getResetEmail(key);
        if (StringUtils.isBlank(email)) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("重置链接已过期");
        }
        modelMap.put("email", email);
        modelMap.put("success_key", key);
        return "/user/accounts/reset";
    }

    /**
     * @Description 重置密码
     **/
    @RequestMapping(value = "accounts/resetSubmit", method = {RequestMethod.POST, RequestMethod.GET})
    public String resetSubmit(HttpServletRequest req, User user) {
        //验证输入
        ResultMsg retMsg = UserHelper.validateResetPassword(user.getKey(), user.getPasswd(), user.getConfirmPasswd());
        if (!retMsg.isSuccess()) {
            String suffix = "";
            if (StringUtils.isNotBlank(user.getKey())) {
                suffix = "email=" + accountService.getResetEmail(user.getKey()) + "&key=" + user.getKey() + "&";
            }
            return "redirect:/accounts/reset?" + suffix + retMsg.asUrlParams();
        }
        //重置密码，重置当前线程中的用户信息
        User updatedUser = accountService.reset(user.getKey(), user.getPasswd());
        UserContext.setUser(updatedUser);
        return "redirect:/index?" + retMsg.asUrlParams();
    }


    /**
     * @Description 个人信息页面跳转
     **/
    @RequestMapping(value = "accounts/profile", method = {RequestMethod.POST, RequestMethod.GET})
    public String profile(ModelMap model) {
        List<Agency> list = agencyService.getAllAgency();
        model.addAttribute("agencyList", list);
        return "/user/accounts/profile";
    }

    /**
     * @Description 修改个人信息
     **/
    @RequestMapping(value = "accounts/profileSubmit", method = {RequestMethod.POST, RequestMethod.GET})
    public String profileSubmit(User updateUser) {
        if (updateUser.getEmail() == null) {
            return "redirect:/accounts/profile?" + ResultMsg.errorMsg("邮箱不能为空").asUrlParams();
        }
        User user = accountService.updateUser(updateUser);
        UserContext.setUser(user);
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }


    /**
     * @Description 修改密码操作
     **/
    @RequestMapping("accounts/changePassword")
    public String changePassword(String email, String password, String newPassword,
                                 String confirmPassword) {
        User user = accountService.auth(email, password);
        if (user == null || !confirmPassword.equals(newPassword)) {
            return "redirct:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        User updateUser = new User();
        updateUser.setPasswd(newPassword);
        updateUser.setEmail(email);
        accountService.updateUser(updateUser);
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }
}
