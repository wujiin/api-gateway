package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description 机构信息实体
 * @Author wujin
 **/
@Data
@ToString
public class Agency implements Serializable{
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String aboutUs;
    private String webSite;
    private String mobile;
}
