package com.house.api.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Community {
    private Integer id;
    private String cityCode;
    private String cityName;
    private String name;
}
