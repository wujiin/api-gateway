package com.house.api.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Description 机构信息实体
 * @Author wujin
 **/
@Data
@ToString
public class Agency {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String aboutUs;
    private String webSite;
    private String mobile;
}
