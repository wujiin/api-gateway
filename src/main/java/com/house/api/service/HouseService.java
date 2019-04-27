package com.house.api.service;

import java.util.List;

import com.house.api.common.HouseUserType;
import com.house.api.common.PageParams;
import com.house.api.feignclient.HouseClient;
import com.house.api.model.*;
import com.house.api.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.house.api.common.PageData;


@Service
public class HouseService {

    @Autowired
    private FileService fileService;

    @Autowired
    private HouseClient houseClient;

    /**
     * @Description 更新用户评价
     **/
    public void updateRating(Long id, Double rating) {
        houseClient.rating(id, rating);
    }

    /**
     * @Description 房产信息添加
     **/
    public void addHouse(House house, User user) {
        //判断是否添加房产图片
        if (house.getHouseFiles() != null && !house.getHouseFiles().isEmpty()) {
            List<MultipartFile> files = house.getHouseFiles();
            //逗号分隔路径
            String imags = Joiner.on(",").join(fileService.getImgPaths(files));
            house.setImages(imags);
        }
        //判断是否添加房型图片
        if (house.getFloorPlanFiles() != null && !house.getFloorPlanFiles().isEmpty()) {
            List<MultipartFile> files = house.getFloorPlanFiles();
            String floorPlans = Joiner.on(",").join(fileService.getImgPaths(files));
            house.setFloorPlan(floorPlans);
        }
        //设置属性默认值
        BeanHelper.setDefaultProp(house, House.class);
        //设置创建、更新时间时间
        BeanHelper.onInsert(house);
        house.setUserId(user.getId());
        houseClient.addHouse(house);
    }

    /**
     * @Description 获取当前小区列表
     **/
    public List<Community> getAllCommunitys() {
        return houseClient.getAllCommunitys().getResult();
    }

    /**
     * @Description 获取所有城市信息
     **/
    public List<City> getAllCitys() {
        return houseClient.getAllCitys().getResult();
    }

    /**
     * @Description 房产信息评论
     **/
    public void addUserMsg(UserMsg userMsg) {
        houseClient.addUserMsg(userMsg);
    }

    /**
     * @Description 获取最新的房产信息  （最新登记的前8个信息）
     **/
    public List<House> getLastest() {
        return houseClient.getLastest().getResult();
    }

    /**
     * @Description 按条件获取房产信息列表
     **/
    public PageData<House> queryHouse(House query, PageParams build) {
        HouseQueryReq req = new HouseQueryReq(query, build.getLimit(), build.getOffset());
        ListResponse<House> result = houseClient.getHouseList(req).getResult();
        return PageData.buildPage(result.getList(), result.getCount(), build.getPageSize(), build.getPageNum());
    }

    /**
     * @Description 获取热门房产列表
     **/
    public List<House> getHotHouse(Integer recomSize) {
        return houseClient.getHotHouseList(recomSize).getResult();
    }

    /**
     * @Description 根据id查询房产信息
     **/
    public House queryOneHouse(long id) {
        return houseClient.getOneHouse(id).getResult();
    }

    /**
     *@Description 收藏房产
     *@Author wujin
     **/
    public void bindUser2House(Long houseId, Long userId, boolean bookmark) {
        HouseUserReq req = new HouseUserReq(houseId, userId, bookmark ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value, false);
        houseClient.bindOrInBind(req);
    }

    /**
     * @Description 取消收藏房产
     **/
    public void unbindUser2House(Long houseId, Long userId, boolean bookmark) {
        HouseUserReq req = new HouseUserReq(houseId, userId, bookmark ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value, true);
        houseClient.bindOrInBind(req);
    }
}
