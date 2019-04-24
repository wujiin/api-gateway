package com.house.api.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Description 城市实体
 * @Author wujin
 **/
@Data
@ToString
public class City {
    private Integer id;
    private String cityName;
    private String cityCode;
}
