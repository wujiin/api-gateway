package com.house.api.controller;

import java.util.List;

import com.house.api.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.api.service.HouseService;

/**
 * @Description 房产控制器
 * @Author wujin
 **/
@Controller
public class HomepageController {

    @Autowired
    private HouseService houseService;


    /**
     * @Description 房产首页信息初始化
     **/
    @RequestMapping("index")
    public String accountsRegister(ModelMap modelMap) {
        List<House> houses = houseService.getLastest();
        modelMap.put("recomHouses", houses);
        return "/homepage/index";
    }

    @RequestMapping("")
    public String index(ModelMap modelMap) {
        return "redirect:/index";
    }
}
