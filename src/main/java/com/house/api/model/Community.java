package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Community implements Serializable {
    private Integer id;
    private String cityCode;
    private String cityName;
    private String name;
}
