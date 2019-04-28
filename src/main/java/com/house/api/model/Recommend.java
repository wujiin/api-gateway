package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Recommend implements Serializable {
    private Long id;
    private Long houseId;
    private Integer type;
    private Integer userId;
    private Integer district;
    private Date createTime;
}
