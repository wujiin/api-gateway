package com.house.api.feignclient;

import com.house.api.common.RestResponse;
import com.house.api.model.Agency;
import com.house.api.model.ListResponse;
import com.house.api.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 用户服务
 * @Author wujin
 **/
@FeignClient(name = "user")
public interface UserClient {

    @RequestMapping(value = "/user/getList", method = RequestMethod.GET)
    RestResponse<List<User>> getUserList(@RequestBody User query);

    @RequestMapping(value = "/user/enable", method = RequestMethod.GET)
    RestResponse<Object> enable(@RequestParam("key") String key);

    @RequestMapping(value = "/user/auth", method = RequestMethod.POST)
    RestResponse<User> authUser(@RequestBody User user);

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    RestResponse<User> logout(@RequestParam("token") String token);

    @RequestMapping(value = "/user/resetNotify", method = RequestMethod.GET)
    RestResponse<User> resetNotify(@RequestParam("email") String email, @RequestParam("url") String url);

    @RequestMapping(value = "/user/getKeyEmail", method = RequestMethod.GET)
    RestResponse<String> getEmail(@RequestParam("key") String key);

    @RequestMapping(value = "/user/reset", method = RequestMethod.GET)
    RestResponse<User> reset(@RequestParam("key") String key, @RequestParam("password") String password);

    @RequestMapping(value = "/user/get", method = RequestMethod.GET)
    RestResponse<User> getUserByToken(@RequestParam("token") String token);

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    RestResponse<User> addUser(User user);

    @RequestMapping(value = "/user/update", method = RequestMethod.GET)
    RestResponse<User> updateUser(@RequestBody User user);

    @RequestMapping(value = "/agency/list", method = RequestMethod.GET)
    RestResponse<List<Agency>> agencyList();

    @RequestMapping(value = "/agency/agentDetail", method = RequestMethod.GET)
    RestResponse<User> getAgentById(@RequestParam("id") Long id);

    @RequestMapping(value = "/agency/agentList", method = RequestMethod.GET)
    RestResponse<ListResponse<User>> getAgentList(@RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset);

    @RequestMapping(value = "/agency/agencyDetail", method = RequestMethod.GET)
    RestResponse<Agency> getAgencyById(@RequestParam("id") Integer id);

    @RequestMapping(value = "/agency/add", method = RequestMethod.GET)
    RestResponse addAgency(@RequestBody Agency agency);

}
