package com.house.api.controller;

import java.util.List;

import com.house.api.common.CommonConstants;
import com.house.api.common.PageParams;
import com.house.api.common.ResultMsg;
import com.house.api.common.UserContext;
import com.house.api.feignclient.HouseClient;
import com.house.api.model.Comment;
import com.house.api.model.House;
import com.house.api.model.User;
import com.house.api.model.UserMsg;
import com.house.api.service.AgencyService;
import com.house.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.house.api.common.PageData;
import com.house.api.service.HouseService;

/**
 * @Description 房产信息控制器
 * @Author wujin
 **/
@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private HouseClient houseClient;

    /**
     * @Description 房产信息列表
     **/
    @RequestMapping(value = "house/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
        //获取房产列表
        PageData<House> ps = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        //获取热门房产列表
        List<House> rcHouses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", rcHouses);
        modelMap.put("vo", query);
        modelMap.put("ps", ps);
        return "/house/listing";
    }

    /**
     * @Description 房产的详细信息
     **/
    @RequestMapping(value = "house/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public String houseDetail(long id, ModelMap modelMap) {
        //房产信息
        House house = houseService.queryOneHouse(id);
        //评论信息
        List<Comment> comments = commentService.getHouseComments(id);
        //热门房产信息
        List<House> rcHouses = houseService.getHotHouse(CommonConstants.RECOM_SIZE);
        if (house.getUserId() != null) {
            if (!Objects.equal(0L, house.getUserId())) {
                //获取当前房产的经纪人信息
                modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
            }
        }
        modelMap.put("house", house);
        modelMap.put("recomHouses", rcHouses);
        modelMap.put("commentList", comments);
        return "/house/detail";
    }

    /**
     * @Description 房产信息评论
     **/
    @RequestMapping(value = "house/leaveMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public String houseMsg(UserMsg userMsg) {
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId() + "&" + ResultMsg.successMsg("留言成功").asUrlParams();
    }

    /**
     * @Description 房产信息评价
     **/
    @ResponseBody
    @RequestMapping(value = "house/rating", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultMsg houseRate(Double rating, Long id) {
        houseService.updateRating(id, rating);
        return ResultMsg.success();
    }

    /**
     * @Description 房产信息添加页面跳转，初始化添加信息
     **/
    @RequestMapping(value = "house/toAdd", method = {RequestMethod.POST, RequestMethod.GET})
    public String toAdd(ModelMap modelMap) {
        //获取城市名称列表
        modelMap.put("citys", houseService.getAllCitys());
        //获取小区名称列表
        modelMap.put("communitys", houseService.getAllCommunitys());
        return "/house/add";
    }

    /**
     * @Description 房产信息添加
     **/
    @RequestMapping(value = "house/add", method = {RequestMethod.POST, RequestMethod.GET})
    public String doAdd(House house) {
        User user = UserContext.getUser();
        houseService.addHouse(house, user);
        return "redirect:/house/ownlist?" + ResultMsg.successMsg("添加成功").asUrlParams();
    }

    /**
     * @Description 个人收藏房产列表
     **/
    @RequestMapping(value = "house/ownlist", method = {RequestMethod.POST, RequestMethod.GET})
    public String ownlist(House house, PageParams pageParams, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setUserId(user.getId());
        house.setBookmarked(false);
        modelMap.put("ps", houseService.queryHouse(house, pageParams));
        modelMap.put("pageType", "own");
        return "/house/ownlist";
    }

    /**
     *@Description 删除个人房产信息
     **/
    @RequestMapping(value = "house/del", method = {RequestMethod.POST, RequestMethod.GET})
    public String delsale(Long id, String pageType) {
        User user = UserContext.getUser();
        houseService.unbindUser2House(id, user.getId(), pageType.equals("own") ? false : true);
        return "redirect:/house/ownlist";
    }

    /**
     *@Description 个人房产收藏列表页
     **/
    @RequestMapping(value = "house/bookmarked", method = {RequestMethod.POST, RequestMethod.GET})
    public String bookmarked(House house, PageParams pageParams, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setBookmarked(true);
        house.setUserId(user.getId());
        modelMap.put("ps", houseService.queryHouse(house, pageParams));
        modelMap.put("pageType", "book");
        return "/house/ownlist";
    }

    /**
     *@Description 房产收藏
     **/
    @RequestMapping(value = "house/bookmark", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultMsg bookmark(Long id) {
        User user = UserContext.getUser();
        houseService.bindUser2House(id, user.getId(), true);
        return ResultMsg.success();
    }

    /**
     *@Description 取消房产收藏
     **/
    @RequestMapping(value = "house/unbookmark", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultMsg unbookmark(Long id) {
        User user = UserContext.getUser();
        houseService.unbindUser2House(id, user.getId(), true);
        return ResultMsg.success();
    }
}
