package com.house.api.service;


import java.util.List;

import com.house.api.feignclient.UserClient;
import com.house.api.model.Agency;
import com.house.api.model.ListResponse;
import com.house.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.api.common.PageData;
import com.house.api.common.PageParams;

@Service
public class AgencyService {

    @Autowired
    private UserClient userClient;

    /**
     * @Description 获取所有租房机构信息
     **/
    public List<Agency> getAllAgency() {
        return userClient.agencyList().getResult();
    }

    /**
     * @Description 查询机构信息
     **/
    public Agency getAgency(Integer id) {
        return userClient.getAgencyById(id).getResult();
    }

    /**
     * @Description 添加机构
     **/
    public void add(Agency agency) {
        userClient.addAgency(agency);
    }

    /**
     * @Description 获取经纪人列表
     **/
    public PageData<User> getAllAgent(PageParams pageParams) {
        ListResponse<User> result = userClient.getAgentList(pageParams.getLimit(), pageParams.getOffset()).getResult();
        Long count = result.getCount();
        return PageData.buildPage(result.getList(), count, pageParams.getPageSize(), pageParams.getPageNum());
    }

    /**
     * @Description 获取房产经纪人信息
     **/
    public User getAgentDetail(Long id) {
        return userClient.getAgentById(id).getResult();
    }
}
