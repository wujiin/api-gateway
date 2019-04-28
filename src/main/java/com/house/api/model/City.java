package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description 城市实体
 * @Author wujin
 **/
@Data
@ToString
public class City implements Serializable {
    private Integer id;
    private String cityName;
    private String cityCode;
}
