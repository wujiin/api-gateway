package com.house.api.feignclient;

import com.house.api.common.RestResponse;
import com.house.api.model.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 房产服务
 * @Author wujin
 **/
@FeignClient(name = "house")
public interface HouseClient {

    @RequestMapping(value = "/house/lastest", method = RequestMethod.GET)
    RestResponse<List<House>> getLastest();

    @RequestMapping(value = "/house/list", method = RequestMethod.GET)
    RestResponse<ListResponse<House>> getHouseList(@RequestBody HouseQueryReq req);

    @RequestMapping(value = "/house/lastest", method = RequestMethod.GET)
    RestResponse<List<House>> getHotHouseList(@RequestParam("size") Integer size);

    @RequestMapping(value = "/house/detail", method = RequestMethod.GET)
    RestResponse<House> getOneHouse(@RequestParam("id") Long id);

    @RequestMapping(value = "/house/addUserMsg", method = RequestMethod.GET)
    RestResponse<User> addUserMsg(@RequestBody UserMsg userMsg);

    @RequestMapping(value = "/house/rating", method = RequestMethod.GET)
    RestResponse<User> rating(@RequestParam("id") Long id, @RequestParam("rating") Double rating);

    @RequestMapping(value = "/house/allCitys", method = RequestMethod.GET)
    RestResponse<List<City>> getAllCitys();

    @RequestMapping(value = "/house/allCommunitys", method = RequestMethod.GET)
    RestResponse<List<Community>> getAllCommunitys();

    @RequestMapping(value = "/house/add", method = RequestMethod.GET)
    RestResponse addHouse(@RequestBody House house);

    @RequestMapping(value = "/house/bind", method = RequestMethod.GET)
    RestResponse bindOrInBind(@RequestBody HouseUserReq req);
}
