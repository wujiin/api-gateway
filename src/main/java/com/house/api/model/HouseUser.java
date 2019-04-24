package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class HouseUser {
    private Long id;
    private Long houseId;
    private Long userId;
    private Date createTime;
    private Integer type;
}
