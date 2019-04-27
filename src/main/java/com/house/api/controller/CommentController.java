package com.house.api.controller;

import com.house.api.common.UserContext;
import com.house.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.api.service.CommentService;

/**
 * @Description 用户评论控制器
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * @Description 房产用户评论
     **/
    @RequestMapping(value = "comment/leaveComment")
    public String leaveComment(String content, Long houseId, ModelMap modelMap) {
        User user = UserContext.getUser();
        Long userId = user.getId();
        commentService.addHouseComment(houseId, content, userId);
        return "redirect:/house/detail?id=" + houseId;
    }

    /**
     * @Description 百科用户评论
     **/
    @RequestMapping(value = "comment/leaveBlogComment")
    public String leaveBlogComment(String content, Integer blogId, ModelMap modelMap) {
        User user = UserContext.getUser();
        Long userId = user.getId();
        commentService.addBlogComment(blogId, content, userId);
        return "redirect:/blog/detail?id=" + blogId;
    }
}
