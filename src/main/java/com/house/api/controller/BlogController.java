package com.house.api.controller;

import java.util.List;

import com.house.api.common.PageParams;
import com.house.api.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.house.api.common.CommonConstants;
import com.house.api.common.PageData;
import com.house.api.model.Blog;
import com.house.api.model.Comment;
import com.house.api.service.CommentService;
import com.house.api.service.HouseService;

/**
 * @Description 百科控制器
 * @Author wujin
 **/
@Controller
public class BlogController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HouseService houseService;

    /**
     *@Description 百科列表页面初始化
     **/
    @RequestMapping(value = "blog/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(Integer pageSize, Integer pageNum, Blog query, ModelMap modelMap) {
        PageData<Blog> ps = commentService.queryBlogs(query, PageParams.build(pageSize, pageNum));
        List<House> houses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", houses);
        modelMap.put("ps", ps);
        return "/blog/listing";
    }

    /**
     *@Description 百科详细信息
     **/
    @RequestMapping(value = "blog/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public String blogDetail(int id, ModelMap modelMap) {
        Blog blog = commentService.queryOneBlog(id);
        List<Comment> comments = commentService.getBlogComments(id);
        List<House> houses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", houses);
        modelMap.put("blog", blog);
        modelMap.put("commentList", comments);
        return "/blog/detail";
    }
}
